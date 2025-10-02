package com.note.Services;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.note.helper.Message;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Component
public class SessionHelper {
	
	
	
	
	public String removeMessageFromSession(String key) {
		try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes()).getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                Object obj = session.getAttribute(key);
                if (obj != null && obj instanceof Message) {
                    Message message = (Message) obj;
                    session.removeAttribute(key);
                    return message.getContent(); // ✅ Just return the text
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
	
	

}
