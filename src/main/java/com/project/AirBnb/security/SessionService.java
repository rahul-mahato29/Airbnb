package com.project.AirBnb.security;

import com.project.AirBnb.entities.Session;
import com.project.AirBnb.entities.User;
import com.project.AirBnb.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    int SESSION_LIMIT = 2;

    //create a new session
    public void generateNewSession(User user, String refreshToken) {
        List<Session> userSessions = sessionRepository.findByUser(user);

        //checking if user session limit is exceeded or not ?
        if(userSessions.size() == SESSION_LIMIT) {
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));

            Session leastRecentlyUsedSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);    //deleting the least recently session,
            // to allow others new session.
        }

        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();

        sessionRepository.save(newSession);
    }

    //verify session
    public void validateSession(String refreshToken) {
        Session session  = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("Session Not Found"));

        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}

