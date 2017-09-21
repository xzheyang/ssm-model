package pojo;

import com.sun.corba.se.spi.copyobject.CopyobjectDefaults;
import org.apache.shiro.session.Session;
import org.springframework.web.bind.support.SessionStatus;

import java.io.Serializable;
import java.util.Date;

public class SessionItem implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SESSION_STATUS ="online-status";

    private String sessionId;
    private String userName;
    //Session Host
    private String host;
    //Session创建时间
    private Date startTime;
    //Session最后交互时间
    private Date lastAccess;
    //Session timeout
    private long timeout;
    //session 是否踢出
    private boolean sessionStatus = true;

    //获得session的详细信息
    public void setSessionDetails(Session session){


        setLastAccess(session.getLastAccessTime());

        setHost(session.getHost());

        setSessionId(session.getId().toString());

        setLastAccess(session.getLastAccessTime());

        setTimeout(session.getTimeout());

        setStartTime(session.getStartTimestamp());

        Object status = session.getAttribute(SESSION_STATUS);

        if (status!=null){
            sessionStatus = false;
        }

    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(boolean sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
