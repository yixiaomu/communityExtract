package cn.passionshark.project.community.core.util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.passionshark.project.community.common.log.Logger;
import cn.passionshark.project.community.common.log.LoggerFactory;
import cn.passionshark.project.community.common.util.NetUtil;
import cn.passionshark.project.community.common.util.SerialNoGenerator;
import cn.passionshark.project.community.core.entity.CommunitySerial;


/**
 * 序号号生成服务.
 *
 */
@Component
public class CommunitySerialNoService {
    private static final Logger logger = LoggerFactory.getLogger(CommunitySerialNoService.class);

    private SerialNoGenerator guid;
    @Autowired
    private cn.passionshark.project.community.core.dao.CommunitySerialDao CommunitySerialDao;

    public CommunitySerialNoService() {
    }

    @PostConstruct
    public void init() {
        String ipAddr = NetUtil.getLocalHost();
        logger.info("local ip: {}", new Object[] { ipAddr });
        Long workerId = CommunitySerialDao.getTradeMachineForIp(ipAddr);
        if (null == workerId) {
            CommunitySerial SettlementSerial = new CommunitySerial();
            SettlementSerial.setMachineAddr(ipAddr);
            CommunitySerialDao.add(SettlementSerial);
            workerId = SettlementSerial.getId();
        }
        logger.info("Cal worker id: {}", new Object[] { workerId });
        guid = new SerialNoGenerator(workerId);
    }

    @PreDestroy
    public void destroy() {
    }

    public String getSerialNo(String command) {
        return command + guid.nextNo();
    }
    
}
