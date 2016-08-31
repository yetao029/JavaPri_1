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

import com.yt.bean.Criticism;
import com.yt.bean.Employee;
import com.yt.dao.CriticismDAO;
import com.yt.factory.CriticismDAOFactory;

/**
 * Servlet implementation class CommitCriticism
 */
@WebServlet(name="CommitCriticism", urlPatterns="/CommitCriticism")
public class CommitCriticism extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		String criticismContent=request.getParameter("critircismContent");
		int messageID=Integer.parseInt(request.getParameter("messageID"));
		Employee employee=(Employee) request.getSession().getAttribute("employee");
		ServletContext context=getServletContext();
		RequestDispatcher dispatcher=null;
		if(employee==null){
			request.setAttribute("error", "要进行批复首先必须进行身份识别");
		}else{
			if(criticismContent==null||"".equals(criticismContent)){
				request.setAttribute("error", "必须输入批复内容");
			}else{
				Criticism criticism=new Criticism();
				criticism.setCriticismContent(criticismContent);
				criticism.setEmployeeID(employee.getEmployeeID());
				criticism.setMessageID(messageID);
				criticism.setCriticismTime(new Date());
				CriticismDAO criticismDAO=CriticismDAOFactory.getCriticismDAOInstance();
				criticismDAO.addCriticism(criticism);
				
			}
		}
		dispatcher = context.
				getRequestDispatcher("/GetMessage?messageID=" + messageID);//跳转回消息查看页
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
