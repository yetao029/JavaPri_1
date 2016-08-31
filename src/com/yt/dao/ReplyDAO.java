package com.yt.dao;

import java.util.List;

import com.yt.bean.Reply;
import com.yt.util.Page;

public interface ReplyDAO {
	public void addReply(Reply reply); // 添加回复

	public List<Reply> findReplyByMsgID(int messageID, Page page);// 按消息ID查找回复

	public int findCountByMsgID(int messageID);// 查询消息回复记录数

}
