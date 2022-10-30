package v2;

import utils.YamlUtil;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class ApplicationConfig {
    private String[] hostNames;
    private int port;
    private final AtomicInteger inboundChannelCount;

    public ApplicationConfig() {
        inboundChannelCount = new AtomicInteger();
    }

    public void incrementChannelCount() {
        inboundChannelCount.incrementAndGet();
    }

    public int getInboundChannelCount() {
        return inboundChannelCount.get();
    }

    public String[] getHostNames() {
        return hostNames;
    }

    public void setHostNames(String[] hostNames) {
        this.hostNames = hostNames;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static ApplicationConfig getApplicationConfig(String configFile) {
        File fileHandler = getClassFolderResourceFile(configFile, ApplicationConfig.class);

        if (!fileHandler.isFile() && !fileHandler.exists()) {
            throw new IllegalArgumentException(String.format("You can not load configuration file from : %s", configFile));
        }

        return YamlUtil.toObject(fileHandler, ApplicationConfig.class);
    }

    private static File getClassFolderResourceFile(String resourceName, Class clazz) {
        final String resolvedResourcePath = resolveResourcePath(resourceName);
        return new File(clazz.getResource(resolvedResourcePath).getFile());
    }

    private static String resolveResourcePath(String resourceName) {
        if (resourceName.startsWith("/")) {
            return resourceName;
        }
        return new StringBuilder().append("/")
                .append(resourceName).toString();
    }
}
