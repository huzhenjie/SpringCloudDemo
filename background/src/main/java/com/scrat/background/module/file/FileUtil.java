package com.scrat.background.module.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class.getName());

    public static void downloadFile(String url, String filePath) throws IOException {
        InputStream in = null;
        ReadableByteChannel readableByteChannel = null;
        FileOutputStream fileOutputStream = null;
        try {
            in = new URL(url).openStream();
            readableByteChannel = Channels.newChannel(in);
            fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } finally {
            closeQuietly(fileOutputStream);
            closeQuietly(readableByteChannel);
            closeQuietly(in);
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (IOException e) {
            log.error("关闭流失败", e);
        }
    }

}
