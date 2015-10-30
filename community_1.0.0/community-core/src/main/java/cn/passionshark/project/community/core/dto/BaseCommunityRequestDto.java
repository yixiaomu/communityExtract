package cn.passionshark.project.community.core.dto;

import java.io.Serializable;

import cn.passionshark.project.community.common.validate.Signable;
import cn.passionshark.project.community.common.validate.Validatable;
import constant.LengthConstant;
import constant.Version;

public class BaseCommunityRequestDto implements Serializable {

    private static final long serialVersionUID = -550088676899195495L;

    @Validatable(value = "版本号", enumScope = Version.class)
    @Signable
    private String version;

    @Validatable(value = "签名", maxLength = LengthConstant.SIGN_MAX_LENGTH)
    private String sign;

    public BaseCommunityRequestDto() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

	@Override
	public String toString() {
		return "BaseCommunityRequestDto [version=" + version + ", sign=" + sign
				+ "]";
	}
   
}
