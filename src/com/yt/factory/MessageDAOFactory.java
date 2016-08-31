package com.yt.factory;

import com.yt.dao.MessageDAO;
import com.yt.daoImpl.MessageDAOImpl;

public class MessageDAOFactory {
	
	/**
	 * 工厂方法，返回MessageDAO实现类实例
	 * @return
	 */
	public static MessageDAO getMessageDAOInstance(){
		return new MessageDAOImpl();
	}

}
