package cn.passionshark.project.community.core;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.passionshark.project.community.core.entity.UserInfo;
import cn.passionshark.project.community.core.service.UserInfoService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-*.xml")
public class UserInfoTest {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoTest.class);
    @Autowired
    private UserInfoService userInfoService;

    @Before
    public void setUp() throws Exception {
        logger.info("start...................");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("test end................");
    }
    
    @Test
    public void addTest(){
        logger.info(".......开始：");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("shy001");
        userInfo.setUserName("孙海洋");
        userInfo.setCreateTime(new Date());
        userInfoService.addUser(userInfo);
        logger.info(".......结果为：order={}",userInfo);
    }
}
