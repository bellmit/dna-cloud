package com.bazl.dna.deploy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * 安装部署工具类
 * @author zhaoyong
 *
 */
public class DeployUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeployUtils.class);
	
	public static final int POSIX_PERMISSIONS = 755;
	
	public static boolean isMac() {
		String os = System.getProperty("os.name");
		if(os.toLowerCase().startsWith("win")){ 
			return false;
		}
		return true;
	}
	
	/**
	 * 创建连接
	 * @param ip
	 * @param port
	 * @return Connection
	 */
	public static Connection getConnection(String ip, int port) {
		try {
			/*Connection*/
			Connection con = new Connection(ip, port);
			con.connect();
			return con;
		} catch (IOException e) {
			LOGGER.error("getConnection error:", e);
		}
		return null;
	}
	
	/**
	 * 获取Session
	 * @param con
	 * @return Session
	 */
	public static Session getSession(Connection con) {
		try {
			return con.openSession();
		} catch (IOException e) {
			LOGGER.error("getSession error:", e);
		}
		return null;
	}
	
	/**
	 * 关闭连接
	 * @param con
	 * @param session
	 * @param sftpClient
	 */
	public static void close(Connection con, Session session, SFTPv3Client sftpClient) {
		if (sftpClient != null) {
			sftpClient.close();
		}
		
		if (session != null) {
			session.close();
		}
		
		if (con != null) {
			con.close();
		}
	}
	
	/**
	 * 验证帐号
	 * @param con
	 * @param user
	 * @param password
	 * @return boolean
	 */
	public static boolean auth(Connection con, String user, String password) {
		boolean result = false;
		try {
			result = con.authenticateWithPassword(user, password);
		} catch (IOException e) {
			LOGGER.error("auth error:", e);
		}
		return result;
	}
	
	/**
	 * 调用方式
	 * 
	 * @param commands	执行命令
	 * @return List<List<String>>
	 */
	public static List<List<String>> execCommand(Connection con, String[] commands) {
		List<List<String>> result = Lists.newArrayList();
		try {
			for (String s : commands) {
				LOGGER.info("> {}", s);
				Session session = getSession(con);
				session.execCommand(s);
				
				InputStream eis = new StreamGobbler(session.getStderr());
				List<String> list = IOUtils.readLines(eis, Charset.defaultCharset());
				eis.close();
				
				if (list.isEmpty()) {
					InputStream is = new StreamGobbler(session.getStdout());
					list = IOUtils.readLines(is, Charset.defaultCharset());
					is.close();
				}
				result.add(list);
				close(null, session, null);
			}
		} catch (Exception e) {
			List<String> list = Lists.newArrayList();
			list.add("ssh exec command error.");
			result.add(list);
			LOGGER.error("execCommand error:", e);
		}
		return result;
	}
	
	/**
	 * 执行命令
	 * @param commands
	 * @return List<String>
	 */
	public static List<String> execExecute(String[] commands) {
		if (commands == null) {
			return Collections.emptyList();
		}
		
		List<String> result = Lists.newArrayList();
		for (String command : commands) {
			result.add(execExecute(command));
		}
		return result;
	}

	/**
	 * 执行命令
	 * @param command
	 * @return String
	 */
	public static String execExecute(String command) {
		if (command == null) {
			return null;
		}
		
		try (ByteArrayOutputStream outstream = new ByteArrayOutputStream()) {
			CommandLine commandLine = CommandLine.parse(command);

			DefaultExecutor exec = new DefaultExecutor();
			exec.setExitValues(null);
			
			PumpStreamHandler streamHandler = new PumpStreamHandler(outstream);
			exec.setStreamHandler(streamHandler);
			exec.execute(commandLine);
			return outstream.toString(Charset.defaultCharset());
		} catch (IOException e) {
			LOGGER.error("execExecute error:", e);
		}
		return null;
	}
	
	/**
	 * 非阻塞 执行命令
	 * @param commands
	 */
	public static void execExecuteCommand(String[] commands) {
		if (commands == null) {
			return;
		}
		
		for (String command : commands) {
			execExecuteCommand(command);
		}
	}
	
	/**
	 * 非阻塞 执行命令
	 * @param command
	 */
	public static void execExecuteCommand(String command) {
		if (command == null) {
			return;
		}
		
		try (ByteArrayOutputStream outstream = new ByteArrayOutputStream()) {
			CommandLine commandLine = CommandLine.parse(command);

			DefaultExecutor exec = new DefaultExecutor();
			exec.setExitValues(null);
			
			DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
			
			PumpStreamHandler streamHandler = new PumpStreamHandler(outstream);
			exec.setStreamHandler(streamHandler);
			exec.execute(commandLine, handler);
			handler.waitFor();
		} catch (Exception e) {
			LOGGER.error("execExecuteCommand error:", e);
		}
	}
	
	public static void uploadFileMap(Connection con, String[] localFiles, String remoteTargetDirectory) {
		try {
			SFTPv3Client sftpClient = new SFTPv3Client(con);
			boolean isDirExists = forceDir(sftpClient, remoteTargetDirectory);
			LOGGER.info("> Directory: {} is not create: {}", remoteTargetDirectory, isDirExists);
			SCPClient scpClient = con.createSCPClient();
			LOGGER.info("> {} -> {}", localFiles, remoteTargetDirectory);
			scpClient.put(localFiles, remoteTargetDirectory);
			
			sftpClient.close();
		} catch (IOException e) {
			LOGGER.error("uploadFileMap error:", e);
		}
	}
	
	/**
	 * 查询目录
	 * 
	 * @param sftpClient
	 * @param directory
	 * @return boolean
	 */
	public static boolean ls(SFTPv3Client sftpClient, String directory) {
		try {
			sftpClient.ls(directory);
			return true;
		} catch (IOException e) {
			// not exception
		}
		return false;
	}
	
	/**
	 * 创建目录
	 * 
	 * @param sftpClient
	 * @param directory
	 * @return boolean
	 */
	public static boolean forceDir(SFTPv3Client sftpClient, String directory) {
		boolean result = ls(sftpClient, directory);
		if (!result) {
			try {
				sftpClient.mkdir(directory, POSIX_PERMISSIONS);
				return true;
			} catch (IOException e) {
				LOGGER.error("Error mkdir:", e);
			}
		}
		return false;
	}
}
