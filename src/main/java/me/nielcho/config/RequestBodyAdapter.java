package me.nielcho.config;

import me.nielcho.annotation.Validate;
import me.nielcho.validator.Validator;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RequestBodyAdapter extends RequestBodyAdviceAdapter {

    private Map<String, Validator> validatorCache = new HashMap<>();

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        final Annotation[] parameterAnnotations = methodParameter.getParameterAnnotations();
        for (final Annotation annotation : parameterAnnotations) {
            if (annotation.annotationType().equals(Validate.class)) {
                return true;
            }
        }
        return false;
    }

    private void validate(Object body, MethodParameter parameter) {
        Validate validate = parameter.getParameterAnnotation(Validate.class);
        Class<? extends Validator> validatorClass = validate.value();
        String key = validatorClass.getName();
        Validator validator = validatorCache.get(key);
        if (validator == null) {
            try {
                validator = validate.value().newInstance();
                validatorCache.put(key, validator);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        validator.validate(body, parameter);
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        validate(body, parameter);
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type
            targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        validate(body, parameter);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
