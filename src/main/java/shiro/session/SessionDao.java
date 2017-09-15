package shiro.session;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.stereotype.Repository;
import shiro.utils.SerializableUtils;


import java.io.Serializable;


@Repository
public class SessionDao extends CachingSessionDAO {

    private Cache cache = null;

    private int i=0;
    private int j=0;


    @Override
    public void setCacheManager(CacheManager cacheManager) {
        super.setCacheManager(cacheManager);
        cache = cacheManager.getCache("hyCache");
    }

    protected void doUpdate(Session session) {


        if (session instanceof ValidatingSession
                && !((ValidatingSession) session).isValid()) {
            return; // 如果会话过期/停止 没必要再更新了
        }

        cache.put(session.getId(), SerializableUtils.serialize(session));

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
        cache.put(sessionId,session);

        System.out.println("CreateSession = [" + session+ "]");

        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {


        Session session = (Session) cache.get(sessionId.toString());

        System.out.println("ReadSession = [" + session+ "]");

        return session;
    }

}
