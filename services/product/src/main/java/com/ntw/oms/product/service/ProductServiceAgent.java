package com.ntw.oms.product.service;

import com.ntw.common.config.AppConfig;
import com.ntw.common.config.ServiceID;
import com.ntw.common.status.ServiceAgent;
import com.ntw.common.status.ServiceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceAgent extends ServiceAgent {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceAgent.class);

    @GetMapping(path = AppConfig.STATUS_PATH, produces = "application/json")
    public ResponseEntity<ServiceStatus> getServiceStatus() {
        logger.debug("Status request received");
        ServiceStatus status = getServiceStatus(ServiceID.ProductSvc);
        logger.debug("Status request response is {}",status);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
