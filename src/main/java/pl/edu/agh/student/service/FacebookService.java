package pl.edu.agh.student.service;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Event;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Invitation;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class FacebookService {

    public String getProfileId(HttpServletRequest request) {
        return getFacebookApiFromRequestSession(request).userOperations().getUserProfile().getId();
    }

    public PagedList<Invitation> getAttendingEvents(Facebook facebook) {
        return facebook.eventOperations().getAttending();
    }

    public PagedList<Invitation> getDeclinedEvents(Facebook facebook) {
        return facebook.eventOperations().getDeclined();
    }

    public PagedList<Invitation> getMaybeAttendingEvents(Facebook facebook) {
        return facebook.eventOperations().getMaybeAttending();
    }

    public PagedList<Invitation> getNoRepliesEvents(Facebook facebook) {
        return facebook.eventOperations().getNoReplies();
    }

    public Event getEvent(Facebook facebook, String id) {
        return facebook.eventOperations().getEvent(id);
    }

    public Facebook getFacebookApiFromRequestSession(HttpServletRequest request) {
        SecurityContextImpl context = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(context != null) {
            SocialAuthenticationToken token = (SocialAuthenticationToken) context.getAuthentication();
            return ((Connection<Facebook>) token.getConnection()).getApi();
        }
        return null;
    }
}
