package service;

import javax.servlet.http.HttpSession;

public interface SessionService {

    boolean deleteMySessions(HttpSession session);

}
