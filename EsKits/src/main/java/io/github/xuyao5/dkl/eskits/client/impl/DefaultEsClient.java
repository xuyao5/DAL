package io.github.xuyao5.dkl.eskits.client.impl;

import io.github.xuyao5.dkl.eskits.client.EsClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.elasticsearch.client.RestClientBuilder.*;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:45
 * @apiNote EsClientImpl
 * @implNote EsClientImpl
 */
@Slf4j
public final class DefaultEsClient implements EsClient {

    private static final short MULTI = 30;

    private final HttpHost[] HOSTS;
    private final String USERNAME;
    private final String PASSWORD;

    public DefaultEsClient(@NotNull String[] clientUrls, String clientUsername, String clientPassword) {
        HOSTS = url2HttpHost(clientUrls);
        USERNAME = clientUsername;
        PASSWORD = clientPassword;
    }

    @SneakyThrows
    @Override
    public <T> T invokeFunction(Function<RestHighLevelClient, T> function) {
        try (RestHighLevelClient client = getRestHighLevelClient()) {
            return function.apply(client);
        }
    }

    @SneakyThrows
    @Override
    public void invokeConsumer(Consumer<RestHighLevelClient> consumer) {
        try (RestHighLevelClient client = getRestHighLevelClient()) {
            consumer.accept(client);
        }
    }

    @Override
    public int hostsCount() {
        return HOSTS.length;
    }

    private RestHighLevelClient getRestHighLevelClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));
        return new RestHighLevelClient(RestClient.builder(HOSTS)
                .setHttpClientConfigCallback(builder -> builder
                        .setMaxConnPerRoute(DEFAULT_MAX_CONN_PER_ROUTE * MULTI)//10*30
                        .setMaxConnTotal(DEFAULT_MAX_CONN_TOTAL * MULTI)//30*30
                        .setDefaultCredentialsProvider(credentialsProvider))
                .setRequestConfigCallback(builder -> builder
                        .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS * MULTI)//1s*30
                        .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT_MILLIS * MULTI)));//30s*30
    }

    private HttpHost[] url2HttpHost(@NotNull String[] url) {
        return Arrays.stream(url)
                .filter(StringUtils::isNoneEmpty)
                .map(HttpHost::create)
                .distinct()
                .toArray(HttpHost[]::new);
    }
}