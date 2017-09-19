package shiro.session;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import shiro.utils.SerializableUtils;

import javax.annotation.Resource;
import java.io.Serializable;


@Repository
public class SessionDao extends CachingSessionDAO {

    private Cache cache = null;
    //@Resource(name="cacheManager")       不同Manager的储存值不一样
    //EhCacheManager Manager;


    @Override
    public void setCacheManager(CacheManager cacheManager) {
        super.setCacheManager(cacheManager);
        cache = cacheManager.getCache("activeSessionCache");
    }

    protected void doUpdate(Session session) {


        if (session instanceof ValidatingSession
                && !((ValidatingSession) session).isValid()) {
            return; // 如果会话过期/停止 没必要再更新了
        }
        //向缓存放入session,并序列化
        //cache.put(session.getId(), SerializableUtils.serialize(session));
        System.out.println("修改"+cache.keys());
    }



    protected void doDelete(Session session) {

        cache.remove(session.getId());  //这里是持久层,原本是删除数据库里面的,而且sessionManager会自己删除缓存的
        System.out.println("删除"+session.getId());

    }


    protected Serializable doCreate(Session session) {


        //创建sessionId
        Serializable sessionId = this.generateSessionId(session);
        //分配session
        assignSessionId(session,sessionId);
        //将数据序列化后  添加到缓存
        //cache.put(sessionId,SerializableUtils.serialize(session));
        System.out.println("创建"+session.getId());


        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {
        System.out.println("找不到"+cache.keys());      //因为缓存里没有,所以找不到,来访问持久Dao
        Object desSession = cache.get(sessionId.toString());

        if(desSession==null){
            return null;
        }

        //取出session,并反序列化
        Session session = SerializableUtils.deserialize( (String) desSession );


        return session;
    }

    public void deleteByUserName(String userName){
        //不同SessionDao用的不同SessionManager,这样,这方法是失败的,待解决

        //Cache cache = Manager.getCache("activeSessionCache");


        System.out.println("why = " + cache.keys());

        for(Object value:cache.keys()){

            Object result = cache.get(value);

            Session session = SerializableUtils.deserialize( (String) result );

            if(session!=null){
                System.out.println("session = [" + session.toString() + "]");
            }

            if(userName.equals(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY))){
                cache.remove(value);

                System.out.println("delete");
            }

        }


    }

}
