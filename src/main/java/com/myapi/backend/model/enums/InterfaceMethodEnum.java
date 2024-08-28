package com.myapi.backend.model.enums;

public enum InterfaceMethodEnum {
    GET_USER_NAME_BY_POST("getUserNamebyPost"),
    GET_WEATHER_BY_CITY("getWeatherByCity"),
    CONVERT_CURRENCY("convertCurrency"),
    GET_CHINESE_RECIPES("getChineseRecipes");

    private final String methodName;

    InterfaceMethodEnum(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public static InterfaceMethodEnum getByMethodName(String methodName) {
        for (InterfaceMethodEnum value : InterfaceMethodEnum.values()) {
            if (value.getMethodName().equalsIgnoreCase(methodName)) {
                return value;
            }
        }
        return null;
    }
}

