package com.yunang.fangda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunang.fangda.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LD
 * 全局错误处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final ResponseResult<String> result = new ResponseResult<>(false, "");

    /**
     * 统一异常处理
     *
     * @param request
     * @param response
     * @param exception
     * @throws Exception
     */
    @ExceptionHandler
    public void exception(HttpServletRequest request,
                          HttpServletResponse response,
                          Exception exception) throws Exception {
//        exception.printStackTrace();
//        log.debug("ERROR::::：" + exception.getLocalizedMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getCause() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getSuppressed()) + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getStackTrace()) + "::::::" + new Date());
        //        权限不足
        if ((exception instanceof UnauthorizedException) || (exception instanceof AuthorizationException)) {
            res(response, "权限不足");
            exception.printStackTrace();
            return;
        }
//        文件不存在
        if (exception instanceof FileNotFoundException) {
            res(response, "文件不存在");
            exception.printStackTrace();
            return;
        }
        if (exception instanceof HttpMessageNotReadableException) {
            res(response, "参数序列化异常，请仔细对比参数的类型");
            exception.printStackTrace();
            return;
        }
        res(response, "未定义的的其它错误");
        exception.printStackTrace();
    }


//    /**
//     * 文件不存在
//     *
//     * @param request
//     * @param response
//     * @param exception
//     * @throws Exception
//     */
//    @ExceptionHandler(value = TemplateInputException.class)
//    public void templateInputException(HttpServletRequest request,
//                                       HttpServletResponse response,
//                                       Exception exception) throws Exception {
//        //        exception.printStackTrace();
//        log.debug("ERROR::::：" + exception.getLocalizedMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getCause() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getSuppressed()) + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getStackTrace()) + "::::::" + new Date());
////        boolean b = isAjaxRequest(request);
////        if (b) {
//        res(response, "系统找不到指定的文件夹");
////        } else {
////            response.sendRedirect("/views/error/500");
////        }
//    }
//
//    /**
//     * 文件不存在
//     *
//     * @param request
//     * @param response
//     * @param exception
//     * @throws Exception
//     */
//    @ExceptionHandler(value = FileNotFoundException.class)
//    public void fileNotFoundException(HttpServletRequest request,
//                                      HttpServletResponse response,
//                                      Exception exception) throws Exception {
//        //        exception.printStackTrace();
//        log.debug("ERROR::::：" + exception.getLocalizedMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getCause() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getSuppressed()) + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getStackTrace()) + "::::::" + new Date());
////        boolean b = isAjaxRequest(request);
////        if (b) {
//        res(response, "系统找不到指定的文件夹");
////        } else {
////            response.sendRedirect("/views/error/500");
////        }
//    }
//
//    /**
//     * 密码错误
//     *
//     * @param request
//     * @param exception
//     * @return
//     */
//    @ExceptionHandler(value = org.apache.ibatis.exceptions.PersistenceException.class)
//    public void persistenceException(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     Exception exception) throws Exception {
//        //        exception.printStackTrace();
//        log.debug("ERROR::::：" + exception.getLocalizedMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getCause() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getSuppressed()) + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getStackTrace()) + "::::::" + new Date());
//        boolean b = isAjaxRequest(request);
//        if (b) {
//            res(response, "数据库错误");
//        } else {
//            response.sendRedirect("/views/error/500");
//        }
//    }
//
//    /**
//     * 密码错误
//     *
//     * @param request
//     * @param exception
//     * @return
//     */
//    @ExceptionHandler(value = IncorrectCredentialsException.class)
//    public void incorrectCredentialsException(HttpServletRequest request,
//                                              HttpServletResponse response,
//                                              Exception exception) throws Exception {
//        //        exception.printStackTrace();
//        log.debug("ERROR::::：" + exception.getLocalizedMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getCause() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getSuppressed()) + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getStackTrace()) + "::::::" + new Date());
//        boolean b = isAjaxRequest(request);
//        if (b) {
//            res(response, "账号密码错误");
//        } else {
//            response.sendRedirect("/error/403");
//        }
//    }
//
//    /**
//     * 权限不足
//     *
//     * @param request
//     * @param exception
//     * @return
//     */
//    @ExceptionHandler(value = AuthorizationException.class)
//    public void authorizationException(HttpServletRequest request,
//                                       HttpServletResponse response,
//                                       Exception exception) throws Exception {
//        //        exception.printStackTrace();
//        log.debug("ERROR::::：" + exception.getLocalizedMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getCause() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getSuppressed()) + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getStackTrace()) + "::::::" + new Date());
//        boolean b = isAjaxRequest(request);
//        if (b) {
//            res(response, "权限不足");
//        } else {
//            response.sendRedirect("/views/error/403");
//        }
//    }
//
//    /**
//     * 权限不足
//     *
//     * @param request
//     * @param exception
//     * @return
//     */
//    @ExceptionHandler(value = UnauthorizedException.class)
//    public void unauthorizedException(HttpServletRequest request,
//                                      HttpServletResponse response,
//                                      Exception exception) throws Exception {
//        //        exception.printStackTrace();
//        log.debug("ERROR::::：" + exception.getLocalizedMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getCause() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getSuppressed()) + "::::::" + new Date());
//        log.debug("ERROR::::：" + exception.getMessage() + "::::::" + new Date());
//        log.debug("ERROR::::：" + Arrays.toString(exception.getStackTrace()) + "::::::" + new Date());
//        boolean b = isAjaxRequest(request);
//        if (b) {
//            res(response, "权限不足");
//        } else {
//            response.sendRedirect("/views/error/403");
//        }
//    }

    /**
     * 判断是否是ajax
     *
     * @param request
     * @return
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }

    /**
     * ajax的返回工具类
     *
     * @param response
     * @param str
     * @throws IOException
     */
    public void res(HttpServletResponse response,
                    String str) throws IOException {
        ResponseResult<String> result = new ResponseResult<>();
        result.setSuccess(false);
        result.setMessage(str);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(result);
        //这句话的意思，是让浏览器用utf8来解析返回的数据
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write(json);
    }
}
