package cn.passionshark.project.community.core.enums;

/**
 * 证件类型
 * @author zhangmeiqing
 */
public enum IdTypeEnum {

	IDTYPE_IDCARD("111", "居民身份证"),IDTYPE_OFFICER("114", "军官证"),
	IDTYPE_FOREIGNER_TEMPORARY_RESIDENCE("555", "外国人临时居留证"),IDTYPE_OTHER_CORD("990", "其他证件"),IDTYPE_LEGAL_CARD("992", "法人证件"),
	IDTYPE_ORGANIZING_INSTITUTION_CODE("991", "组织机构代码"),IDTYPE_BUSINESS_LICENSE("889", "营业执照"),
	IDTYPE_RESIDENT_IDENTIFICATION_CARD("112", "临时居民身份证"),IDTYPE_TEMPORARY_RESIDENTIAL_PERMIT("116", "暂住证"),
	IDTYPE_STUDENT_CARD("133", "学生证"),IDTYPE_MOTOR_VEHICLE_DRIVING_LICENSE("335", "机动车驾驶证"),
	IDTYPE_DIPLOMATIC_PASSPORT("411", "外交护照"),IDTYPE_SERVICE_PASSPORT("412", "公务护照"),
	IDTYPE_ORDINARY_PASSPORT_AFFAIRS("413", "因公普通护照"),IDTYPE_ORDINARY_PASSPORT("414", "普通护照"),
	IDTYPE_TRAVEL_CERTIFICATE("415", "旅行证"),IDTYPE_ENTRY_EXIT_TRAFFIC_PERMIT("416", "入出境通行证"),
	IDTYPE_FOREIGNER_ENTRY_EXIT_TRAFFIC_PERMIT("417", "外国人出入境证"),
	IDTYPE_FOREIGNER_TRAVEL_CERTIFICATE("418", "外国人旅行证"),
	IDTYPE_HKSAR("420", "香港特别行政区护照"),IDTYPE_MACAO_SPECIAL_ADMINISTRATIVE_REGION("421", "澳门特别行政区护照"),
	IDTYPE_TANWAN_COME_TRAFFIC_PERMIT("511", "台湾居民来往大陆通行证"),
	IDTYPE_COME_GO_HK_MACAO("513", "往来港澳通行证"),IDTYPE_GO_HK_MACAO("515", "前往港澳通行证"),
	IDTYPE_HK_MACAO_TRAFFIC_PERMIT("516", "港澳同胞回乡证（通行卡）"),
	IDTYPE_MAINLAND_GO_TAIWAN_TRAFFIC_PERMIT("517", "大陆居民往来台湾通行证"),
	IDTYPE_BUSINESS_COME_GO_HK_MACAO_TRAFFIC_PERMIT("518", "因公往来香港澳门特别行政区通行证"),
	IDTYPE_BORDER_PRECINCT_TRAFFIC_PERMIT("711", "边境管理区通行证");

	private String code;
	private String desc;

	private IdTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static IdTypeEnum toEnum(String code) {
		for (IdTypeEnum category : IdTypeEnum.values()) {
			if (category.getCode().equals(code)) {
				return category;
			}
		}
		return null;
	}

	
	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}