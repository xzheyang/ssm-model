package shiro.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import shiro.session.RedisSessionDao;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static pojo.SessionItem.SESSION_STATUS;

public class ForceLogOutFilter extends AccessControlFilter {

    @Resource
    private RedisSessionDao redisSessionDao;

    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        Subject subject = getSubject(servletRequest, servletResponse);

        //未登陆,则不需要踢出
        if(!subject.isAuthenticated() && !subject.isRemembered()){
            return true;
        }

        //因为这里的session是缓存里拿的,而没有实现Redis缓存所以这只拿Id
        String sessionId = (String) subject.getSession().getId();

        Session session = redisSessionDao.getSession(sessionId);
        System.out.println("what fuck"+sessionId);
        if(session.getAttribute(SESSION_STATUS)!=null){
            return false;
        }

        return true;
    }

    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //先退出
        Subject subject = getSubject(servletRequest, servletResponse);
        subject.logout();

        return false;
    }


}
