package ry.rudenko.englishlessonsdictionary.access;

import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ry.rudenko.englishlessonsdictionary.bean.CurrentUser;

@Component
@AllArgsConstructor
public class CurrentUserProvider {
    private final HttpServletRequest httpServletRequest;
    private final String ATTR_CURRENT_USER = "CurrentUser";

    public void set(CurrentUser currentUser){
        httpServletRequest.setAttribute(ATTR_CURRENT_USER, currentUser);
    }

    public CurrentUser get(){
        CurrentUser currentUser = (CurrentUser) httpServletRequest.getAttribute(ATTR_CURRENT_USER);
        if(currentUser == null){
            return new CurrentUser();
        }
        return currentUser;
    }
}
