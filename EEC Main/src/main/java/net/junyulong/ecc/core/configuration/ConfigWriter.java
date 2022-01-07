package net.junyulong.ecc.core.configuration;

import net.junyulong.ecc.core.local.LocalStrings;

public class ConfigWriter {

    private final Configuration configuration;

    private final ConfigReader reader;

    public ConfigWriter() {
        this.configuration = new Configuration();
        this.reader = new ConfigReader(configuration);
    }

    public boolean importConfigure(String filePath) {
        // TODO: 导入配置文件
        return true;
    }

    public boolean exportConfigure(String filePath) {
        // TODO: 导出配置文件
        return true;
    }

    public ConfigReader getReader() {
        return this.reader;
    }

    public boolean setLocal(String localName) {
        if (LocalStrings.getLangFormLocalName(localName) != null) {
            configuration.Local = localName;
            return true;
        } else {
            return false;
        }
    }

}
