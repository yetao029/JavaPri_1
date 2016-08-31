package com.yt.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yt.bean.Criticism;
import com.yt.bean.Message;
import com.yt.bean.Reply;
import com.yt.dao.CriticismDAO;
import com.yt.dao.MessageDAO;
import com.yt.dao.ReplyDAO;
import com.yt.factory.CriticismDAOFactory;
import com.yt.factory.MessageDAOFactory;
import com.yt.factory.ReplyDAOFactory;
import com.yt.util.Page;
import com.yt.util.PageUtil;

/**
 * Servlet implementation class GetMessage
 */
@WebServlet(name="GetMessage", urlPatterns="/GetMessage")
public class GetMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获得消息
		int messageID = Integer.parseInt(request.getParameter("messageID"));
		MessageDAO messageDAO = MessageDAOFactory.getMessageDAOInstance();
		Message message = messageDAO.findMessageById(messageID);
		request.setAttribute("message", message);
		// 获得消息回复
		int currentPage = 0;
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null || "".equals(currentPageStr)) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(currentPageStr);
		}
		ReplyDAO replyDAO = ReplyDAOFactory.getReplyDAOInstance();
		Page page = PageUtil.creatPage(5, replyDAO.findCountByMsgID(messageID), currentPage);
		List<Reply> replys = replyDAO.findReplyByMsgID(messageID, page);
		request.setAttribute("replys", replys);
		request.setAttribute("page", page);
		// 获得消息批复
		CriticismDAO criticismDAO = CriticismDAOFactory.getCriticismDAOInstance();
		Criticism criticism = criticismDAO.findCriticismByMsgID(messageID);
		request.setAttribute("criticism", criticism);
		// 跳转到消息显示页
		ServletContext servletContext = getServletContext();
		RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/showMsg.jsp");
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
