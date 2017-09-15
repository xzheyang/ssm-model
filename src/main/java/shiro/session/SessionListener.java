package shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

public class SessionListener extends SessionListenerAdapter {

    public void onStart(Session session) {
        System.out.println("session = [" + session.getId() + "]");
    }


    public void onStop(Session session) {

    }


    public void onExpiration(Session session) {

    }

}
