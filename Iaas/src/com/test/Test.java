/**
 * 
 */
package com.test;

import java.util.UUID;

/**
 * @author Rahul
 *
 */
public class Test {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		/*
		 * InstancesUtilility iu = new InstancesUtilility();
		 * iu.createSensorInstance("Temperature","San Jose");
		 * Thread.sleep(90000); Utils ls= new Utils(); String[] lat =
		 * ls.getLatLongPositions("San Jose"); SensorData sd = new SensorData();
		 * sd.fetchData(UtilConstants.weatherURLLat+lat[0]+UtilConstants.
		 * weatherURLLong+lat[1]+UtilConstants.weatherURLAppID);
		 */
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
	    System.out.println("uuid = " + uuid);
	}
}
