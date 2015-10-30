package cn.passionshark.project.community.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.passionshark.project.community.core.dao.UserInfoDao;
import cn.passionshark.project.community.core.entity.UserInfo;
import cn.passionshark.project.community.core.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	private UserInfoDao userInfoDao; 
	
	public int addUser(UserInfo userInfo) {
		
		return userInfoDao.add(userInfo);
	}

}
