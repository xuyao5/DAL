package io.github.xuyao5.dal.file2es.disruptor;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 26/09/20 19:41
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class LongEvent {

    private long value;
}
