/**
 * 
 */
package com.Iaas.dbConnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Iaas.Util.UtilConstants;
import com.Iaas.Util.Utils;
import com.Iaas.VO.SensorVO;
import com.Iaas.VO.UserSensorDeatailVO;
import com.Iaas.VO.UserSensorVO;
import com.Iaas.VO.ViewSensorDetailsVO;
import com.Iaas.VO.WeatherDataVO;

/**
 * @author Rahul
 *
 */
public class DBConnections {

	public static Connection createDbConnection() throws SQLException, ClassNotFoundException {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(UtilConstants.URL + UtilConstants.DB, UtilConstants.USER,
					UtilConstants.PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void closeConnection(Connection connection) {
		if (connection != null)
			try {
				connection.close();
				System.out.println("Connection Closed");
			} catch (SQLException e) {
				System.out.println(e.getStackTrace());
			}
	}

	public void insertWeatherData(WeatherDataVO weatherData) throws ClassNotFoundException, SQLException {
		Connection dBConnection = createDbConnection();
		String insertData = "insert into sensor_data "
				+ "(location_id, pressure, temp_min, temp_max, humidity, wind_speed, wind_degree, last_update_time)"
				+ " values" + "(1,?,?,?,?,?,?,?)";
		PreparedStatement ps = dBConnection.prepareStatement(insertData);
		ps.setString(1, weatherData.getPressure());
		ps.setString(2, weatherData.getMin_temp());
		ps.setString(3, weatherData.getMax_temp());
		ps.setString(4, weatherData.getHumidity());
		ps.setString(5, weatherData.getWindSpeed());
		ps.setString(6, weatherData.getWindDirection());
		ps.setString(7, weatherData.getTimeStamp());
		ps.executeUpdate();
	}

	public void insertSensorData(SensorVO sensor) throws ClassNotFoundException, SQLException {
		Connection dBConnection = createDbConnection();
		String insertData = "insert into sensor" + "(type,city)" + " values" + "(?,?)";
		PreparedStatement ps = dBConnection.prepareStatement(insertData);
		ps.setString(1, sensor.getType());
		ps.setString(2, sensor.getLocation());
		ps.executeUpdate();
	}

	public void insertUserSensorData(UserSensorVO userSensor) throws ClassNotFoundException, SQLException {
		Connection dBConnection = createDbConnection();
		String insertData = "insert into user_sensor" + "(user_id,sensor_id,location_id,status,start_time)" + " values" + "(?,?,?,?,?)";
		PreparedStatement ps = dBConnection.prepareStatement(insertData);
		ps.setString(1, userSensor.getUserId());
		ps.setString(2, userSensor.getSensorId());
		ps.setInt(3, userSensor.getLocationId());
		ps.setString(4, userSensor.getStatusId());
		ps.setString(5, userSensor.getStartTime());
		ps.executeUpdate();
	}
	
	public int getLocationId(String location, String SensorType) throws ClassNotFoundException, SQLException {
		int locationId = 0;
		Connection dBConnection = createDbConnection();
		Statement stmt = dBConnection.createStatement();
		String query = "Select location_id from sensor where city="+'"'+location+'"'+" and type="+'"'+SensorType+'"'+";";
		ResultSet result = stmt.executeQuery(query);
		while(result.next()){
			locationId = result.getInt("location_id");
		}
		return locationId;
	}
	
	public boolean checkLocation(String location, String SensorType) throws ClassNotFoundException, SQLException{
		Connection dBConnection = createDbConnection();
		Statement stmt = dBConnection.createStatement();
		String query = "Select location_id from sensor where city="+'"'+location+'"'+" and type="+'"'+SensorType+'"'+";";
		ResultSet result = stmt.executeQuery(query);
		boolean dataExists = result.next();
		return dataExists;
	}
	
	public List<UserSensorDeatailVO> getSensorDetails(String userId, String status) throws ClassNotFoundException, SQLException{
		Connection dBConnection = createDbConnection();
		List<UserSensorDeatailVO> userSensorsList = new ArrayList<>();
		Statement stmt = dBConnection.createStatement();
		String query = null;
		if(status.equals("all")){
			query = "select sensor_id, type, city, status, start_time, end_time from sensor, user_sensor where sensor.location_id=user_sensor.location_id and user_id="+'"'+userId+'"'+";";
		}
		else if(status.equals("running") || status.equals("stopped")) {
			query = "select sensor_id, type, city, status, start_time, end_time from sensor, user_sensor where sensor.location_id=user_sensor.location_id and user_id="+'"'+userId+'"'+" and status ="+"'"+status+"'"+";";
		}
		else if(status.equals("terminated")){
			query = "select sensor_id, type, city, status, start_time, end_time from sensor, user_sensor where sensor.location_id=user_sensor.location_id and user_id="+'"'+userId+'"'+" and status !="+"'"+status+"'"+";";
		}
		
		ResultSet result = stmt.executeQuery(query);
		while(result.next()){
			UserSensorDeatailVO sensorDeatailVO = new UserSensorDeatailVO();
			sensorDeatailVO.setSensorId(result.getString("sensor_id"));
			sensorDeatailVO.setSensorType(result.getString("type"));
			sensorDeatailVO.setCity(result.getString("city"));
			sensorDeatailVO.setStatus(result.getString("status"));
			sensorDeatailVO.setStartTime(result.getString("start_time"));
			sensorDeatailVO.setEndTime(result.getString("end_time"));
			userSensorsList.add(sensorDeatailVO);
		}
		return userSensorsList;
	}

	public void updateStartStatus(String sensorId) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Utils util = new Utils();
		String timeStamp = util.getCurrentTime();
		Connection dBConnection = createDbConnection();
		String insertData = "update user_sensor set status=?, start_time=?, end_time=? where sensor_id=?";
		PreparedStatement stmt = dBConnection.prepareStatement(insertData);
		stmt.setString(1, "running");
		stmt.setString(2, timeStamp);
		stmt.setString(3, null);
		stmt.setString(4, sensorId);
		stmt.executeUpdate();
		
		ViewSensorDetailsVO userStats = new ViewSensorDetailsVO();
		userStats.setSensorId(sensorId);
		userStats.setStartTime(timeStamp);
		insertUserSensorStats(userStats);
	}
	
	public void updateStopStatus(String sensorId) throws ClassNotFoundException, SQLException, ParseException {
		// TODO Auto-generated method stub
		Utils util = new Utils();
		String timeStamp = util.getCurrentTime();
		Connection dBConnection = createDbConnection();
		String insertDataStatus = "update user_sensor set status=? where sensor_id=? ;";
		String insertDataEndTime = "update user_sensor set end_time=? where sensor_id=? ;" ;
		PreparedStatement stmt1 = dBConnection.prepareStatement(insertDataStatus);
		stmt1.setString(1, "stopped");
		stmt1.setString(2, sensorId);
		stmt1.executeUpdate();
		PreparedStatement stmt2 = dBConnection.prepareStatement(insertDataEndTime);
		stmt2.setString(1, timeStamp);
		stmt2.setString(2, sensorId);
		stmt2.executeUpdate();
		
		updateEndTimeStats(sensorId, timeStamp);
	}
	
	public void updateTerminateStatus(String sensorId) throws ClassNotFoundException, SQLException, ParseException {
		// TODO Auto-generated method stub
		Utils util = new Utils();
		String timeStamp = util.getCurrentTime();
		Connection dBConnection = createDbConnection();
		String insertData = "update user_sensor set status=?, end_time=? where sensor_id=?";
		PreparedStatement stmt = dBConnection.prepareStatement(insertData);
		stmt.setString(1, "terminated");
		stmt.setString(2, timeStamp);
		stmt.setString(3, sensorId);
		stmt.executeUpdate();
		
		updateEndTimeStats(sensorId, timeStamp);
	}
	
	public String getUserId(String user) throws ClassNotFoundException, SQLException{
		String userId = null;
		Connection dBConnection = createDbConnection();
		Statement stmt = dBConnection.createStatement();
		String query = "Select user_id from user where email_id="+"'"+user+"'"+";";
		ResultSet result = stmt.executeQuery(query);
		while(result.next()){
			userId = result.getString("user_id");
		}
		return userId;
	}
	
	public List<ViewSensorDetailsVO> getUserSensorStats(String sensorId) throws ClassNotFoundException, SQLException{
		Connection dBConnection = createDbConnection();
		List<ViewSensorDetailsVO> userStatsList = new ArrayList<>();
		Statement stmtStartTime = dBConnection.createStatement();
		String query = "Select sensor_id, start_time, end_time, session_cost from sensor_stat where sensor_id="+"'"+sensorId+"'"+";";
		ResultSet result = stmtStartTime.executeQuery(query);
		while(result.next()){
			ViewSensorDetailsVO viewSensorDetailsVO = new ViewSensorDetailsVO();
			viewSensorDetailsVO.setSensorId(result.getString("sensor_id"));
			viewSensorDetailsVO.setStartTime(result.getString("start_time"));
			viewSensorDetailsVO.setEndTime(result.getString("end_time"));
			viewSensorDetailsVO.setCost(result.getString("session_cost"));
			userStatsList.add(viewSensorDetailsVO);
		}
		return userStatsList;
	}
	
	public static void updateEndTimeStats(String sensorId, String endTime) throws ClassNotFoundException, SQLException, ParseException{
		String startTime = null;
		Connection dBConnection = createDbConnection();
		Statement stmtStartTime = dBConnection.createStatement();
		String query = "Select start_time from sensor_stat where sensor_id="+'"'+sensorId+'"'+" and end_time is null;";
		ResultSet result = stmtStartTime.executeQuery(query);
		while(result.next()){
			startTime = result.getString("start_time");
		}
		
		String cost = calculateCost(startTime, endTime);
		String insertData = "update sensor_stat set end_time=?, session_cost=? where sensor_id=?";
		PreparedStatement stmt = dBConnection.prepareStatement(insertData);
		stmt.setString(1, endTime);
		stmt.setString(2, cost);
		stmt.setString(3, sensorId);
		stmt.executeUpdate();
	}
	
	public static void insertUserSensorStats(ViewSensorDetailsVO userStats) throws ClassNotFoundException, SQLException {
		Connection dBConnection = createDbConnection();
		String insertData = "insert into sensor_stat" + "(sensor_id, start_time)" + " values" + "(?,?)";
		PreparedStatement ps = dBConnection.prepareStatement(insertData);
		ps.setString(1, userStats.getSensorId());
		ps.setString(2, userStats.getStartTime());
		ps.executeUpdate();
	}
	
	public static String calculateCost(String startTime, String endTime) throws ParseException{
		long seconds = (convertStringToDate(endTime).getTime()-convertStringToDate(startTime).getTime())/1000;
		double hours = seconds/3600;
		double cost = (hours*UtilConstants.perHourUsage/1024)*UtilConstants.costPerGb + (hours*UtilConstants.costPerHour);
		return Double.toString(cost);
	}
	
	public static Date convertStringToDate(String date) throws ParseException{
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy hh:mm a");
		Date convertedDate = formatter.parse(date);
		return convertedDate;
	}
}
