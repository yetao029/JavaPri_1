package com.yt.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.yt.bean.Message;
import com.yt.dao.MessageDAO;
import com.yt.util.DBConnection;
import com.yt.util.Page;

public class MessageDAOImpl implements MessageDAO {

	@Override
	public void addMessage(Message message) {
		Connection conn = DBConnection.getConnection();
		String addMessageSQL = "insert into tb_message (messageTitle,messageContent,employeeID,publishTime) values (?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(addMessageSQL);
			pstmt.setString(1, message.getMessageTitle());
			pstmt.setString(2, message.getMessageContent());
			pstmt.setInt(3, message.getEmployeeID());
			pstmt.setTimestamp(4, new Timestamp(message.getPublishTime()
					.getTime()));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

	}

	@Override
	public void updateMessage(Message message) {
		Connection conn = DBConnection.getConnection();
		String updateMessageSQL = "update tb_message set messageTitle = ?,messageContent = ?,employeeID = ?,publishTime = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(updateMessageSQL);
			pstmt.setString(1, message.getMessageTitle());
			pstmt.setString(2, message.getMessageContent());
			pstmt.setInt(3, message.getEmployeeID());
			pstmt.setTimestamp(4, new Timestamp(message.getPublishTime()
					.getTime()));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

	}

	@Override
	public void deleteMessage(int messageID) {
		Connection conn = DBConnection.getConnection();
		String deleteMessageSQL = "delete from tb_message where messageID = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(deleteMessageSQL);
			pstmt.setInt(1, messageID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
	}

	@Override
	public List<Message> findAllMessagee(Page page) {
		Connection conn = DBConnection.getConnection();
		String findAllMessageSQL = "select * from tb_message order by publishTime desc limit ?,?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Message> messages = new ArrayList<Message>();
		try {
			pstmt = conn.prepareStatement(findAllMessageSQL);
			pstmt.setInt(1, page.getBeginIndex());
			pstmt.setInt(2, page.getEveryPage());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Message message = new Message();
				message.setMessageID(rs.getInt(1));
				message.setMessageTitle(rs.getString(2));
				message.setMessageContent(rs.getString(3));
				message.setEmployeeID(rs.getInt(4));
				message.setPublishTime(rs.getTimestamp(5));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

		return messages;
	}

	@Override
	public Message findMessageById(int messageID) {
		Connection conn = DBConnection.getConnection();
		String findMessageByIdSQL = "select * from tb_message where messageID = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Message message = null;
		try {
			pstmt = conn.prepareStatement(findMessageByIdSQL);
			pstmt.setInt(1, messageID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				message = new Message();
				message.setMessageID(rs.getInt(1));
				message.setMessageTitle(rs.getString(2));
				message.setMessageContent(rs.getString(3));
				message.setEmployeeID(rs.getInt(4));
				message.setPublishTime(rs.getTimestamp(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

		return message;
	}

	@Override
	public int findAllCount() {
		Connection conn = DBConnection.getConnection();
		String findAllCountSQL = "select count(*) from tb_message";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(findAllCountSQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}
