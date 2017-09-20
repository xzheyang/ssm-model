package service.impl;

import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Component;
import service.SessionService;
import shiro.session.RedisSessionDao;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Component("SessionServiceImpl")
public class SessionServiceImpl implements SessionService {

    @Resource
    private RedisSessionDao redisSessionDao;

    public boolean deleteMySessions(HttpSession session) {

        Object obj =  session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

        if(null != obj && obj instanceof SimplePrincipalCollection) {
            //强转
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            //判断用户，匹配用户ID。
            obj = spc.getPrimaryPrincipal();
        }
        if(obj!=null){
            String userName = (String) obj;
            redisSessionDao.deleteByUserName(userName);
        }



        return true;
    }


}
