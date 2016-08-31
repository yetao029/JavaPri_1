package com.yt.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.yt.bean.Reply;
import com.yt.dao.ReplyDAO;
import com.yt.util.DBConnection;
import com.yt.util.Page;

public class ReplyDAOImpl implements ReplyDAO {

	@Override
	public void addReply(Reply reply) {
		Connection conn = DBConnection.getConnection();
		String addReplaySQL = "insert into tb_reply(replyContent,employeeID,replyTime,messageID) values (?,?,?,?)";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(addReplaySQL);
			pstmt.setString(1, reply.getReplyContent());
			pstmt.setInt(2, reply.getEmployeeID());
			pstmt.setTimestamp(3, new Timestamp(reply.getReplyTime().getTime()));
			pstmt.setInt(4, reply.getMessageID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

	}

	@Override
	public List<Reply> findReplyByMsgID(int messageID, Page page) {
		Connection conn = DBConnection.getConnection();
		String findReplyByMsgIDSQL = "select * from tb_reply where messageID = ? limit ?,?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Reply> replys = new ArrayList<Reply>();
		try {
			pstmt = conn.prepareStatement(findReplyByMsgIDSQL);
			pstmt.setInt(1, messageID);
			pstmt.setInt(2, page.getBeginIndex());
			pstmt.setInt(3, page.getEveryPage());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Reply reply = new Reply();
				reply.setReplyID(rs.getInt(1));
				reply.setReplyContent(rs.getString(2));
				reply.setEmployeeID(rs.getInt(3));
				reply.setReplyTime(rs.getTimestamp(4));
				reply.setMessageID(rs.getInt(5));
				replys.add(reply);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(rs);
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return replys;
	}

	@Override
	public int findCountByMsgID(int messageID) {
		Connection conn = DBConnection.getConnection();
		String findCountByMsgIDSQL = "select count(*) from tb_reply where messageID = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(findCountByMsgIDSQL);
			pstmt.setInt(1, messageID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}
