/**
 * 
 */
package com.Iaas.VO;

/**
 * @author Rahul
 *
 */
public class UserSensorDeatailVO {
	private String sensorId;
	private String sensorType;
	private String city;
	private String status;
	public String getSensorId() {
		return sensorId;
	}
	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}
	public String getSensorType() {
		return sensorType;
	}
	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
