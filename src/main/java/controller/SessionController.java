package controller;

import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.SessionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class SessionController {

    @Resource(name = "SessionServiceImpl")
    SessionService sessionService;

    @RequestMapping()
    public String listOnlineUsers(){


        return null;
    }

    @RequestMapping("/logOutAll")
    public String logOutAll(HttpSession session){

        sessionService.deleteMySessions(session);

        return "forward:login_user.jsp";
    }



}
