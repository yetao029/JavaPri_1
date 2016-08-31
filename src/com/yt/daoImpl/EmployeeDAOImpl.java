package com.yt.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.yt.bean.Employee;
import com.yt.dao.EmployeeDAO;
import com.yt.util.DBConnection;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public void addEmployee(Employee employee) {
		Connection conn = DBConnection.getConnection();
		String addEmployeeSQL = "insert into tb_employee (employeeName,employeeSex,employeeBirth,employeePhone,employeePlace,joinTime,password,isLead) values (?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(addEmployeeSQL);
			pstmt.setString(1, employee.getEmployeeName());
			pstmt.setBoolean(2, employee.isEmployeeSex());
			pstmt.setTimestamp(3, new Timestamp(employee.getEmployeeBirth()
					.getTime()));
			pstmt.setString(4, employee.getEmployeePhone());
			pstmt.setString(5, employee.getEmployeePlace());
			pstmt.setTimestamp(6, new Timestamp(employee.getJoinTime()
					.getTime()));
			pstmt.setString(7, employee.getPassword());
			pstmt.setBoolean(8, employee.isLead());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

	}

	@Override
	public void updateEmployee(Employee employee) {
		Connection conn = DBConnection.getConnection();
		String updateEmployeeSQL = "update tb_employee set employeeName = ?,employeeSex = ?,employeeBirth = ?,employeePhone = ?,employeePlace = ?,joinTime = ?,password = ?,isLead = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(updateEmployeeSQL);
			pstmt.setString(1, employee.getEmployeeName());
			pstmt.setBoolean(2, employee.isEmployeeSex());
			pstmt.setTimestamp(3, new Timestamp(employee.getEmployeeBirth()
					.getTime()));
			pstmt.setString(4, employee.getEmployeePhone());
			pstmt.setString(5, employee.getEmployeePlace());
			pstmt.setTimestamp(6, new Timestamp(employee.getJoinTime()
					.getTime()));
			pstmt.setString(7, employee.getPassword());
			pstmt.setBoolean(8, employee.isLead());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

	}

	@Override
	public void deleteEmployee(int employeeID) {
		Connection conn = DBConnection.getConnection();
		String deleteEmployeeSQL = "delete from tb_employee where employeeID = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(deleteEmployeeSQL);
			pstmt.setInt(1, employeeID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

	}

	@Override
	public List<Employee> findAllEmployee() {
		Connection conn = DBConnection.getConnection();
		String findAllMessageSQL = "select * from tb_employee";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Employee> employees = new ArrayList<Employee>();
		try {
			pstmt = conn.prepareStatement(findAllMessageSQL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setEmployeeID(rs.getInt(1));
				employee.setEmployeeName(rs.getString(2));
				employee.setEmployeeSex(rs.getBoolean(3));
				employee.setEmployeeBirth(rs.getDate(4));
				employee.setEmployeePhone(rs.getString(5));
				employee.setEmployeePlace(rs.getString(6));
				employee.setJoinTime(rs.getDate(7));
				employee.setPassword(rs.getString(8));
				employee.setLead(rs.getBoolean(9));
				employees.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

		return employees;
	}

	@SuppressWarnings("null")
	@Override
	public Employee findEmployeeById(int employeeID) {
		Connection conn = DBConnection.getConnection();
		String findEmployeeByIdSQL = "select * from tb_employee where employeeID = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Employee employee = null;
		try {
			pstmt = conn.prepareCall(findEmployeeByIdSQL);
			pstmt.setInt(1, employeeID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				employee.setEmployeeID(rs.getInt(1));
				employee.setEmployeeName(rs.getString(2));
				employee.setEmployeeSex(rs.getBoolean(3));
				employee.setEmployeeBirth(rs.getDate(4));
				employee.setEmployeePhone(rs.getString(5));
				employee.setEmployeePlace(rs.getString(6));
				employee.setJoinTime(rs.getDate(7));
				employee.setPassword(rs.getString(8));
				employee.setLead(rs.getBoolean(9));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return employee;
	}

}
