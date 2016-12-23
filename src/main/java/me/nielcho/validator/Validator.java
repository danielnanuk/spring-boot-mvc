package me.nielcho.validator;


import org.springframework.core.MethodParameter;

public interface Validator {

    void validate(Object object, MethodParameter parameter);

}
