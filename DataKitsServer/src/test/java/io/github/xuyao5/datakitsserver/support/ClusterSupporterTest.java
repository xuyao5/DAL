package io.github.xuyao5.datakitsserver.support;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.ClusterSupporter;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.junit.jupiter.api.Test;

public class ClusterSupporterTest extends AbstractTest {

    @Test
    void testCreate() {
        ClusterHealthResponse clusterHealthResponse = new ClusterSupporter().health(esClient);
        System.out.println(clusterHealthResponse.getNumberOfNodes());
        System.out.println(clusterHealthResponse.getNumberOfDataNodes());
    }
}