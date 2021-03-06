package io.github.xuyao5.datakitsserver.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:41
 * @apiNote DataKitsConfigBean
 * @implNote DataKitsConfigBean
 */
@Getter
@ConfigurationProperties("es.client")
public final class EsClientConfig {

    @Value("${es.bulk.threads}")
    private int esBulkThreads;

    @Value("${es.scroll.size}")
    private int esScrollSize;

    @Value("${mysql.binlog.host}")
    private String mysqlBinlogHost;

    @Value("${mysql.binlog.port}")
    private int mysqlBinlogPort;

    @Value("${mysql.binlog.username}")
    private String mysqlBinlogUsername;

    @Value("${mysql.binlog.password}")
    private String mysqlBinlogPassword;
}
