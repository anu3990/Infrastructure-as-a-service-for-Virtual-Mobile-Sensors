/**
 * 
 */
package com.Iaas.dbConnections;

import java.sql.SQLException;
import java.util.List;

import com.Iaas.Util.InstancesUtilility;
import com.Iaas.Util.UtilConstants;
import com.Iaas.Util.Utils;
import com.Iaas.VO.SensorVO;
import com.Iaas.VO.UserSensorDeatailVO;
import com.Iaas.VO.UserSensorVO;

/**
 * @author Rahul
 *
 */
public class DBOperations {
	public String insertSensorData(String sensorType, String location) throws ClassNotFoundException, SQLException{
		SensorVO sensorVO = new SensorVO();
		sensorVO.setLocation(location);
		sensorVO.setType(sensorType);
		
		DBConnections dBConnections = new DBConnections();
		dBConnections.insertSensorData(sensorVO);
		return "running";
	}
	
	public void insertUserSensorData(String sensorId, String status,String location, String sensorType) throws ClassNotFoundException, SQLException{
		Utils util = new Utils();
		
		DBConnections dbConnections = new DBConnections();
		UserSensorVO userSensorVO = new UserSensorVO();
		
		userSensorVO.setUserId(UtilConstants.getUserId());
		userSensorVO.setSensorId(sensorId);
		userSensorVO.setLocationId(dbConnections.getLocationId(location, sensorType));
		userSensorVO.setStatusId(status);
		userSensorVO.setStartTime(util.getCurrentTime());

		dbConnections.insertUserSensorData(userSensorVO);
	}
	
	public List<UserSensorDeatailVO> viewSensorsDetails(String userId, String status) throws ClassNotFoundException, SQLException{
		DBConnections dbConnection = new DBConnections();
		List<UserSensorDeatailVO> userSensorsList= dbConnection.getSensorDetails(userId, status);
		return userSensorsList;
	}
	
	public void startSensor(String sensorId) throws ClassNotFoundException, SQLException{
		InstancesUtilility iu = new InstancesUtilility();
		iu.startSensorInstance(sensorId);
		
		DBConnections dbConnection = new DBConnections();
		dbConnection.updateStartStatus(sensorId);
	}
	
	public void stopSensor(String sensorId) throws ClassNotFoundException, SQLException{
		InstancesUtilility iu = new InstancesUtilility();
		iu.stopSensorInstance(sensorId);
		DBConnections dbConnection = new DBConnections();
		dbConnection.updateStopStatus(sensorId);
	}
	
	public void terminateSensor(String sensorId) throws ClassNotFoundException, SQLException{
		InstancesUtilility iu = new InstancesUtilility();
		iu.terminateSensorInstance(sensorId);
		DBConnections dbConnection = new DBConnections();
		dbConnection.updateTerminateStatus(sensorId);
	}
	
	public String getUserId(String name) throws ClassNotFoundException, SQLException{
		DBConnections dbconn = new DBConnections();
		return dbconn.getUserId(name);
	}
}
