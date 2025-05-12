package com.ntw.auth.service;

import com.ntw.common.config.AppConfig;
import com.ntw.common.config.ServiceID;
import com.ntw.common.status.ServiceAgent;
import com.ntw.common.status.ServiceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthService provides rest interface for authentication and authorization
 */
@RestController
public class AuthServiceAgent extends ServiceAgent {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceAgent.class);

    @GetMapping(path= AppConfig.STATUS_PATH,
        produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceStatus> getServiceStatus() {
        logger.debug("Status request received");
        ServiceStatus status = getServiceStatus(ServiceID.AuthSvc);
        logger.debug("Status request response is {}",status);
        return ResponseEntity.ok(status);
    }

}
