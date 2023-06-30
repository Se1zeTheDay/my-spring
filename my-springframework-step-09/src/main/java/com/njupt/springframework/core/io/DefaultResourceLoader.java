package com.njupt.springframework.core.io;

import com.njupt.springframework.utils.Assert;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader {
    @Override
    public Resource getResource(String location) {

        Assert.notNull(location, "location must not be null");

        if (location.startsWith(CLASS_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASS_URL_PREFIX.length()));
        } else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }


        }
    }
}
