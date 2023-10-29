package com.akerke.salonservice.tc;

import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

public class SalonServiceElasticsearchContainer extends ElasticsearchContainer {

    private static final String ELASTIC_SEARCH_DOCKER = "elasticsearch:8.3.3";
    private static final String CLUSTER_NAME = "cluster.name";
    private static final String ELASTIC_SEARCH = "elasticsearch";
    private static final String DISCOVERY_TYPE = "discovery.type";
    private static final Integer HOST_PORT = 9200;
    private static final Integer CONTAINER_PORT = 9200;
    private static final String DISCOVERY_TYPE_SINGLE_NODE = "single-node";
    private static final String XPACK_SECURITY_ENABLED = "xpack.security.enabled";

    public SalonServiceElasticsearchContainer() {
        super(DockerImageName.parse(ELASTIC_SEARCH_DOCKER)
                .asCompatibleSubstituteFor("docker.elastic.co/elasticsearch/elasticsearch"));

        addFixedExposedPort(HOST_PORT, CONTAINER_PORT);
        addEnv(DISCOVERY_TYPE, DISCOVERY_TYPE_SINGLE_NODE);
        addEnv(XPACK_SECURITY_ENABLED, Boolean.FALSE.toString());
        addEnv(CLUSTER_NAME, ELASTIC_SEARCH);
    }
}
