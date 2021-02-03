package io.github.xuyao5.dkl.eskits.client;

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
import java.util.function.Function;

import static org.elasticsearch.client.RestClientBuilder.DEFAULT_MAX_CONN_PER_ROUTE;
import static org.elasticsearch.client.RestClientBuilder.DEFAULT_MAX_CONN_TOTAL;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:45
 * @apiNote ElasticsearchClient
 * @implNote ElasticsearchClient
 */
@Slf4j
public final class EsClient {

    private final HttpHost[] HOSTS;
    private final String USERNAME;
    private final String PASSWORD;
    private final int CONN_MULTI;

    public EsClient(@NotNull String[] clientUrls, String clientUsername, String clientPassword) {
        this(clientUrls, clientUsername, clientPassword, 10);
    }

    public EsClient(@NotNull String[] clientUrls, String clientUsername, String clientPassword, int connMulti) {
        HOSTS = url2HttpHost(clientUrls);
        USERNAME = clientUsername;
        PASSWORD = clientPassword;
        CONN_MULTI = connMulti;
    }

    @SneakyThrows
    public <T> T execute(Function<RestHighLevelClient, T> function) {
        try (RestHighLevelClient client = getRestHighLevelClient()) {
            return function.apply(client);
        }
    }

    private RestHighLevelClient getRestHighLevelClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));
        return new RestHighLevelClient(RestClient.builder(HOSTS)
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setMaxConnPerRoute(DEFAULT_MAX_CONN_PER_ROUTE * CONN_MULTI)
                        .setMaxConnTotal(DEFAULT_MAX_CONN_TOTAL * CONN_MULTI)
                        .setDefaultCredentialsProvider(credentialsProvider)));
    }

    private HttpHost[] url2HttpHost(@NotNull String[] url) {
        return Arrays.stream(url)
                .filter(StringUtils::isNoneEmpty)
                .map(HttpHost::create)
                .distinct()
                .toArray(HttpHost[]::new);
    }
}