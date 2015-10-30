package cn.passionshark.project.community.core.dto;

import constant.LengthConstant;
import cn.passionshark.project.community.common.util.MaskSupport;
import cn.passionshark.project.community.common.validate.Signable;
import cn.passionshark.project.community.common.validate.Validatable;
import cn.passionshark.project.community.core.enums.IdTypeEnum;


/**
 * @author zhao
 * @Description:快捷充值获取验证码接口参数封装 2015年7月6日
 */
public class RegisteredRequestDto extends BaseCommunityRequestDto {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6636371207966490996L;
	
	/*客户姓名*/
    @Validatable(value="客户姓名", maxLength=LengthConstant.CUSTOMERNAME_MAX_LENGTH, nullable=false)
    @Signable
    private String customerName;
    /*证件类型*/
    @Validatable(value="证件类型", enumScope = IdTypeEnum.class)
    @Signable
    private String idType;
    /*证件号码*/
    @Validatable(value="证件号码", maxLength=LengthConstant.IDNO_MAX_LENGTH, nullable=false)
    @Signable
    private String idNo;
    /*手机号*/
    @Validatable(value="手机号", maxLength=LengthConstant.PHONE_MAX_LENGTH, isPhone = true, nullable=false)
    @Signable
    private String phone;
    /*用户IP*/
    @Validatable(value="用户IP", maxLength=LengthConstant.USER_ID_MAX_LENGTH, nullable=false)
    @Signable
    private String userIp;


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }


    @Override
    public String toString() {
        return "RegisteredRequestDto [version=" + super.getVersion()
                + ", customerName=" + customerName + ", idType=" + idType + ", idNo=" + MaskSupport.maskIdInfo(idNo)
                + ", phone=" + MaskSupport.maskPhone(phone) + ", userIp=" + userIp + ", sign=" + super.getSign() + "]";
    }
}
