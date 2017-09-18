package shiro.session;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Repository;
import shiro.utils.SerializableUtils;
import java.io.Serializable;


@Repository()
public class SessionDao extends CachingSessionDAO {

    private Cache cache = null;


    @Override
    public void setCacheManager(CacheManager cacheManager) {
        super.setCacheManager(cacheManager);
        cache = cacheManager.getCache("activeSessionCache");    //这不是session存放的地方?
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

        cache.remove(session.getId());


    }


    protected Serializable doCreate(Session session) {


        //创建sessionId
        Serializable sessionId = this.generateSessionId(session);
        //分配session
        assignSessionId(session,sessionId);
        //将数据序列化后  添加到缓存
        //cache.put(sessionId,SerializableUtils.serialize(session));
        System.out.println("创建");


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


        EhCacheManager manager = new EhCacheManager();
        Cache cache = manager.getCache("activeSessionCache");


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
