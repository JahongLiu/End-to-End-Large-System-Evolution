package com.ntw.oms.admin.service;

import com.ntw.common.status.DatabaseStatus;
import com.ntw.common.status.ServiceStatus;
import com.ntw.oms.admin.db.DBAdminMgr;
import com.ntw.oms.admin.entity.OperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class AdminServiceImpl {

    @Autowired
    private DBAdminMgr adminDBMgr;

    @Autowired
    private APIDataManager apiDataManager;

    public OperationStatus createAppData(int userCount, int productCount, String authHeader) {
        return apiDataManager.createAppData(userCount, productCount, authHeader);
    }

    public OperationStatus deleteAppData(String authHeader) {
        boolean success = true;
        OperationStatus deleteOperationStatus;
        deleteOperationStatus = apiDataManager.deleteAppData(authHeader);
        OperationStatus operationStatus = apiDataManager.createBootstrapData(authHeader);
        if (!operationStatus.isSuccess()) {
            return operationStatus;
        }
        if (!deleteOperationStatus.isSuccess()) {
            return deleteOperationStatus;
        }
        return operationStatus;
    }

    public List<DatabaseStatus> getDBStatus() {
        List<DatabaseStatus> serviceStatusList = new LinkedList<>();
        DatabaseStatus cqlStatus = adminDBMgr.getDBStatus("CQL");
        serviceStatusList.add(cqlStatus);
        DatabaseStatus sqlStatus = adminDBMgr.getDBStatus("SQL");
        serviceStatusList.add(sqlStatus);
        return serviceStatusList;
    }

    public List<ServiceStatus> getServicesStatus() {
        return apiDataManager.getServicesStatus();
    }

}
