package com.yilanjiaju.log.common.utils;

import java.util.Random;
import java.util.UUID;

public class CommonUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generatePassword(int length){
        String[] symbol = {"!","@","#","$","%","&","*"};
        // 最终生成的密码
        String password = "";
        Random random = new Random();
        boolean hasSymbol = false;
        for (int i = 1; i <= length; i ++) {
            // 随机生成0或1，用来确定是当前使用数字还是字母 (0则输出数字，1则输出字母, 3输出符号)
            int charOrNum = random.nextInt(hasSymbol ? 3 : 4);
            if (charOrNum == 1 || charOrNum==0) {
                // 随机生成0或1，用来判断是大写字母还是小写字母 (0则输出小写字母，1则输出大写字母)
                int temp = random.nextInt(2) == 1 ? 65 : 97;
                password += (char) (random.nextInt(26) + temp);
            }
            if (charOrNum == 2){
                // 生成随机数字
                password += random.nextInt(10);
            }
            if (charOrNum == 3){
                password += symbol[random.nextInt(i%length==0 ? i%length+1 : i%length)];
                hasSymbol = true;
            }
        }
        return password;
    }

    public static void main(String[] args) {
        System.out.println(        uuid());
    }

}
