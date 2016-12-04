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

import com.Iaas.Util.UtilConstants;
import com.Iaas.VO.UserSensorDeatailVO;
import com.Iaas.dbConnections.DBOperations;

/**
 * @author Rahul
 *
 */
public class ViewSensorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("blank.html");
		rd.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBOperations dbOperations = new DBOperations();
		List<UserSensorDeatailVO> userSensorData = null;
		String status = "all";
		try {
			userSensorData = dbOperations.viewSensorsDetails(UtilConstants.getUserId(), status);
			request.setAttribute("userSensorList", userSensorData);
			RequestDispatcher rd = request.getRequestDispatcher("viewSensor.jsp");
			rd.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
