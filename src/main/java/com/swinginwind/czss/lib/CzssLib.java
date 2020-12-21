package com.swinginwind.czss.lib;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;


public class CzssLib {
	
	private CzssGoLib czssGoLib;
	
	public String encryptString(String plain) {
		Pointer pointer = czssGoLib.EncryptStringJson(new CzssGoLib.GoString.ByValue(plain));
		return pointer.getString(0);
	}
	
	public String decryptString(String str) {
		Pointer pointer = czssGoLib.DecryptStringJson(new CzssGoLib.GoString.ByValue(str));
		return pointer.getString(0);
	}
	
	public boolean isSame(String str1, String str2) {
		return czssGoLib.IsSame(new CzssGoLib.GoString.ByValue(str1), new CzssGoLib.GoString.ByValue(str2));
	}
	
	
	/**
	 * @return the czssGoLib
	 */
	public CzssGoLib getCzssGoLib() {
		return czssGoLib;
	}

	/**
	 * @param czssGoLib the czssGoLib to set
	 */
	public void setCzssGoLib(CzssGoLib czssGoLib) {
		this.czssGoLib = czssGoLib;
	}



	public static interface CzssGoLib extends Library {
	    void Init();
	    Pointer EncryptStringJson(GoString.ByValue plain);
	    Pointer DecryptStringJson(GoString.ByValue jsonstr);
	    boolean IsSame(GoString.ByValue cstr1, GoString.ByValue cstr2);
	    
	    // C type struct { const char *p; GoInt n; }
        public class GoString extends Structure {
            public static class ByValue extends GoString implements Structure.ByValue {
            	public ByValue() {
                }

                public ByValue(String s) {
                    this.p = s;
                    this.n = s.length();
                }
            	
            }
            public String p;
            public long n;
            protected List getFieldOrder(){
                return Arrays.asList(new String[]{"p","n"});
            }
        }

    }
	

}
