package com.yt.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yt.bean.Employee;
import com.yt.bean.Message;
import com.yt.dao.MessageDAO;
import com.yt.factory.MessageDAOFactory;

/**
 * Servlet implementation class MsgPublish
 */
@WebServlet(name="MsgPublish", urlPatterns="/MsgPublish")
public class MsgPublish extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = null;
		Employee employee = (Employee) request.getSession().getAttribute("employee");
		if (employee == null) {
			request.setAttribute("error", "要发布消息必须首先进行身份识别");
			dispatcher = context.getRequestDispatcher("/publishNewMsg.jsp");
		} else {
			if (title == null || "".equals(title)) {
				request.setAttribute("error", "标题不可为空");
				dispatcher = context.getRequestDispatcher("/publishNewMsg.jsp");
			} else {
				Message message = new Message();
				message.setEmployeeID(employee.getEmployeeID());
				message.setMessageTitle(title);
				message.setMessageContent(content);
				message.setPublishTime(new Date());
				MessageDAO messageDAO = MessageDAOFactory.getMessageDAOInstance();
				messageDAO.addMessage(message);
				dispatcher = context.getRequestDispatcher("/GetMessageList.jsp");
			}
		}
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
