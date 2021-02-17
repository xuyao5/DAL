package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.rankeval.*;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.validation.constraints.NotNull;
import java.util.*;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:48
 * @apiNote EsIndexSupporter
 * @implNote EsIndexSupporter
 */
public final class EsSearchSupporter extends AbstractSupporter {

    public EsSearchSupporter(@NotNull RestHighLevelClient client) {
        super(client);
    }

    /**
     * Search API
     */
    @SneakyThrows
    public SearchResponse search() {
        return client.search(new SearchRequest("").source(new SearchSourceBuilder().query(null)
                        .from(0)
                        .size(10)
                        .timeout(TimeValue.timeValueSeconds(60))
                        .fetchSource(false)),
                DEFAULT);
    }

    /**
     * Search Scroll API
     */
    @SneakyThrows
    public SearchResponse scroll() {
        SearchScrollRequest request = new SearchScrollRequest();
        request.scroll(TimeValue.timeValueSeconds(30));
        return client.scroll(request, DEFAULT);
    }


    /**
     * Clear Scroll API
     */
    @SneakyThrows
    public ClearScrollResponse clearScroll() {
        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId("scrollId");
        return client.clearScroll(request, DEFAULT);
    }

    /**
     * Multi-Search API
     */
    @SneakyThrows
    public MultiSearchResponse msearch() {
        MultiSearchRequest request = new MultiSearchRequest();
        SearchRequest firstSearchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
        firstSearchRequest.source(searchSourceBuilder);
        request.add(firstSearchRequest);
        SearchRequest secondSearchRequest = new SearchRequest();
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "luca"));
        secondSearchRequest.source(searchSourceBuilder);
        request.add(secondSearchRequest);
        return client.msearch(request, DEFAULT);
    }

    /**
     * Search Template API
     */
    @SneakyThrows
    public SearchTemplateResponse searchTemplate() {
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest(""));
        request.setScriptType(ScriptType.INLINE);
        request.setScript("");
        request.setScriptParams(null);
        return client.searchTemplate(request, DEFAULT);
    }

    /**
     * Multi-Search-Template API
     */
    @SneakyThrows
    public MultiSearchTemplateResponse msearchTemplate() {
        String[] searchTerms = {"elasticsearch", "logstash", "kibana"};

        MultiSearchTemplateRequest multiRequest = new MultiSearchTemplateRequest();
        for (String searchTerm : searchTerms) {
            SearchTemplateRequest request = new SearchTemplateRequest();
            request.setRequest(new SearchRequest("posts"));

            request.setScriptType(ScriptType.INLINE);
            request.setScript(
                    "{" +
                            "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                            "  \"size\" : \"{{size}}\"" +
                            "}");

            Map<String, Object> scriptParams = new HashMap<>();
            scriptParams.put("field", "title");
            scriptParams.put("value", searchTerm);
            scriptParams.put("size", 5);
            request.setScriptParams(scriptParams);

            multiRequest.add(request);
        }
        return client.msearchTemplate(multiRequest, DEFAULT);
    }

    /**
     * Field Capabilities API
     */
    @SneakyThrows
    public FieldCapabilitiesResponse fieldCaps() {
        FieldCapabilitiesRequest request = new FieldCapabilitiesRequest()
                .fields("user")
                .indices("posts", "authors", "contributors");
        return client.fieldCaps(request, DEFAULT);
    }

    /**
     * Ranking Evaluation API
     */
    @SneakyThrows
    public RankEvalResponse rankEval() {
        EvaluationMetric metric = new PrecisionAtK();
        List<RatedDocument> ratedDocs = new ArrayList<>();
        ratedDocs.add(new RatedDocument("posts", "1", 1));
        SearchSourceBuilder searchQuery = new SearchSourceBuilder();
        searchQuery.query(QueryBuilders.matchQuery("user", "kimchy"));
        RatedRequest ratedRequest =
                new RatedRequest("kimchy_query", ratedDocs, searchQuery);
        List<RatedRequest> ratedRequests = Arrays.asList(ratedRequest);
        RankEvalSpec specification =
                new RankEvalSpec(ratedRequests, metric);
        RankEvalRequest request =
                new RankEvalRequest(specification, new String[]{"posts"});
        return client.rankEval(request, DEFAULT);
    }

    /**
     * Explain API
     */
    @SneakyThrows
    public ExplainResponse explain() {
        ExplainRequest request = new ExplainRequest("contributors", "1");
        request.query(QueryBuilders.termQuery("user", "tanguy"));
        return client.explain(request, DEFAULT);
    }

    /**
     * Count API
     */
    @SneakyThrows
    public CountResponse count() {
        CountRequest countRequest = new CountRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        return client.count(countRequest, DEFAULT);
    }
}
