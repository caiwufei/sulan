package com.yilanjiaju.sulan.common.enums;

public class UserEnum {

    public static enum Status {
        ok(1, "正常"),
        disable(2, "失效"),
        expired(3, "过期"),
        delete(4, "删除");

        int value;
        String msg;

        Status(int value, String msg){
            this.value = value;
            this.msg = msg;
        }

        public int getValue() {
            return value;
        }

        public String getMsg() {return msg;}
    }

    public static enum Type{
        admin(1),
        query(2);

        int value;

        Type(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static enum LoginCode{
        error(11, "用户名或者密码错误"),
        inefficacy(12, "用户已失效，请联系管理员"),
        ok(0, "登陆成功");

        int value;
        String desc;

        LoginCode(int value, String desc){
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }
    }


    public static enum ApplyOrgAuth{
        acked(1, "已确认"),
        no_apply(2, "未申请"),
        no_ack(3, "已申请，未确认");

        int value;
        String desc;

        ApplyOrgAuth(int value, String desc){
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }
    }


}
