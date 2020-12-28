package io.github.xuyao5.datakitsserver.abstr;

import io.github.xuyao5.datakitsserver.configuration.DataKitsConfigBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:44
 * @apiNote AbstractSupporter
 * @implNote AbstractSupporter
 */
@Slf4j
public abstract class AbstractSupporter {

    @Autowired
    protected DataKitsConfigBean configBean;
}