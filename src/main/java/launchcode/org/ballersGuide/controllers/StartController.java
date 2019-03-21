package launchcode.org.ballersGuide.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("start")
public class StartController {

    @RequestMapping(value = "")
    public String index() {
        return "start/index";
    }

    @RequestMapping(value = "login")
    public String login() {
        return "redirect:/user/log-in";
    }

    @RequestMapping(value = "register")
    public String register() {
        return "redirect:/user/register";
    }
}