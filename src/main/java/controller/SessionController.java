package controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.SessionItem;
import service.SessionService;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Collection;


@Controller
public class SessionController {

    @Resource(name = "SessionServiceImpl")
    SessionService sessionService;


    @RequiresRoles("super")
    @RequestMapping("/listSessions")
    public String listOnlineUsers(Model model){
        Collection<SessionItem> sessions = sessionService.getActiveSessions();
        model.addAttribute("sessions",sessions);

        return "list_sessions";
    }

    @RequiresRoles("super")
    @RequestMapping("/forceLogOutSession")
    public String forceLogOutSession(String outSessionId){

        sessionService.forceLogOutSession(outSessionId);

        return "redirect:listSessions";
    }

    @RequiresRoles("super")
    @RequestMapping("/forbidLogin")
    public String forbidLogin(){


        return "redirect:listSessions";
    }


    @RequestMapping("/logOutAll")
    public String logOutAll(HttpSession session){

        sessionService.deleteMySessions(session);

        return "redirect:login_user.jsp";
    }



}
