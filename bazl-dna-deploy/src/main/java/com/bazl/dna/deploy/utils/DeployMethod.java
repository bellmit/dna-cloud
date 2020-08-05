package com.bazl.dna.deploy.utils;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bazl.dna.deploy.entity.Server;

import ch.ethz.ssh2.Connection;

public class DeployMethod {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeployMethod.class);
	
	/**
	 * 停止服务
	 * @param con
	 * @param targetServer
	 */
	public static void stop(Connection con, String targetServer) {
		//kill tomcat
		String cmd = "kill -9 `cat "+targetServer+".pid 2>/dev/null` 2>/dev/null || true";
		LOGGER.info("> {}", cmd);
		List<List<String>> execList = DeployUtils.execCommand(con, new String[] { cmd });
		for (List<String> list : execList) {
			for (String s : list) {
				LOGGER.info("> {}", s);
			}
		}
	}
	
	/**
	 * 启动服务
	 * @param con
	 * @param targetServer
	 */
	public static void start(Connection con, String targetServer) {
		//tomcat启动找不到java_home,需要设置 ln -s /opt/jdk1.6.0_32/bin/java /bin/java
		String cmd = targetServer + "/bin/startup.sh";
		LOGGER.info("> {}", cmd);
		List<List<String>> execList = DeployUtils.execCommand(con, new String[] { cmd });
		for (List<String> list : execList) {
			for (String s : list) {
				LOGGER.info("> {}", s);
			}
		}
	}
	
	/**
	 * 上传文件到目标服务器
	 */
	public static void deploy(Server server, String localFile, String targetPath) {
		// upload
		String a1 = "scp -P " + server.getPort() + " " + localFile + " " + server.getUsername() + "@" + server.getIp() + ":" + targetPath;
		LOGGER.info("> {}", a1);
		DeployUtils.execExecute(a1);
	}
	
	/**
	 * 执行命令
	 * @param con
	 * @param commands
	 */
	public static void execCommands(Connection con, String[] commands) {
		
		List<List<String>> execList = DeployUtils.execCommand(con, commands);
		for (List<String> list : execList) {
			for (String s : list) {
				LOGGER.info("> {}", s);
			}
		}
	}
	
	/**
	 * 打包部署到指定 目录中
	 * @param computer
	 * @param targetRoot
	 * @param projectName
	 */
	public static void deployNative(String[] projectNames, String targetRoot, String version, String suffix) {
		String [] commands = new String[projectNames.length];
		for (int i = 0; i < projectNames.length; i++) {
			String path = ".." + File.separator + projectNames[i] + File.separator +
					"target" + File.separator + projectNames[i] + "-" + version + "." + suffix;
			String targetPath = targetRoot + File.separator + projectNames[i];
			
			String mkdirCommand = "mkdir " + targetRoot + File.separator + projectNames[i];
			if (DeployUtils.isMac()) {
				DeployUtils.execExecute(mkdirCommand);
				commands[i] = "cp -rf " + path + " " + targetPath;
			} else {
				DeployUtils.execExecute("cmd /c " + mkdirCommand);
				commands[i] = "cmd /c copy " + path + " " + targetPath;
			}
			LOGGER.info("> {}", commands[i]);
		}
		DeployUtils.execExecuteCommand(commands);
	}
	
	public static void deployServer(List<Server> deployServerList,
			String[] projectNames, String local, String targetRoot, String version, String suffix) {
		try {
			// 多个目标进行部署
			for (Server server : deployServerList) {
				// get connection
				Connection con = DeployUtils.getConnection(server.getIp(), server.getPort());
				
				// 认证
				boolean auth = DeployUtils.auth(con, server.getUsername(), server.getPassword());
				LOGGER.info("Auth : {}", auth);
				if (auth) {
					String[] cmds = deploy(con, server, projectNames, local, targetRoot, version, suffix);
					DeployMethod.execCommands(con, cmds);
				}
				// close
				DeployUtils.close(con, null, null);
			}
		} catch (Exception e) {
			LOGGER.error("deployServer error:", e);
		}
	}
	
	public static String[] deploy(Connection con, Server server, 
			String[] projectNames, String local, String targetRoot, String version, String suffix) {
		String[] cmds = new String[projectNames.length];
		for (int i = 0; i < projectNames.length; i++) {
			String localFile = local + File.separator + projectNames[i] + File.separator + projectNames[i] + "-" + version + "." + suffix;
			String targetPath = targetRoot + "/" + projectNames[i];
			if (DeployUtils.isMac()) {
				// mac scp方式
				DeployMethod.deploy(server, localFile, targetPath);
			} else {
				// windows scp方式
				DeployUtils.uploadFileMap(con, new String[] { localFile } , targetPath);
			}
			// 准备执行的命令
			cmds[i] = targetRoot + File.separator + projectNames[i] + File.separator + "start.sh";
		}
		return cmds;
	}
}
