package pub.chenhuang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.chenhuang.pojo.Useraccount;
import pub.chenhuang.service.UserService;

import javax.annotation.Resource;

/**
 * Created by cjw on 2017/3/16.
 */
@Controller
@RequestMapping("/chen")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/getUser")
    @ResponseBody
    public Useraccount getUser(){
        return userService.getUser();
    }
}
