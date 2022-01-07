package net.junyulong.ecc.core.configuration;

public class ConfigReader {

    private final Configuration mConfiguration;

    public ConfigReader(Configuration config) {
        this.mConfiguration = config;
    }

    public String getLocal() {
        return mConfiguration.Local;
    }

}
