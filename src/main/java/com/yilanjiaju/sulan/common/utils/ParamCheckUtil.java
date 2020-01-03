package com.yilanjiaju.sulan.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auhor: Aniskin
 * @Description:
 * @Date:by 15:30 2017/5/17
 * @Modified by:
 */

public class ParamCheckUtil {
    /**
     * 是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;

    }

    /**
     * 是否非空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 是否为0或者正整数
     * 不带符号位
     * @param str
     * @return
     */
    public static boolean isZeroPosInt(String str){
        if (isNull(str)) return false;
        String regex="[0-9]+";
        return str.matches(regex);
    }

    /**
     * 是否为正整数
     * 不带符号位
     * @param str
     * @return
     */
    public static boolean isPosInt(String str){
        if (isNull(str)) return false;
        String regex="^[1-9]\\d*$";
        return str.matches(regex);
    }

    /**
     * 是否数字
     * 包括正负整数、小数
     * @param str
     * @return
     */
    public static boolean isNumberic(String str){
        if (isNull(str)) return false;
        String regex="^[+-]?\\d+(\\.\\d+)?$";
        return str.matches(regex);
    }

    /**
     * 是否为日期类型的字符串
     * YYYY-MM-DD格式
     * @param str
     * @return
     */
    public static boolean isShortDateString(String str) {
        if (isNull(str)) return false;
        String regex = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
        return str.matches(regex);
    }

    /**
     * 是否为QQ号
     * @param str
     * @return
     */
    public static boolean isQQNumber(String str){
        if (isNull(str)) return false;
        String regex="^[1-9]\\d{5,12}$";
        return str.matches(regex);
    }

    /**
     * 是否为手机号码
     * @param str
     * @return
     */
    public static boolean isTelephoneNumber(String str){
        if (isNull(str)) return false;
        String regex="^1\\d{10}$";
        return str.matches(regex);
    }

    /**
     * 是否为邮箱
     * @param str
     * @return
     */
    public static boolean isEmail(String str){
        if (isNull(str)) return false;
        String regex="^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        return str.matches(regex);
    }


    public static boolean isNull(String str){
        return str == null;
    }

    /**
     * 是否为组合形式的字符串，如：
     * 1|2|3|4|5
     * 第一位一定不为分隔符，最后一位一定不为分隔符
     * 符合条件的字符串一定为奇数长度
     * 偶数位一定为数字，奇数位一定为分隔符
     *
     * @param str
     * @param begin 数字的最小值
     * @param end   数字的最大值
     * @param split 分割符，例子中为“|”
     * @return
     */
    public static boolean isCombine(String str, int begin, int end, String split) {
        if (StringUtils.isEmpty(str)) return false;
        try{
            String itemArray[] = str.split(split);
            int tempInt = 0;
            for (String item : itemArray) {
                try {
                    tempInt = Integer.parseInt(item);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false;
                }

                if (tempInt < begin || tempInt > end) return false;
                else continue;

            }
            return true;
        }catch (Exception e){
            return false;
        }

    }

    /**
     * 判断中英文
     * @param regex
     * @param str
     * @return
     */
    public static boolean isChineseEnglishName(String regex,String str){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 反射校验参数
     * @param t
     * @param params
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static List<String> checkParam(Object t, String... params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // 如果传入的对象为空，则直接抛出异常
        if (t == null) {
            throw new IllegalArgumentException("The object checked cann't be null!");
        }
        if (null==params || 0==params.length) {
            throw new IllegalArgumentException("The param name cann't be null!");
        }

        List<String> nullKeys = new ArrayList<>();
        for (String arg : params) {
            Field field = ReflectionUtils.findField(t.getClass(), arg);
            field.setAccessible(true);
            Object value = field.get(t);
            if(null==value){
                nullKeys.add(arg);
            }
            if(value instanceof String && StringUtils.isBlank((String) value)){
                nullKeys.add(arg);
            }
            if(value instanceof List && ((List) value).isEmpty()){
                nullKeys.add(arg);
            }
            if(value instanceof Map && ((Map) value).isEmpty()){
                nullKeys.add(arg);
            }
        }
        return nullKeys;
    }


}
