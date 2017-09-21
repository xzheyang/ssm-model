package service;

import pojo.SessionItem;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Map;

public interface SessionService {

    boolean deleteMySessions(HttpSession session);

    Collection<SessionItem> getActiveSessions();

    boolean forceLogOutSession(String sessionId);

}
