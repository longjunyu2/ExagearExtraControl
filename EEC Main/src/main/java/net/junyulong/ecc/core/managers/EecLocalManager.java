package net.junyulong.ecc.core.managers;

import net.junyulong.ecc.core.configuration.ConfigReader;
import net.junyulong.ecc.core.errors.EecException;
import net.junyulong.ecc.core.local.Lang;
import net.junyulong.ecc.core.local.LocalStrings;

public class EecLocalManager {

    private Lang mLang;
    private static final Lang DEFAULT_LANG = Lang.Chinese;
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
