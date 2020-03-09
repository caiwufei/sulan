package com.yilanjiaju.sulan.module.search.pojo;

import com.jcraft.jsch.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Data
@Slf4j
public class Shell {

    //远程主机的ip地址
    private String ip;
    //远程主机登录用户名
    private String username;
    //远程主机的登录密码
    private String password;
    //设置ssh连接的远程端口
    public int port = 22;
    //保存输出内容的容器
    private ArrayList<String> stdout = new ArrayList<>();


    public Session getSession(){
        JSch jsch = new JSch();
        try {
            //创建session并且打开连接，因为创建session之后要主动打开连接
            Session session = jsch.getSession(this.username, this.ip, this.port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();
            return session;
        } catch (JSchException e) {
            log.info("---------------------getSession exception == {}", e);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int exec(String command){
        Session session = null;
        ChannelExec channelExec = null;
        BufferedReader input = null;
        InputStreamReader inputStreamReader = null;
        try {
            //打开通道，设置通道类型，和执行的命令
            session = getSession();
            if(null==session){
                return -1;
            }
            channelExec = (ChannelExec)session.openChannel("exec");
            channelExec.setCommand(command);
            channelExec.setInputStream(null);
            inputStreamReader = new InputStreamReader(channelExec.getInputStream(), StandardCharsets.UTF_8);
            input = new BufferedReader(inputStreamReader);

            channelExec.connect();
            //接收远程服务器执行命令的结果
            String line;
            while ((line = input.readLine()) != null) {
                stdout.add(line);
            }
        } catch (Exception e) {
            log.info("---------------------shell exec exception == {}", e);
            e.printStackTrace();
        } finally {
            if(null!=input){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=channelExec){
                channelExec.disconnect();
                // 得到returnCode
                if (channelExec.isClosed()) {
                    return channelExec.getExitStatus();
                }
            }
            if(null!=inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=getSession()){
                getSession().disconnect();
            }
        }
        return -1;
    }

    /**
     * get stdout
     * @return
     */
    public ArrayList<String> getStandardOutput() {
        return stdout;
    }
}
