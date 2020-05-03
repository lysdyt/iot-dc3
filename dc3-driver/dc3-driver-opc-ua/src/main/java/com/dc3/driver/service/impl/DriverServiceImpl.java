/*
 * Copyright 2019 Pnoker. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dc3.driver.service.impl;

import com.dc3.common.model.Device;
import com.dc3.common.model.Point;
import com.dc3.common.sdk.bean.AttributeInfo;
import com.dc3.common.sdk.bean.DriverContext;
import com.dc3.common.sdk.service.DriverService;
import com.dc3.common.sdk.service.rabbit.PointValueService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static com.dc3.common.sdk.util.DriverUtils.attribute;

/**
 * @author pnoker
 */
@Slf4j
@Service
public class DriverServiceImpl implements DriverService {
    @Resource
    private PointValueService pointValueService;
    @Resource
    private DriverContext driverContext;

    @Override
    public void initial() {
    }

    @Override
    public String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) {
        SecurityPolicy policy = SecurityPolicy.None;

        //EndpointDescription[] endpointDescription = UaTcpStackClient.getEndpoints(EndPointUrl).get();


        return "";
    }

    @Override
    public Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo value) {
        return false;
    }

    @Override
    public void schedule() {

    }

    /**
     * 获取 Opc Ua 的 EndPointUrl
     *
     * @param driverInfo
     * @return
     */
    private String getEndPointUrl(Map<String, AttributeInfo> driverInfo) {
        String host = attribute(driverInfo, "host");
        String port = attribute(driverInfo, "port");
        String endPointUrl = String.format("opc.tcp://%s:%s", host, port);
        log.debug("endPortUrl: {}", endPointUrl);
        return endPointUrl;
    }

}
