package com.yt.factory;

import com.yt.dao.EmployeeDAO;
import com.yt.daoImpl.EmployeeDAOImpl;

public class EmployeeDAOFactory {
	
	/**
	 * 工厂方法，返回EmployeeDAO实现类实例
	 * @return
	 */
	public static EmployeeDAO getEmployeeDAOInstance(){
		return new EmployeeDAOImpl();
	}

}
