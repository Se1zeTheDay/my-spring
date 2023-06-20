package com.njupt.springframework.core.io;

import com.njupt.springframework.beans.exception.BeansException;
import com.njupt.springframework.utils.Assert;
import org.springframework.util.ClassUtils;
import sun.misc.Launcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {

    private final String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, "path must not be null");
        this.path = path;
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    public ClassPathResource(String path) {
        this(path, (ClassLoader) null);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = classLoader.getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
        }
        return is;
    }
}
