package shiro.session;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static pojo.SessionItem.SESSION_STATUS;


@Repository
public class RedisSessionDao extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);
    @Resource
    private RedisTemplate redisTemplate;

    //RedisTemplate这种方法一般拿来储存和获得一个值,对于全部值非常影响性能,还是应该采用RedisManager

    private String prekey = "hyRedisSession";

    public String getKey(String session){
        StringBuffer result = new StringBuffer(prekey);
        result.append(session);
        return result.toString();
    }

    protected Serializable doCreate(Session session) {


        //创建sessionId
        Serializable sessionId = this.generateSessionId(session);
        //分配session
        assignSessionId(session,sessionId);

        //将session添加到缓存
        logger.debug("创建seesion,id=[{}]", session.getId().toString());
        try {
            redisTemplate.opsForValue().set(getKey(session.getId().toString()), session,30,TimeUnit.MINUTES);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return sessionId;
    }



    protected Session doReadSession(Serializable sessionId) {
            //因为缓存里没有,所以找不到,来访问持久Dao

        logger.debug("获取seesion,id=[{}]", sessionId.toString());
        Session readSession = null;
        try {
            readSession=(Session) redisTemplate.opsForValue().get(getKey(sessionId.toString()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return readSession;
    }


    public void update(Session session) throws UnknownSessionException {
        logger.debug("更新seesion,id=[{}]", session.getId().toString());

        if(session.getAttribute(SESSION_STATUS)!=null){
            System.out.println("用户update中被踢出");}
        if (session instanceof ValidatingSession
                && !((ValidatingSession) session).isValid()) {
            return; // 如果会话过期/停止 没必要再更新了
        }

        try {
            redisTemplate.opsForValue().set(getKey(session.getId().toString()), session,30, TimeUnit.MINUTES);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    public void delete(Session session) {

        //这里是持久层,原本是删除数据库里面的,而且sessionManager会自己删除缓存的

        logger.debug("删除seesion,id=[{}]", session.getId().toString());
        try {
            String key=session.getId().toString();
            redisTemplate.delete(getKey(key));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    public Collection<Session> getActiveSessions() {

        Set keys = redisTemplate.keys(prekey+"*");
        Set<Session> sessions = new HashSet<Session>();

        if (keys != null && keys.size() > 0) {
            for (Object key : keys) {

                Session value = (Session)redisTemplate.opsForValue().get(key);
                    sessions.add(value);
            }
        }

        return sessions;
    }

    //效率低,寻找更好的方法
    public void deleteByUserName(String userName){
        Set keys = redisTemplate.keys(prekey+"*");


        try {
            if (keys != null && keys.size() > 0) {
                for (Object key : keys) {
                    Session session = (Session) redisTemplate.opsForValue().get(key.toString());
                    String result = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY).toString();

                    if (userName.equals(result)) {
                        redisTemplate.delete(key.toString());
                    }

                }
            }
        }catch (Exception e){ logger.error(e.getMessage(),e);}

    }

    public Session getSession(String sessionId){

        return doReadSession(sessionId);

    }

}
