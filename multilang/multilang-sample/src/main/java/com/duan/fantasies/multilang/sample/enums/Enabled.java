package com.duan.fantasies.multilang.sample.enums;

/**
 * Created on 2018/10/15.
 *
 * @author DuanJiaNing
 */
public enum Enabled {

    /**
     * 启用(是)
     */
    ENABLE(1),

    /**
     * 不启用(非)
     */
    DISABLE(0);

    private final Integer code;

    Enabled(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static boolean enable(Integer code) {
        return ENABLE.getCode().equals(code);
    }

    public static Enabled valueOf(Integer code) {
        if (code == null)
            return null;

        for (Enabled enabled : Enabled.values()) {
            if (enabled.getCode().equals(code))
                return enabled;
        }

        return null;
    }
}
