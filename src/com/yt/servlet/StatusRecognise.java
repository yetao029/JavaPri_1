package com.yt.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yt.bean.Employee;
import com.yt.dao.EmployeeDAO;
import com.yt.factory.EmployeeDAOFactory;

/**
 * Servlet implementation class StatusRecognise
 */
@WebServlet(name="StatusRecognise", urlPatterns="/StatusRecognise")
public class StatusRecognise extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
		RequestDispatcher dispatcher = null;
		String employeeID = request.getParameter("employeeID");
		String password = request.getParameter("password");
		if (employeeID == null || "".equals(employeeID)) {
			request.setAttribute("error", "请输入员工编号!");
			dispatcher = servletContext.getRequestDispatcher("/statusRecognise.jsp");
		} else {
			if (password == null || "".equals(password)) {
				request.setAttribute("error", "请输入系统口令!");
				dispatcher = servletContext.getRequestDispatcher("/statusRecognise.jsp");
			} else {
				EmployeeDAO employeeDAO = EmployeeDAOFactory.getEmployeeDAOInstance();
				Employee employee = employeeDAO.findEmployeeById(Integer.parseInt(employeeID));
				if (employee == null) {
					request.setAttribute("error", "该员工编号不存在!");
					dispatcher = servletContext.getRequestDispatcher("/statusRecognise.jsp");
				} else {
					if (password.equals(employee.getPassword())) {
						request.getSession().setAttribute("employee", employee);
						response.sendRedirect("index.jsp");
						return;
					} else {
						request.setAttribute("error", "系统口令不正确!");
						dispatcher = servletContext.getRequestDispatcher("/statusRecognise.jsp");
					}
				}
			}
		}
		dispatcher.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
