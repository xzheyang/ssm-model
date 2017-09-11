package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.Student;
import service.StudentService;

import javax.annotation.Resource;

@Controller
public class UserController {

    @Resource(name = "StudentServiceImpl")
    private StudentService service;

    @RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    public String loginUser(Student student){

        boolean result = service.loginUser(student);

        if(result==true){return "success";}

        return "forward:login_user.jsp";
    }


    @RequestMapping(value = "/{path:\\w+}")
    public String all(@PathVariable("path") String path){

        return path;
    }




}
