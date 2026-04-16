package org.candymagicshow.authBypasser;

import com.zenith.plugin.api.Plugin;
import com.zenith.plugin.api.PluginAPI;
import com.zenith.plugin.api.ZenithProxyPlugin;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.candymagicshow.authBypasser.command.ExampleCommand;
import org.candymagicshow.authBypasser.command.ExampleESPCommand;
import org.candymagicshow.authBypasser.command.ExampleWanderCommand;
import org.candymagicshow.authBypasser.module.ExampleESPModule;
import org.candymagicshow.authBypasser.module.ExampleModule;
import org.candymagicshow.authBypasser.module.ExampleWanderModule;

@Plugin(
    id = BuildConstants.PLUGIN_ID,
    version = BuildConstants.VERSION,
    description = "ZenithProxy Example Plugin",
    url = "https://github.com/rfresh2/ZenithProxyExamplePlugin",
    authors = {"rfresh2"},
    mcVersions = {BuildConstants.MC_VERSION} // to indicate any MC version: @Plugin(mcVersions = "*")
)
public class ExamplePlugin implements ZenithProxyPlugin {
    // public static for simple access from modules and commands
    // or alternatively, you could pass these around in constructors
    public static ExampleConfig PLUGIN_CONFIG;
    public static ComponentLogger LOG;

    @Override
    public void onLoad(PluginAPI pluginAPI) {
        LOG = pluginAPI.getLogger();
        LOG.info("Example Plugin loading...");
        // initialize any configurations before modules or commands might need to read them
        PLUGIN_CONFIG = pluginAPI.registerConfig(BuildConstants.PLUGIN_ID, ExampleConfig.class);
        pluginAPI.registerModule(new ExampleModule());
        pluginAPI.registerModule(new ExampleESPModule());
        pluginAPI.registerModule(new ExampleWanderModule());
        pluginAPI.registerCommand(new ExampleCommand());
        pluginAPI.registerCommand(new ExampleESPCommand());
        pluginAPI.registerCommand(new ExampleWanderCommand());
        LOG.info("Example Plugin loaded!");
    }
}
