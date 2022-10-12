/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pulsar.tests.integration.containers;

import org.apache.pulsar.tests.integration.utils.DockerUtils;

public class WebSocketContainer extends PulsarContainer<WebSocketContainer> {

    public WebSocketContainer(String clusterName, String hostName) {
        super(clusterName, hostName, hostName,
                "bin/run-websocket.sh",
                -1,
                BROKER_HTTP_PORT, "/admin/v2/proxy-stats/stats");
    }

    public String getWSUrl() {
        return "ws://" + getHost() + ":" + getMappedPort(BROKER_HTTP_PORT);
    }

    @Override
    protected void afterStart() {
        DockerUtils.runCommandAsyncWithLogging(this.dockerClient, this.getContainerId(),
                "tail", "-f", "/var/log/pulsar/pulsar-websocket.log");
    }
}
