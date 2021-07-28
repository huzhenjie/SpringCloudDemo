package com.scrat.background.module.valid;

import com.scrat.background.model.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Res<Object> parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        String msg = String.format("param %s is require", e.getParameterName());
        return Res.error(400, msg);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Res<Object> parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return Res.error(400, "body format error");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Res<Object> parameterExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : errorList) {
                sb.append(error.getDefaultMessage()).append(';');
            }
            int errSize = sb.length();
            if (errSize > 0) {
                sb.setLength(errSize - 1);
                return Res.error(400, sb.toString());
            }
        }
        return Res.error(400, "param error");
    }

    @ExceptionHandler({ParamException.class})
    @ResponseBody
    public Res<Object> paramExceptionHandler(ParamException e) {
        log.error(e.getMessage(), e);
        String msg = e.getMessage();
        if (!StringUtils.hasLength(msg)) {
            msg = "param error";
        }
        return Res.error(400, msg);
    }
}
