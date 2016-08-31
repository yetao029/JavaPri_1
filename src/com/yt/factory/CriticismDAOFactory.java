package com.yt.factory;

import com.yt.dao.CriticismDAO;
import com.yt.daoImpl.CriticismDAOImpl;

public class CriticismDAOFactory {
	
	/**
	 * 工厂方法，返回CriticismDAO实现类实例
	 * @return
	 */
	public static CriticismDAO getCriticismDAOInstance() {
		return new CriticismDAOImpl(); 
	}

}
