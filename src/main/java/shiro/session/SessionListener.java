package shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

public class SessionListener extends SessionListenerAdapter {

    public void onStart(Session session) {

    }


    public void onStop(Session session) {
        System.out.println("StopSession = [" + session+ "]");
    }


    public void onExpiration(Session session) {

    }

}
