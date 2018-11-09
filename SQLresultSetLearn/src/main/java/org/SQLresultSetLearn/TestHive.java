package org.SQLresultSetLearn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import com.facebook.fb303.FacebookService.AsyncProcessor.reinitialize;

public class TestHive {
	private static final Object RESOURCES_LOCK = new Object();
	public static Configuration getConfiguration(String paths) {
		Configuration configuration = new Configuration();
		String[] splits = paths.split(",");
		for (String path : splits) {
			configuration.addResource(new Path(path.trim()));
		}
		return configuration;
	}
	
	public boolean validateUserWithKerberos(String conf,String principal,String keyTab) throws IOException {
		ClassLoader saveClassLoder = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
		Configuration configuration = getConfiguration(conf);
		configuration.set("hadoop.security.authentication", "Kerberos");
		FileSystem fSystem;
		UserGroupInformation ugi;
		
		synchronized (RESOURCES_LOCK) {
			UserGroupInformation.setConfiguration(configuration);
			UserGroupInformation.loginUserFromKeytab(principal, keyTab);
		}
		Thread.currentThread().setContextClassLoader(saveClassLoder);
		return false;
	}

	/**
	 * 利用jdbc连接hive数据库
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void hiveJDBC() throws ClassNotFoundException, SQLException {
		Properties properties = new Properties();
		try {
			properties.load(TestHive.class.getClassLoader().getResourceAsStream("dbcp.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Class.forName(properties.getProperty("hivedriver"));
		Connection connection = DriverManager.getConnection(properties.getProperty("hiveurl"));
		PreparedStatement ps = connection.prepareStatement("select * from apple.demo1");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			String string = rs.getString(2);
			System.out.println(string);
		}
		System.out.println("over");
	}
	
	/**
	 * 测试使用Kerberos认证连接hive
	 * @throws Exception
	 */
	public static void hiveJDBCWithKerberos() throws Exception {
		Configuration configuration = new Configuration();
		configuration.setBoolean("hadoop.security.authorization", true);
		configuration.set("hadoop.security.authentication", "Kerberos");
		System.setProperty("java.security.krb5.conf", "C:\\Users\\lijk_\\Downloads\\krb5.conf");
		UserGroupInformation ugi;
		UserGroupInformation.setConfiguration(configuration);
		UserGroupInformation.loginUserFromKeytab("kylo@ABC123.WU", "C:\\Users\\lijk_\\Downloads\\kylo.keytab");
		
		Properties properties = new Properties();
		try {
			properties.load(TestHive.class.getClassLoader().getResourceAsStream("dbcp.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Class.forName(properties.getProperty("hivedriver2"));
		Connection connection = DriverManager.getConnection(properties.getProperty("hiveurl2"));
		PreparedStatement ps = connection.prepareStatement("show databases");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			String string = rs.getString(1);
			System.out.println(string);
		}
	}
	public static void main(String[] args) throws Exception {
		hiveJDBCWithKerberos();
		
	}
}
