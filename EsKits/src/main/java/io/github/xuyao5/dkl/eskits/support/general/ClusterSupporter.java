package io.github.xuyao5.dkl.eskits.support.general;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.cluster.RemoteInfoRequest;
import org.elasticsearch.client.cluster.RemoteInfoResponse;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/03/21 14:05
 * @apiNote ClusterSupporter
 * @implNote ClusterSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClusterSupporter {

    public static ClusterSupporter getInstance() {
        return ClusterSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Cluster Get Settings API
     */
    @SneakyThrows
    public ClusterGetSettingsResponse getSettings(@NonNull RestHighLevelClient client) {
        return client.cluster().getSettings(new ClusterGetSettingsRequest(), DEFAULT);
    }

    /**
     * Cluster Health API
     */
    @SneakyThrows
    public ClusterHealthResponse health(@NonNull RestHighLevelClient client, @NonNull String... indices) {
        return client.cluster().health(new ClusterHealthRequest(indices), DEFAULT);
    }

    /**
     * Remote Cluster Info API
     */
    @SneakyThrows
    public RemoteInfoResponse remoteInfo(@NonNull RestHighLevelClient client) {
        return client.cluster().remoteInfo(new RemoteInfoRequest(), DEFAULT);
    }

    private static class SingletonHolder {
        private static final ClusterSupporter INSTANCE = new ClusterSupporter();
    }
}