package com.yilanjiaju.log.common.utils;

import com.alibaba.fastjson.JSON;
import com.yilanjiaju.log.common.AppContext;
import com.yilanjiaju.log.common.base.ResponseVO;
import com.yilanjiaju.log.common.enums.UserEnum;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 *   -1 程序异常
 *   0  参数校验不通过
 *   1  请求响应正常（程序正常执行整个流程，无拦截，无报错，在查询的时候，返回空{}或者[]，也表示响应正常）
 *   2 无认证的禁止接入
 *   3 无权限的禁止接入
 */
public class ResponseUtil {

    public static ResponseVO errorCheck(){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(1);
        responseVO.setMsg("必填参数不能为空");
        responseVO.setRequestId(AppContext.getRequestLogId());
        return responseVO;
    }

    public static ResponseVO errorRun(){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(-1);
        responseVO.setMsg("异常，请稍后再重试");
        responseVO.setRequestId(AppContext.getRequestLogId());
        return responseVO;
    }

    public static boolean errorRun(HttpServletResponse response) throws IOException {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(-1);
        responseVO.setMsg("异常，请稍后再重试");
        responseVO.setRequestId(AppContext.getRequestLogId());

        ResponseUtil.writeResponseData(response, responseVO);
        return false;
    }

    public static ResponseVO success(){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(0);
        responseVO.setMsg("OK");
        responseVO.setRequestId(AppContext.getRequestLogId());
        return responseVO;
    }

    public static ResponseVO success(Object o){
        ResponseVO responseVO = success();
        responseVO.setData(o);
        return responseVO;
    }

    public static ResponseVO success(String datakey, Object datavalue){
        HashMap<String, Object> data = new HashMap<>();
        data.put(datakey, datavalue);
        return success(data);
    }

    /**
     * 强调会话失效
     * @return
     */
    public static boolean invalidAccess(HttpServletResponse response) throws IOException {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setRequestId(AppContext.getRequestLogId());
        responseVO.setCode(-99);
        responseVO.setMsg("会话失效，请重新登陆");

        ResponseUtil.writeResponseData(response, responseVO);
        return false;
    }

    /**
     * 强调权限禁止
     * @return
     */
    public static boolean forbiddenAccess(HttpServletResponse response) throws IOException {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setRequestId(AppContext.getRequestLogId());
        responseVO.setCode(3);
        responseVO.setMsg("请求失败，无权限请求该资源");

        ResponseUtil.writeResponseData(response, responseVO);
        return false;
    }

    public static void writeResponseData(HttpServletResponse response, ResponseVO vo) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter();){
            out.append(JSON.toJSONString(vo));
            out.flush();
        }
        catch (IOException e){
            e.printStackTrace();
            response.sendError(500);
        }
    }


    public static ResponseVO loginFailForAccountOrPasswordMistake(Object object){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(UserEnum.LoginCode.error.getValue());
        responseVO.setMsg("用户名或者密码错误");
        responseVO.setData(object);
        responseVO.setRequestId(AppContext.getRequestLogId());
        return responseVO;
    }

    public static ResponseVO loginFailForUserExpire(Object object){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(UserEnum.LoginCode.inefficacy.getValue());
        responseVO.setMsg("用户已失效");
        responseVO.setData(object);
        responseVO.setRequestId(AppContext.getRequestLogId());
        return responseVO;
    }

    public static ResponseVO loginSuccess(Object object){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(UserEnum.LoginCode.ok.getValue());
        responseVO.setMsg("登陆成功");
        responseVO.setData(object);
        responseVO.setRequestId(AppContext.getRequestLogId());
        return responseVO;
    }
    //1用户名或者密码错误，2用户已失效，请联系管理员3登陆成功

    public static ResponseVO response(int code, String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(code);
        responseVO.setMsg(msg);
        responseVO.setRequestId(AppContext.getRequestLogId());
        return responseVO;
    }
}
