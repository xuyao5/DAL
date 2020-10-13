package io.github.xuyao5.dal.core.util;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/10/20 14:11
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyFileUtils extends FileUtils {

    public static List<File> getDecisionFiles(@NotNull String basePath, @NotNull String fileRegex, @NotNull String fileSeparator, @NotNull String confirmFilePrefix, @NotNull String confirmFileSuffix) {
        final List<File> fileList = Lists.newCopyOnWriteArrayList();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(basePath), fileRegex)) {
            directoryStream.forEach(path -> {
                //判断是否有确认文件
                System.out.println(path);
            });
        } catch (IOException ex) {
            log.error("", ex);
        }
        return fileList;
    }
}