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
		public static final String accessKeyId = "****";
		public static final String secretAccessKey = "****";
		public static final String endPoint = "****";
		
		// Weather API URL
		public static final String weatherURLLat = "****";
		public static final String weatherURLLong = "****";
		public static final String weatherURLAppID = "****";
		// Amazon instance creation constants	
		public static final String ec2ImageId = "****";
		public static final String ec2InstanceType = "****";
		
		// Database credentials
		public static final String URL = "****";
		public static final String USER = "****";
		public static final String PASS = "****";
		public static final String DB = "****";
		
		//userID
		private static String userId;

		public static String getUserId() {
			return userId;
		}

		public static void setUserId(String userId) {
			UtilConstants.userId = userId;
		}
}
