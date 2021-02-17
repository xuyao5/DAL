package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.bulk.BulkSupporter;
import org.junit.jupiter.api.Test;

public class BulkSupporterTest extends AbstractTest {

    @Test
    void testBulk() {
        getEsClient().execute(client -> new BulkSupporter(client).bulk(function -> {
            for (int i = 0; i < 1000000; i++) {
                function.apply(BulkSupporter.genIndexRequest("test_index_5", String.valueOf(i), Pojo.of("xu")));
            }
            return 30;
        }));
    }
}