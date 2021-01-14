package com.swinginwind.czss.lib;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Library;
import com.sun.jna.Structure;

public class CzssLib {

	private CzssGoLib czssGoLib;

	public String encryptString(String plain) {
		return czssGoLib.EncryptStringJson(new CzssGoLib.GoString.ByValue(plain));
	}

	public String decryptString(String str) {
		return czssGoLib.DecryptStringJson(new CzssGoLib.GoString.ByValue(str));
	}

	public boolean isSame(String str1, String str2) {
		return czssGoLib.IsSame(new CzssGoLib.GoString.ByValue(str1), new CzssGoLib.GoString.ByValue(str2));
	}

	public String encryptString3T(String id1, String id2, String id3, String plain) {
		return czssGoLib.EncryptString3TJson(new CzssGoLib.GoString.ByValue(id1),
				new CzssGoLib.GoString.ByValue(id2), new CzssGoLib.GoString.ByValue(id3),
				new CzssGoLib.GoString.ByValue(plain));
	}

	public String decryptString3T(String id1, String id2, String id3, String str) {
		return czssGoLib.DecryptString3TJson(new CzssGoLib.GoString.ByValue(id1),
				new CzssGoLib.GoString.ByValue(id2), new CzssGoLib.GoString.ByValue(id3),
				new CzssGoLib.GoString.ByValue(str));
	}
	
	public String encryptImage(String imageStr) {
		return czssGoLib.EncryptImage(new CzssGoLib.GoString.ByValue(imageStr));
	}
	
	public int execute(String imageStr) {
		return czssGoLib.Execute(new CzssGoLib.GoString.ByValue(imageStr));
	}

	/**
	 * @return the czssGoLib
	 */
	public CzssGoLib getCzssGoLib() {
		return czssGoLib;
	}

	/**
	 * @param czssGoLib
	 *            the czssGoLib to set
	 */
	public void setCzssGoLib(CzssGoLib czssGoLib) {
		this.czssGoLib = czssGoLib;
	}

	public static interface CzssGoLib extends Library {
		void Init();

		String EncryptStringJson(GoString.ByValue plain);

		String DecryptStringJson(GoString.ByValue jsonstr);

		boolean IsSame(GoString.ByValue cstr1, GoString.ByValue cstr2);

		String EncryptString3TJson(GoString.ByValue id1, GoString.ByValue id2, GoString.ByValue id3,
				GoString.ByValue plain);

		String DecryptString3TJson(GoString.ByValue id1, GoString.ByValue id2, GoString.ByValue id3,
				GoString.ByValue jsonstr);
		
		String EncryptImage(GoString.ByValue imageStr);
		
		int Execute(GoString.ByValue imageStr);

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

			protected List<String> getFieldOrder() {
				return Arrays.asList(new String[] { "p", "n" });
			}
		}

	}

}
