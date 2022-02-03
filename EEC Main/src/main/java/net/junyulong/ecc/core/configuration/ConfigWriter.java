/*
 * Copyright 2022 Junyu Long
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
