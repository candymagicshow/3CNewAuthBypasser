package org.candymagicshow.authBypasser;

import com.zenith.plugin.api.Plugin;
import com.zenith.plugin.api.PluginAPI;
import com.zenith.plugin.api.ZenithProxyPlugin;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.candymagicshow.authBypasser.module.AuthBypasserModule;

@Plugin(
    id = BuildConstants.PLUGIN_ID,
    version = BuildConstants.VERSION,
    description = "Fixes offline login flow for compatible hybrid-auth servers",
    authors = {"CandyMagicShow"},
    mcVersions = {BuildConstants.MC_VERSION}
)
public class AuthBypasserPlugin implements ZenithProxyPlugin {
    public static AuthBypasserConfig CONFIG;
    public static ComponentLogger LOG;

    @Override
    public void onLoad(PluginAPI pluginAPI) {
        LOG = pluginAPI.getLogger();
        CONFIG = pluginAPI.registerConfig(BuildConstants.PLUGIN_ID, AuthBypasserConfig.class);
        pluginAPI.registerModule(new AuthBypasserModule());
        LOG.info("Loaded auth bypass login fixes");
    }
}
