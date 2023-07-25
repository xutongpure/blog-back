package com.blog.handler.exception;

import com.blog.domain.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  //用于处理全局异常，当请求达到Controller类中带@RequestMapping注解的方法时，如果没有本地定义的@ExceptionHandler，@InitBinder和@ModelAttribute时，将使用由@ControllerAdvice注解标记的类中的相应方法。
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)  //表示该方法专门处理SystemException异常类
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage()); //第二个参数用AppHttpCodeEnum.SYSTEM_ERROR.getMsg()也行。e.getMessage()是为了方便控制台找错
    }
}