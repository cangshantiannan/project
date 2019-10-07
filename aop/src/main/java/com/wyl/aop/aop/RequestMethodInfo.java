/**
 * @Author wangyl
 * @E-mail wangyl@dsgdata.com
 **/
package com.wyl.aop.aop;

import com.wyl.aop.util.RequestTools;
import org.apache.catalina.User;
import org.apache.catalina.connector.Request;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.UnknownHostException;

/**
 * @author wyl
 * @version V1.0
 * @ClassName: RequestMethodInfo
 * @Function: TODO
 * @Date: 2019/9/22 19:07
 */
@Aspect
@Component
public class RequestMethodInfo {
    public Logger logger = LoggerFactory.getLogger(RequestMethodInfo.class);
//    @Pointcut("@annotation(com.wyl.aop.annotation.RequestInfo) ")
    @Pointcut("execution (* com.wyl.test.controller..*.*(..))")
    public void MethodInfo() {
        logger.info("get Method Info");
        System.out.println(111);
    }

    /**
     * @Description 请求执行之前 记录用户和ip
     * @Date 2019/9/22 19:59
     * @Param
     * @return
     * @Author wangyl
     * @Version    V1.0
     */
    @Before("MethodInfo()")
    public void BeforeMethodInfo(JoinPoint joinPoint) throws UnknownHostException {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("SPRING_SECURITY_CONTEXT");
//        if(user!=null)
//        {
//            logger.info("username is {}",user.getUsername());
//        }
//        String ip = RequestTools.REQUEST.GetRequestIp(request);
        logger.info("remort ip is {}","10");
    }

    /**
     * @Description 请求执行成功之后记录参数和操作
     * @Date 2019/9/22 20:00
     * @Param
     * @return
     * @Author wangyl
     * @Version    V1.0
     */
    @After("MethodInfo()")
    public void AfterMethodInfo(JoinPoint joinPoint)
    {

    }
}
