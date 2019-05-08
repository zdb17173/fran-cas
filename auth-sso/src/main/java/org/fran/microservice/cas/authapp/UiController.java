package org.fran.microservice.cas.authapp;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fran
 * @Description
 * @Date 2019/4/22 19:49
 */
@Controller
public class UiController {

    /*@RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(){

        return "login";
    }*/

    @RequestMapping(value="/securedPage",method= RequestMethod.GET)
    public ModelAndView secured(){
        Map<String, Object> map = new HashMap<>();
        map.put("security", new FreemarkerUtil());
        return new ModelAndView("secured", map);
    }

    @ResponseBody
    @RequestMapping(value = "/api")
    @PreAuthorize("hasRole('ROLE_common')")
    public String success() {

        return "SUCCESS";
    }

}
