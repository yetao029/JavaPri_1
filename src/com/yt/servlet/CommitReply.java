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
import com.yt.bean.Reply;
import com.yt.dao.ReplyDAO;
import com.yt.factory.ReplyDAOFactory;

/**
 * Servlet implementation class CommitReply
 */
@WebServlet(name="CommitReply", urlPatterns="/CommitReply")
public class CommitReply extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		String replyContent = request.getParameter("replyContent");
		int messageID = Integer.parseInt(request.getParameter("messageID"));
		ServletContext servletContext = getServletContext();
		RequestDispatcher dispatcher = null;
		// 是哪个员工发布的回复
		Employee employee = (Employee) request.getSession().getAttribute("employee");
		if (employee == null) {
			request.setAttribute("error", "要进行回复必须首先进行身份识别");

		} else {
			if (replyContent == null || "".equals(replyContent)) {
				request.setAttribute("error", "必须输入回复内容");
			} else {
				Reply replay = new Reply();
				replay.setReplyContent(replyContent);
				replay.setMessageID(messageID);
				replay.setEmployeeID(employee.getEmployeeID());
				replay.setReplyTime(new Date());
				ReplyDAO replayDAO = ReplyDAOFactory.getReplyDAOInstance();
				replayDAO.addReply(replay);
			}
		}
		dispatcher = servletContext.getRequestDispatcher("/GetMessage?messageID=" + messageID);
		dispatcher.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
