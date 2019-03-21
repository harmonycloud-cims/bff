package com.harmonycloud.util;

import com.harmonycloud.log.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class LogUtil {


    public static String getRequest(HttpServletRequest request) {
        if (request != null) {
            String ip = IpUtil.getIpAddress(request);
            String correlation = request.getHeader("x-b3-traceid");
            String loginName = request.getHeader("user");
            Log log = new Log(ip, loginName, "CIMS", correlation, "Bff Application");
            return log.toString();
        }
        return null;
    }

}
