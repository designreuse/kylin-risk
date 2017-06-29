package com.rkylin.risk.rule.vm.interpreter.builder;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.LogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.DataSourceResourceLoader;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import org.apache.velocity.runtime.resource.loader.JarResourceLoader;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.loader.URLResourceLoader;

/**
 * Created by tomalloc on 16-3-14.
 */
public class VelocityEngineBuilder {
    private VelocityEngine velocityEngine;
    private String resourceLoader;

    private byte cacheFlag = 0;

    private ResourceLoaderType resourceLoaderType;

    public VelocityEngineBuilder(ResourceLoaderType resourceLoaderType) {
        if (resourceLoaderType == null) {
            throw new RuntimeException("resourceLoaderType is not null");
        }
        initVelocity(resourceLoaderType.name);
        this.resourceLoaderType = resourceLoaderType;
    }

    public VelocityEngineBuilder(String resourceLoader) {
        if (StringUtils.isBlank(resourceLoader)) {
            throw new RuntimeException("resourceLoaderPrefix is not blank");
        }
        initVelocity(resourceLoader);
    }

    public VelocityEngineBuilder() {
        initVelocity(ResourceLoaderType.CLASSPATH.name);
    }

    private void initVelocity(String resourceLoader) {
        velocityEngine = new VelocityEngine();
        this.resourceLoader = resourceLoader;
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, resourceLoader);
    }

    public enum ResourceLoaderType {
        JAR("jar", JarResourceLoader.class),
        FILE("file", FileResourceLoader.class),
        URL("url", URLResourceLoader.class),
        STRING("string", StringResourceLoader.class),
        DATASOURCE("datasource", DataSourceResourceLoader.class),
        CLASSPATH("classpath", ClasspathResourceLoader.class);
        private String name;
        private Class<? extends ResourceLoader> resourceLoaderClass;

        ResourceLoaderType(String name, Class<? extends ResourceLoader> resourceLoaderClass) {
            this.name = name;
            this.resourceLoaderClass = resourceLoaderClass;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private void checkCache(byte cacheFlag) {
        if (this.cacheFlag == cacheFlag) {
            throw new RuntimeException("can't open or close the cache at the same time.");
        }
    }

    public VelocityEngineBuilder openCache() {
        checkCache((byte) 2);
        cacheFlag = 1;
        velocityEngine.setProperty(resourceLoader + ".resource.loader.cache", true);
        return this;
    }

    public VelocityEngineBuilder closeCache() {
        checkCache((byte) 1);
        cacheFlag = 2;
        velocityEngine.setProperty(resourceLoader + ".resource.loader.cache", false);
        return this;
    }

    public VelocityEngineBuilder loaderClass(Class<? extends ResourceLoader> resourceLoaderClass) {
        velocityEngine.setProperty(resourceLoader + ".resource.loader.class",
                resourceLoaderClass.getName());
        return this;
    }

    public VelocityEngineBuilder loaderPath(String loaderPath) {
        velocityEngine.setProperty(resourceLoader + ".resource.loader.path", loaderPath);
        return this;
    }

    public VelocityEngineBuilder checkInterval(long checkInterval) {
        velocityEngine.setProperty(resourceLoader + ".resource.loader.modificationCheckInterval",
                checkInterval);
        return this;
    }

    public VelocityEngineBuilder inputEncoding(String encoding) {
        velocityEngine.setProperty(RuntimeConstants.INPUT_ENCODING, encoding);
        return this;
    }

    public VelocityEngineBuilder outputEncoding(String encoding) {
        velocityEngine.setProperty(RuntimeConstants.OUTPUT_ENCODING, encoding);
        return this;
    }

    public VelocityEngineBuilder runtimeLog(LogChute log) {
        velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, log);
        return this;
    }

    public VelocityEngineBuilder properties(String key, String value) {
        velocityEngine.setProperty(key, value);
        return this;
    }

    public VelocityEngine build() {
        if (resourceLoaderType != null) {
            loaderClass(resourceLoaderType.resourceLoaderClass);
        }
        velocityEngine.init();
        return velocityEngine;
    }
}
