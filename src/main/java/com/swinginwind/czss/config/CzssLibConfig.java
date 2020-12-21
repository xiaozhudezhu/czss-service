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
	private String libPath;
		
	@Bean
	public CzssLib getCzssLib() {
		CzssGoLib czssGoLib = (CzssGoLib) Native.loadLibrary(libPath, CzssGoLib.class);
		czssGoLib.Init();
		CzssLib lib = new CzssLib();
		lib.setCzssGoLib(czssGoLib);
		return lib;
	}
	
	
	

}