/**
 * 
 */
package com.Iaas.Util;

/**
 * @author Rahul
 *
 */
public class UtilConstants {
	// Amazon account access details with IAM credentials and end point
		public static final String accessKeyId = "AKIAJZIU5U65ISR7N6DA";
		public static final String secretAccessKey = "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";
		public static final String endPoint = "ec2.us-west-1.amazonaws.com";
		
		// Weather API URL
		public static final String weatherURLLat = "http://api.openweathermap.org/data/2.5/weather?lat=";
		public static final String weatherURLLong = "&lon=";
		public static final String weatherURLAppID = "&APPID=df3d1d39556280e9221fd2cc2f7ebdfe";
		// Amazon instance creation constants	
		public static final String ec2ImageId = "ami-23e8a343";
		public static final String ec2InstanceType = "t2.micro";
		
		// Database credentials
		public static final String URL = "jdbc:mysql://team18-instance1.c2s2dfvr9r2j.us-west-1.rds.amazonaws.com:3306/";
		public static final String USER = "team18user";
		public static final String PASS = "team18pass";
		public static final String DB = "team18dB1";
		
		//userID
		private static String userId;

		public static String getUserId() {
			return userId;
		}

		public static void setUserId(String userId) {
			UtilConstants.userId = userId;
		}
}
