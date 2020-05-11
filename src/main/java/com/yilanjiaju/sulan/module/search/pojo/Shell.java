package com.yilanjiaju.sulan.module.search.pojo;

import com.jcraft.jsch.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> stdout = new ArrayList<>();

    public void exec(String command){
        ChannelExec channelExec = null;
        InputStream inputStream = null;
        Session session = null;
        try {
            //创建session并且打开连接
            JSch jsch = new JSch();
            session = jsch.getSession(this.username, this.ip, this.port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            //打开通道，设置通道类型，和执行的命令
            channelExec = (ChannelExec)session.openChannel("exec");
            channelExec.setInputStream(null);
            channelExec.setErrStream(System.err);

            channelExec.setCommand(command);
            inputStream = channelExec.getInputStream();
            channelExec.connect();
            stdout = IOUtils.readLines(inputStream,  StandardCharsets.UTF_8);

        } catch (Exception e) {
            log.info("---------------------shell exec exception == {}", e);
            e.printStackTrace();
        } finally {
            if (null!=inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null!=channelExec) {
                try {
                    channelExec.getSession().disconnect();
                } catch (JSchException e) {
                    e.printStackTrace();
                }
                channelExec.disconnect();
            }
            if(null!=session){
                session.disconnect();
            }
        }
    }

}
