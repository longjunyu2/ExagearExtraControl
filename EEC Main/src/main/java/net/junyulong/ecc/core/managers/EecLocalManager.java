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

package net.junyulong.ecc.core.managers;

import net.junyulong.ecc.core.configuration.ConfigReader;
import net.junyulong.ecc.core.errors.EecException;
import net.junyulong.ecc.core.local.Lang;
import net.junyulong.ecc.core.local.LocalStrings;

public class EecLocalManager {

    private Lang mLang;
    private final ConfigReader config;

    public EecLocalManager(ConfigReader configuration) {
        this.config = configuration;
    }

    public void attach() {
        mLang = LocalStrings.getLangFormLocalName(config.getLocal());
        if (mLang == null)
            throw new EecException("Lang is null.");
        else
            LocalStrings.initialize(mLang);
    }

    public void detach() {
        mLang = null;
    }

    public Lang getLang() {
        return this.mLang;
    }
}
