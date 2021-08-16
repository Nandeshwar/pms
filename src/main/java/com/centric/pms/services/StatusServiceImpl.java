package com.centric.pms.services;

import com.centric.pms.models.Status;
import com.centric.pms.models.UpTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    BuildProperties buildProperties;

    @Override
    public Status appInfo() {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        Status status = new Status();
        status.setAppName(buildProperties.getName());
        status.setAppVer(buildProperties.getVersion());
        status.setAppStatus("up and running");
        status.setUpTime(new UpTime(rb.getUptime(), "ms"));
        return status;
    }
}
