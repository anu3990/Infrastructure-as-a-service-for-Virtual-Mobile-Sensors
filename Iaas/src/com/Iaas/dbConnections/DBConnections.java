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
import java.util.ArrayList;
import java.util.List;

import com.Iaas.Util.UtilConstants;
import com.Iaas.Util.Utils;
import com.Iaas.VO.SensorVO;
import com.Iaas.VO.UserSensorDeatailVO;
import com.Iaas.VO.UserSensorVO;
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
	
	public List<UserSensorDeatailVO> getSensorDetails(String userId) throws ClassNotFoundException, SQLException{
		Connection dBConnection = createDbConnection();
		List<UserSensorDeatailVO> userSensorsList = new ArrayList<>();
		Statement stmt = dBConnection.createStatement();
		String query = "select sensor_id, type, city, status from sensor, user_sensor where sensor.location_id=user_sensor.location_id and user_id="+'"'+userId+'"'+";";
		ResultSet result = stmt.executeQuery(query);
		while(result.next()){
			UserSensorDeatailVO sensorDeatailVO = new UserSensorDeatailVO();
			sensorDeatailVO.setSensorId(result.getString("sensor_id"));
			sensorDeatailVO.setSensorType(result.getString("type"));
			sensorDeatailVO.setCity(result.getString("city"));
			sensorDeatailVO.setStatus(result.getString("sensor_id"));
			userSensorsList.add(sensorDeatailVO);
		}
		return userSensorsList;
	}

	public void updateStartStatus() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Utils util = new Utils();
		String timeStamp = util.getCurrentTime();
		Connection dBConnection = createDbConnection();
		String insertData = "update user_sensor set status=" +'"'+"running"+'"'+" and "+"start_time="+"'"+timeStamp+"'"+";" ;
		Statement stmt = dBConnection.createStatement();
		stmt.executeUpdate(insertData);
	}
	
	public void updateStopStatus() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Utils util = new Utils();
		String timeStamp = util.getCurrentTime();
		Connection dBConnection = createDbConnection();
		String insertData = "update user_sensor set status=" +'"'+"stopped"+'"'+" and "+"end_time="+"'"+timeStamp+"'"+";" ;
		Statement stmt = dBConnection.createStatement();
		stmt.executeUpdate(insertData);
	}
	
	public void updateTerminateStatus() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection dBConnection = createDbConnection();
		String insertData = "update user_sensor set status=" +'"'+"termnated"+'"' ;
		Statement stmt = dBConnection.createStatement();
		stmt.executeUpdate(insertData);
	}
}
