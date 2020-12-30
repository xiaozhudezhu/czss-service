package com.swinginwind.czss.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.jna.Native;
import com.swinginwind.czss.lib.CzssLib;
import com.swinginwind.czss.lib.CzssLib.CzssGoLib;


@Configuration
public class CzssLibConfig {
	
	@Value("${czss.libPath}")
	private String libPathAuth;
	
	@Value("${czss.libPath2}")
	private String libPathEncrypt;
		
	@Bean("czssLibAuth")
	public CzssLib getCzssLibAuth() {
		CzssLib lib = new CzssLib();
		try {
			CzssGoLib czssGoLib = (CzssGoLib) Native.loadLibrary(libPathAuth, CzssGoLib.class);
			czssGoLib.Init();
			lib.setCzssGoLib(czssGoLib);
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		return lib;
	}
	
	@Bean("czssLibEncrypt")
	public CzssLib getCzssLibEncrypt() {
		CzssLib lib = new CzssLib();
		try {
			CzssGoLib czssGoLib = (CzssGoLib) Native.loadLibrary(libPathEncrypt, CzssGoLib.class);
			czssGoLib.Init();
			lib.setCzssGoLib(czssGoLib);
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		return lib;
	}
	

}
