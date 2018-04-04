package com.lawliet.springboot.blog.util;

import antlr.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * ConstraintViolationException     处理器
 * @author hao@lawliet.com
 * @since 2018/3/31 20:30
 */
public class ConstraintViolationExceptionHandler {

    /**
     * 返回异常信息
     * @author hao
     * @param [e]
     * @return java.lang.String
     */
    public static String getMessage(ConstraintViolationException e){

        List<String> msgList = new ArrayList<>();
        for(ConstraintViolation<?> constraintViolation : e.getConstraintViolations()){
            msgList.add(constraintViolation.getMessage());
        }

        return org.thymeleaf.util.StringUtils.join(msgList.toArray(),";");
    }
}
