package cn.passionshark.project.community.core.entity;

import java.util.Date;

import cn.passionshark.project.community.common.persistence.Entity;
import cn.passionshark.project.community.common.util.MaskSupport;


public class UserInfo implements Entity<Long>{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3542076987475999827L;

	private Long id;

    private String userId;

    private String userName;

    private Byte sex;

    private Byte idType;

    private String idNo;

    private String idPic;

    private String phone;

    private String mail;

    private String address;

    private Byte authLevel;

    private Byte status;

    private Byte source;

    private Date createTime;

    private Date updateTime;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getIdType() {
        return idType;
    }

    public void setIdType(Byte idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public String getIdPic() {
        return idPic;
    }

    public void setIdPic(String idPic) {
        this.idPic = idPic == null ? null : idPic.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Byte getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(Byte authLevel) {
        this.authLevel = authLevel;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", userId=" + userId + ", userName="
				+ userName + ", sex=" + sex + ", idType=" + idType + ", idNo="
				+ MaskSupport.maskIdInfo(idNo) + ", idPic=" + idPic + ", phone=" + MaskSupport.maskIdInfo(phone) + ", mail="
				+ mail + ", address=" + address + ", authLevel=" + authLevel
				+ ", status=" + status + ", source=" + source + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", remark="
				+ remark + "]";
	}
}