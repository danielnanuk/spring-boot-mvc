package me.nielcho.controller;

import me.nielcho.annotation.Validate;
import me.nielcho.domain.Good;
import me.nielcho.validator.LengthMoreThanFive;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by daniel on 2016/12/23.
 */
@RestController
@RequestMapping
public class HelloController {


    @RequestMapping("/hello")
    public List<Good> hello(@Validate(LengthMoreThanFive.class) @RequestBody List<Good> goods) {
        return goods;
    }
}
