package com.bazl.dna.caseinfo.accept.config;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/v1")
public class QuickRun {
    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public Map<String, Object> firstResp (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("map"));
        return map;
    }

    @RequestMapping("/index")
    public void index(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String sessionId = req.getSession().getId();
        PrintWriter writer = resp.getWriter();
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getRequestURI();
        String cookie = getCookie(req);
        writer.write("<html>");
        writer.write("<body>");
        writer.write("url:" + url + "<br/>");
        writer.write("sessionId:" + sessionId + "<br/>");
        writer.write("cookies:<br/>");
        if(cookie!=null){
            writer.write(cookie);
        }
        writer.write("</body>");
        writer.write("</html>");
        writer.flush();
    }

    public String getCookie(HttpServletRequest req) {

        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Cookie cookie : cookies) {
            if(cookie==null){
                continue;
            }
            sb.append(cookie.getName() + ":" + cookie.getValue() + "<br/>");
        }
        return sb.toString();
    }
}
