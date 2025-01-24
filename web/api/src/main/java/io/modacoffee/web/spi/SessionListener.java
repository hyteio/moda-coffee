/** 
 * HYTE TECHNOLOGIES, INC. CONFIDENTIAL
 * 
 * Copyright © 2017 - 2018 HYTE Technologies, Inc. All Rights Reserved.
 *  
 * NOTICE:  All information contained herein is, and remains the property of HYTE Technologies, Inc. 
 * and its suppliers, if any.  The intellectual and technical concepts contained herein are 
 * proprietary to HYTE Technologies, Inc. and its suppliers and may be covered by U.S. and Foreign 
 * Patents, patents in process, and are protected by trade secret or copyright law.  Dissemination 
 * of this information or reproduction of this material is strictly forbidden unless prior written 
 * permission is obtained from HYTE Technologies, Inc.
 */
package io.modacoffee.web.spi;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionListener implements HttpSessionListener {

    private static final Logger logger = LogManager.getLogger(HttpSessionListener.class);
    private static AtomicInteger activeSessions = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        activeSessions.incrementAndGet();

        if (logger.isDebugEnabled()) {
            logger.debug("Session created id: {} sessionCount: {} maxInactiveInterval: {} (s)", event.getSession().getId(), activeSessions.get(), event.getSession().getMaxInactiveInterval());
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        activeSessions.decrementAndGet();
//        User user = null;
//        String debugInfo = null;
//        if (event != null && event.getSession() != null) {
//            Enumeration<String> names = event.getSession().getAttributeNames();
//            while (names.hasMoreElements()) {
//                String name = names.nextElement();
//                Object value = event.getSession().getAttribute(name);
//                if (StringUtil.containsIgnoreCase(name, Session.SESSION_DEBUG_INFO_KEY)) {
//                    debugInfo = (String) value;
//                } else if (StringUtil.containsIgnoreCase(name, Session.SESSION_USER_KEY)) {
//                    user = (User) value;
//                }
//            }
//            if (debugInfo != null) {
//                WebUtil.getAuditService().auditAction("Logout due to session timeout", user, debugInfo, true);
//            }
//        }

        
        if (logger.isDebugEnabled()) {
            long currentTime = System.currentTimeMillis();
            long idleMillis = currentTime - event.getSession().getLastAccessedTime();

            boolean timedOut = false;
            if (event.getSession().getMaxInactiveInterval() <= (idleMillis / 1000)) {
                timedOut = true;
            }
            logger.debug("Session destroyed id:{} activeSessions:{} maxInactiveInterval:{} (s) idle:{} (ms) timedOut:{}",
                    event.getSession().getId(), activeSessions.get(), event.getSession().getMaxInactiveInterval(), idleMillis, timedOut);
        }
    }
}