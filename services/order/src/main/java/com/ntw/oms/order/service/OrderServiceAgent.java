package com.ntw.oms.order.service;

import com.ntw.common.config.AppConfig;
import com.ntw.common.config.ServiceID;
import com.ntw.common.status.ServiceAgent;
import com.ntw.common.status.ServiceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderServiceAgent extends ServiceAgent {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceAgent.class);

    @GetMapping(path= AppConfig.STATUS_PATH, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceStatus> getServiceStatus() {
        logger.debug("Status request received");
        ServiceStatus status = getServiceStatus(ServiceID.OrderSvc);
        logger.debug("Status request response is {}",status);
        return ResponseEntity.ok().body(status);
    }

}
