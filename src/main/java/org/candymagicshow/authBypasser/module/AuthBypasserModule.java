package org.candymagicshow.authBypasser.module;

import com.zenith.module.api.Module;
import com.zenith.network.codec.PacketHandlerCodec;
import com.zenith.network.codec.PacketHandlerStateCodec;
import org.candymagicshow.authBypasser.AuthBypasserPlugin;
import org.candymagicshow.authBypasser.BuildConstants;
import org.candymagicshow.authBypasser.handler.OfflineAuthenticateHelloHandler;
import org.candymagicshow.authBypasser.handler.OfflineUuidHelloHandler;
import org.geysermc.mcprotocollib.protocol.data.ProtocolState;
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundHelloPacket;
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundHelloPacket;

public class AuthBypasserModule extends Module {
    private static final int PRIORITY = 100_000;

    @Override
    public boolean enabledSetting() {
        return AuthBypasserPlugin.CONFIG.enabled;
    }

    @Override
    public PacketHandlerCodec registerClientPacketHandlerCodec() {
        return PacketHandlerCodec.clientBuilder()
            .setId(BuildConstants.PLUGIN_ID + "-login-fix")
            .setPriority(PRIORITY)
            .state(ProtocolState.LOGIN, PacketHandlerStateCodec.clientBuilder()
                .inbound(ClientboundHelloPacket.class, new OfflineAuthenticateHelloHandler())
                .outbound(ServerboundHelloPacket.class, new OfflineUuidHelloHandler())
                .build())
            .build();
    }
}
