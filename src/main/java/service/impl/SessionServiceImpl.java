package service.impl;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.stereotype.Component;
import pojo.SessionItem;
import service.SessionService;
import shiro.session.RedisSessionDao;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static pojo.SessionItem.SESSION_STATUS;

@Component("SessionServiceImpl")
public class SessionServiceImpl implements SessionService {

    @Resource
    private RedisSessionDao redisSessionDao;
    @Resource
    DefaultWebSessionManager sessionManager;

    public boolean deleteMySessions(HttpSession session) {

        Object obj =  session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

        if(null != obj && obj instanceof SimplePrincipalCollection) {
            //强转
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            //判断用户，匹配用户名
            obj = spc.getPrimaryPrincipal();
        }
        if(obj!=null){
            String userName = (String) obj;
            redisSessionDao.deleteByUserName(userName);
        }

        return true;
    }


    public Collection<SessionItem> getActiveSessions() {
        Collection<Session> sessions = redisSessionDao.getActiveSessions();
        Collection<SessionItem> result = new HashSet<SessionItem>();
        if(sessions.size()>0){
            for(Session session:sessions){
                //如果session被踢下线,则不显示
                if(session.getAttribute(SESSION_STATUS)!=null){
                    continue;
                }

                SessionItem item = new SessionItem();
                //从session取出身份
                String obj =  session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY).toString();

                //添加用户名
                item.setUserName(obj);
                //添加sessionId
                item.setSessionDetails(session);

                result.add(item);
            }
        }

        return result;
    }

    public boolean forceLogOutSession(String sessionId) {

        //从缓存获得session
        Session session = redisSessionDao.getSession(sessionId);

        if(session==null){return false;}

        session.setAttribute(SESSION_STATUS,false);

        redisSessionDao.update(session);

        return true;
    }


}
