/**
 */
package cn.passionshark.project.community.core.dao.impl;

import org.springframework.stereotype.Repository;

import cn.passionshark.project.community.common.persistence.GenericDaoDefault;
import cn.passionshark.project.community.common.util.StringUtil;
import cn.passionshark.project.community.core.dao.CommunitySerialDao;
import cn.passionshark.project.community.core.entity.CommunitySerial;

/**
 * 序号Dao.
 *
 * @author pierre
 * @version $ v1.0 June 25, 2015 $
 */
@Repository
public class CommunitySerialDaoImpl extends GenericDaoDefault<CommunitySerial> implements CommunitySerialDao {

    public Long getTradeMachineForIp(String ipAddr) {
        if (StringUtil.isEmpty(ipAddr)) {
            throw new IllegalArgumentException("Ip Address is empty.");
        }
        return (Long) queryOne("getTradeMachineForIp", ipAddr);
    }
}
