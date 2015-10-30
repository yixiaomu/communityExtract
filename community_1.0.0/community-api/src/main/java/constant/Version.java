/**
 * Copyright (C) 2015 Creditease All rights reserved.
 */
package constant;

/**
 * Represents a version number plus the further qualifiers and build into.
 *
 * @author pierre
 * @version $ v1.0 June 25, 2015 $
 */
public enum Version {
    V_20150616("20150616", "Init version");

    private final String code;
    private final String desc;

    Version(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Version toEnum(String version) {
        for (Version ver : Version.values()) {
            if (ver.getCode().equals(version)) {
                return ver;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
