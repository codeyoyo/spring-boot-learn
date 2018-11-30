package com.example.lesson34.exception;

import com.example.lesson34.dto.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request,Exception e){
        ModelAndView mav=new ModelAndView();
        mav.addObject("exception",e);
        mav.addObject("url",request.getRequestURI());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest request,MyException e){
        ErrorInfo<String> errorInfo=new ErrorInfo<>();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setCode(ErrorInfo.ERROR);
        errorInfo.setData("Some data");
        errorInfo.setUrl(request.getRequestURI().toString());
        return errorInfo;
    }

}
