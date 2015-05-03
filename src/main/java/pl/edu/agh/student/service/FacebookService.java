package pl.edu.agh.student.service;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class FacebookService {

    public String getProfileId(HttpServletRequest request) {
        return getFacebookApiFromRequestSession(request).userOperations().getUserProfile().getId();
    }

    private Facebook getFacebookApiFromRequestSession(HttpServletRequest request) {
        SecurityContextImpl context = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        SocialAuthenticationToken token = (SocialAuthenticationToken) context.getAuthentication();
        return ((Connection<Facebook>)token.getConnection()).getApi();
    }
}
