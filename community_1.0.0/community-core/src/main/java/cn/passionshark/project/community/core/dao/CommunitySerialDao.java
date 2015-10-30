/**
 */
package cn.passionshark.project.community.core.dao;

import cn.passionshark.project.community.common.persistence.GenericDao;
import cn.passionshark.project.community.core.entity.CommunitySerial;

/**
 * 账务序号Dao.
 */
public interface CommunitySerialDao extends GenericDao<CommunitySerial> {
    /**
     * 查询ip地址所对应的序号.
     *
     * @param ipAddr
     * @return
     */
    Long getTradeMachineForIp(String ipAddr);
}
