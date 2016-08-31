package com.yt.factory;

import com.yt.dao.ReplyDAO;
import com.yt.daoImpl.ReplyDAOImpl;

public class ReplyDAOFactory {

	/**
	 * 工厂方法，返回ReplyDAO实现类实例
	 * @return
	 */
	public static ReplyDAO getReplyDAOInstance() {
		return new ReplyDAOImpl();
	}

}
