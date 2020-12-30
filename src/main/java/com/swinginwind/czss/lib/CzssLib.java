package com.swinginwind.czss.lib;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Library;
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

	public String encryptString3T(String id1, String id2, String id3, String plain) {
		Pointer pointer = czssGoLib.EncryptString3TJson(new CzssGoLib.GoString.ByValue(id1),
				new CzssGoLib.GoString.ByValue(id2), new CzssGoLib.GoString.ByValue(id3),
				new CzssGoLib.GoString.ByValue(plain));
		return pointer.getString(0);
	}

	public String decryptString3T(String id1, String id2, String id3, String str) {
		Pointer pointer = czssGoLib.DecryptString3TJson(new CzssGoLib.GoString.ByValue(id1),
				new CzssGoLib.GoString.ByValue(id2), new CzssGoLib.GoString.ByValue(id3),
				new CzssGoLib.GoString.ByValue(str));
		return pointer.getString(0);
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

		Pointer EncryptStringJson(GoString.ByValue plain);

		Pointer DecryptStringJson(GoString.ByValue jsonstr);

		boolean IsSame(GoString.ByValue cstr1, GoString.ByValue cstr2);

		Pointer EncryptString3TJson(GoString.ByValue id1, GoString.ByValue id2, GoString.ByValue id3,
				GoString.ByValue plain);

		Pointer DecryptString3TJson(GoString.ByValue id1, GoString.ByValue id2, GoString.ByValue id3,
				GoString.ByValue jsonstr);

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
