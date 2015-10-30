package cn.passionshark.project.community.core.entity;

import cn.passionshark.project.community.common.persistence.Entity;



/**
 * 序号.
 *
 */
public class CommunitySerial implements Entity<Long>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2060085699908858887L;
	
	private Long id;
    private String machineAddr;
    private String remark;

    public CommunitySerial() {
    }

    public String toString() {
        return "{\"id\":\"" + id + "\",\"machineMac\":\"" + machineAddr + "\",\"remark\":\"" + remark + "\"}  ";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMachineAddr() {
        return machineAddr;
    }

    public void setMachineAddr(String machineAddr) {
        this.machineAddr = machineAddr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
