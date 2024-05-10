package com.group1.system.exception;


import com.group1.system.vo.RespBean;
import com.group1.system.vo.RespBeanEnum;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * PermissionException Class:
 * A global exception handler class that intercepts security-related exceptions within the application.
 * Utilizing Spring's {@link ControllerAdvice}, this class centralizes the handling of exceptions related to
 * authorization and authentication processes managed by Apache Shiro framework. By annotating methods with
 * {@link ExceptionHandler}, it provides a way to uniformly respond to specific exceptions thrown during
 * HTTP requests.
 *
 * <p>Two types of security exceptions are handled:</p>
 *
 * <ul>
 *   <li>{@link UnauthorizedException} - Catches exceptions when a user tries to access a resource or perform
 *       an action without the necessary permissions. </li>
 *   <li>{@link AuthenticationException} - Catches exceptions related to the failure of authentication processes,
 *       such as incorrect login credentials. </li>
 * </ul>
 *
 * @see org.apache.shiro.authc.AuthenticationException for exceptions related to authentication process failures.
 * @see org.apache.shiro.authz.UnauthorizedException for exceptions related to unauthorized access attempts.
 *
 * @Author Ronghui Zhong
 * @date 2024/3/31 16:00
 * @ProjectName Dyson Student Management System
 **/
@ControllerAdvice
public class PermissionException {

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public RespBean unauthorizedException(Exception e) {
        return RespBean.error(RespBeanEnum.NOT_AUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public RespBean authenticatedException(Exception e) {
        return RespBean.error(RespBeanEnum.NOT_AUTHENTICATED);
    }
}
