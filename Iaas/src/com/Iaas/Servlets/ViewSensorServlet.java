/**
 * 
 */
package com.Iaas.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Iaas.VO.UserSensorDeatailVO;
import com.Iaas.dbConnections.DBOperations;

/**
 * @author Rahul
 *
 */
public class ViewSensorServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String userId = request.getParameter("userId");
		String userId="1";
		DBOperations dbOperations = new DBOperations();
		List<UserSensorDeatailVO> userSensorData = null;
		try {
			userSensorData = dbOperations.viewSensorsDetails(userId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("userSensorList", userSensorData);
		RequestDispatcher rd = request.getRequestDispatcher("viewSensor.jsp");
		rd.forward(request, response);
	}
}
