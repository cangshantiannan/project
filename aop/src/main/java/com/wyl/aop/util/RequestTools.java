/**
 * @Author wangyl
 * @E-mail wangyl@dsgdata.com
 **/
package com.wyl.aop.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public enum RequestTools {
    /**/
    REQUEST;

    /**
     * @Description 获取请求的ip地址
     * @Date 2019/9/22 23:53
     * @Param
     * @Author wangyl
     * @Version V1.0
     */
    public String GetRequestIp(HttpServletRequest request) throws UnknownHostException {
        String ip = "";
        String X_FORWARDED_FOR = "x-forwarded-for";
        String LOCALHOST = "127.0.0.1";
        if (request.getHeader(X_FORWARDED_FOR) == null) {
            ip = request.getRemoteAddr();
            if (ip.equals(LOCALHOST)) {
                InetAddress inet = InetAddress.getLocalHost();
                ip = inet.getHostAddress();
            }
        } else {
            ip = request.getHeader(X_FORWARDED_FOR);
            // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ip.length() > 15 && ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

}
