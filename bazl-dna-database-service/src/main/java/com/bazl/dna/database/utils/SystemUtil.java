package com.bazl.dna.database.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtil.class);

	private static Properties systemConfig = new Properties();

	static {
		try {

           InputStream in = SystemUtil.class.getClassLoader().getResourceAsStream("/SystemConfig.xml");
            if(in ==null){
                in = SystemUtil.class.getClassLoader().getResourceAsStream("SystemConfig.xml");
            }
			systemConfig.loadFromXML(in);
			in.close();
		} catch (IOException e) {
			LOGGER.error("Error:", e);
			throw new ExceptionInInitializerError(e.getMessage());
		}
	}

	public static Properties getSystemConfig() {
		return systemConfig;
	}

	public static void setSystemConfig(Properties systemConfig) {
		SystemUtil.systemConfig = systemConfig;
	}

}
