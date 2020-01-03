package com.yilanjiaju.sulan.common.enums;

public class OprEnum {

    public static enum Account{
        create(1),
        edit(2),
        disable(3),
        enable(4);

        int value;

        Account(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}
