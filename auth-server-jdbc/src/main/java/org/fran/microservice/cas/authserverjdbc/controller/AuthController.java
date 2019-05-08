package org.fran.microservice.cas.authserverjdbc.controller;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fran
 * @Description
 * @Date 2019/5/7 14:46
 */
@Controller
public class AuthController {
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @ResponseBody
    @GetMapping("/exit")
    public Logout exit(HttpServletRequest request, HttpServletResponse response) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // token can be revoked here if needed
        new SecurityContextLogoutHandler().logout(request, null, null);
        return new Logout(200, null);
    }

/*    @RequestMapping("/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static class Logout{
        int status;
        String description;

        public Logout(){}

        public Logout(int status, String description) {
            this.status = status;
            this.description = description;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
