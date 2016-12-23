package me.nielcho.validator;

import me.nielcho.exception.ValidateException;
import org.springframework.core.MethodParameter;

import java.util.List;

/**
 * Created by daniel on 2016/12/23.
 */
public class LengthMoreThanFive implements Validator {
    @Override
    public void validate(Object object, MethodParameter parameter) {
        if (null == object) {
            throw new ValidateException(" null object");
        }
        if (object instanceof List) {
            if (((List) object).size() <= 5) {
                throw new ValidateException(" list size is less than or equal to 5");
            }
        }
    }
}
