package cn.it.yip.core.io;

import cn.it.yip.utils.Assert;

import java.io.*;

/**
 * @program: spring_easy
 * @author: YipSouL
 * @create: 2019-05-17 17:12
 **/
public class FileSystemResource implements Resource {
    private String path;
    private File file;

    public FileSystemResource(String path) {
        if (path == null) Assert.NotNull(path, "path must not null...");
        this.file = new File(path);
        this.path = path;
    }

    public FileSystemResource(File file) {
        this(file.getPath());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override
    public String getDescription() {
        return file.getAbsolutePath();
    }
}
