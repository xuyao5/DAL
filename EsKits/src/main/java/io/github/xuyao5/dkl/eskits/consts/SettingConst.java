package io.github.xuyao5.dkl.eskits.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/03/21 21:54
 * @apiNote SettingConst
 * @implNote SettingConst
 */
@RequiredArgsConstructor
public enum SettingConst {

    INDEX_NUMBER_OF_SHARDS("index.number_of_shards"),
    INDEX_NUMBER_OF_REPLICAS("index.number_of_replicas"),
    INDEX_REFRESH_INTERVAL("index.refresh_interval"),
    INDEX_MAX_RESULT_WINDOW("index.max_result_window"),
    INDEX_MERGE_SCHEDULER_MAX_THREAD_COUNT("index.merge.scheduler.max_thread_count"),
    INDEX_TRANSLOG_SYNC_INTERVAL("index.translog.sync_interval"),
    INDEX_SORT_FIELD("index.sort.field"),
    INDEX_SORT_ORDER("index.sort.order"),
    MAPPING_DATE_FORMAT("yyyy-MM-dd HH:mm:ss||strict_date_time_no_millis||strict_date_optional_time||epoch_millis"),
    ;

    @Getter
    private final String name;

    public static Optional<SettingConst> getSettingByName(@NotNull String name) {
        return Arrays.stream(SettingConst.values()).filter(settingConst -> settingConst.name.equalsIgnoreCase(name)).findAny();
    }
}
