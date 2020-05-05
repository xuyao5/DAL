package io.github.xuyao5.dal.flatfile.file.impl;

import io.github.xuyao5.dal.flatfile.configuration.FlatFileKitsConfig;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractFlatFile {

    final protected FlatFileKitsConfig flatFileKitsConfig;

    protected List<String> fileList = new CopyOnWriteArrayList<>();

    protected Map<String, Integer> fileLinesCount = new ConcurrentHashMap<>();

    protected List<String> headLines;

    protected List<String> tailLines;

    private List<Integer> skippedLines;

    protected AbstractFlatFile(FlatFileKitsConfig flatFileKitsConfig) {
        this.flatFileKitsConfig = flatFileKitsConfig;
    }
}