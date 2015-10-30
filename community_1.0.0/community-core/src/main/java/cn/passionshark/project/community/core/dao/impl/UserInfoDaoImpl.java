package cn.passionshark.project.community.core.dao.impl;

import org.springframework.stereotype.Repository;

import cn.passionshark.project.community.common.persistence.GenericDaoDefault;
import cn.passionshark.project.community.core.dao.UserInfoDao;
import cn.passionshark.project.community.core.entity.UserInfo;

@Repository
public class UserInfoDaoImpl extends GenericDaoDefault<UserInfo> implements UserInfoDao {

}
