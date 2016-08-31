package com.yt.dao;

import com.yt.bean.Criticism;

public interface CriticismDAO {
	public void addCriticism(Criticism criticism); // 添加批复

	public Criticism findCriticismByMsgID(int messageID); // 按消息ID查找批复

}
