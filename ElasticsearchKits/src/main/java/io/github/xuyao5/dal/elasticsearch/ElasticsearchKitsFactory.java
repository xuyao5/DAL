package io.github.xuyao5.dal.elasticsearch;

import io.github.xuyao5.dal.elasticsearch.base.EsClient;
import io.github.xuyao5.dal.elasticsearch.configuration.ElasticsearchKitsConfigBean;
import io.github.xuyao5.dal.elasticsearch.document.EsDocumentSupporter;
import io.github.xuyao5.dal.elasticsearch.index.EsIndexSupporter;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:38
 * @apiNote Elasticsearch工具包工厂
 * @implNote 获取配置，生成index/document supporter等
 */
@Component("elasticsearchKitsFactory")
public final class ElasticsearchKitsFactory {

    @Autowired
    private ElasticsearchKitsConfigBean configBean;

    public EsClient getElasticsearchClient() {
        return EsClient.instance(new HttpHost[]{HttpHost.create(configBean.getEsClientHosts()[0])}, Optional.of(configBean.getEsClientUsername()), Optional.of(configBean.getEsClientPassword()));
    }

    public EsIndexSupporter getEsIndexSupporter() {
        return EsIndexSupporter.instance();
    }

    public EsDocumentSupporter getEsDocumentSupporter() {
        return EsDocumentSupporter.instance();
    }
}