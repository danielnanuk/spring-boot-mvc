package me.nielcho.config;

import me.nielcho.exception.ValidateException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add((request, response, handler, ex) -> {
            if (ex instanceof ValidateException) {
                // handle exception here
                response.setStatus(400);
                return new ModelAndView();
            }
            return null;
        });
    }
}
