package controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.Student;
import service.StudentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Resource(name = "StudentServiceImpl")
    private StudentService service;

    @RequestMapping(value = "/loginUser", method = RequestMethod.GET)
    public String loginPage() {
        return "redirect:/login_user.jsp";
    }

    @RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    public String loginUser(Student student, HttpServletRequest request){


        if(student==null){return "forward:login_user.jsp";}

        Subject subject = SecurityUtils.getSubject() ;
        UsernamePasswordToken token = new UsernamePasswordToken(student.getUsername(),student.getPassword()) ;


        try {
            subject.login(token);
        }catch (AuthenticationException e){
            return "forward:login_user.jsp";
        }


        return "success";


    }


    /*
    @RequestMapping(value = "/{path:\\w+}")
    public String all(@PathVariable("path") String path){

        return path;
    }*/

    @RequestMapping(value = "/student")
    public String test1(){

        return "student";
    }

    @RequestMapping(value = "/super")
    public String test2(){

        return "super";
    }

    @RequestMapping(value = "/teacher")
    public String test3(){

        return "teacher";
    }


}
