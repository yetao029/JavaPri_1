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

import com.yt.bean.Message;
import com.yt.dao.MessageDAO;
import com.yt.factory.MessageDAOFactory;
import com.yt.util.Page;
import com.yt.util.PageUtil;

/**
 * Servlet implementation class GetMessageList
 */
@WebServlet(name="GetMessageList", urlPatterns="/GetMessageList")
public class GetMessageList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int currentPage=0;
		String currentPageStr=request.getParameter("currentPage");
		if(currentPageStr==null||"".equals(currentPageStr)){
			currentPage=1;
		}else{
			currentPage=Integer.parseInt(currentPageStr);
		}
		MessageDAO messageDAO=MessageDAOFactory.getMessageDAOInstance();
		Page page=PageUtil.creatPage(5, messageDAO.findAllCount(), currentPage);
		List<Message>messages=messageDAO.findAllMessagee(page);
		request.setAttribute("messages", messages);
		request.setAttribute("page", page);
		ServletContext servletContext=getServletContext();
		RequestDispatcher dispatcher=servletContext.getRequestDispatcher("/msgList.jsp");
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
