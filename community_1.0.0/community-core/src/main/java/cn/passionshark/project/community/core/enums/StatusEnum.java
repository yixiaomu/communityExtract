package cn.passionshark.project.community.core.enums;

/**
 * 狀态status的枚举
 * 
 * @author chen
 *
 */
public enum StatusEnum {
    
    /**
     * 未处理
     */
    STATUS_UNPROCESSED(0, "未处理"), 

    /**
     * 处理中
     */
    STATUS_PROCESSING(1, "处理中"), 
    /**
     * 成功
     */
    STATUS_SUCCESS(2, "成功"), 
    /**
     * 失败
     */
    STATUS_FAILED(3, "失败");

    private Integer code;
    private String desc;

    private StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String toDesc(Integer code) {
        for (StatusEnum category : StatusEnum.values()) {
            if (category.getCode().equals(code)) {
                return category.getDesc();
            }
        }
        return null;
    }

    public static StatusEnum toEnum(Integer code) {
        for (StatusEnum category : StatusEnum.values()) {
            if (category.getCode().equals(code)) {
                return category;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
