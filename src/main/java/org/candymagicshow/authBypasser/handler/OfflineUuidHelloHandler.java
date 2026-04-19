package org.candymagicshow.authBypasser.handler;

import com.zenith.network.client.ClientSession;
import com.zenith.network.codec.PacketHandler;
import com.zenith.util.config.Config;
import org.candymagicshow.authBypasser.AuthBypasserPlugin;
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundHelloPacket;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.zenith.Globals.CONFIG;

public class OfflineUuidHelloHandler implements PacketHandler<ServerboundHelloPacket, ClientSession> {
    @Override
    public ServerboundHelloPacket apply(final ServerboundHelloPacket packet, final ClientSession session) {
        if (CONFIG.authentication.accountType != Config.Authentication.AccountType.OFFLINE) {
            return packet;
        }
        if (!AuthBypasserPlugin.CONFIG.rewriteOfflineUuid) {
            return packet;
        }

        final UUID offlineUuid = UUID.nameUUIDFromBytes(
            ("OfflinePlayer:" + packet.getUsername()).getBytes(StandardCharsets.UTF_8)
        );
        if (offlineUuid.equals(packet.getProfileId())) {
            return packet;
        }

        AuthBypasserPlugin.LOG.info(
            "Rewriting offline login UUID for {} to {}",
            packet.getUsername(),
            offlineUuid
        );
        return new ServerboundHelloPacket(packet.getUsername(), offlineUuid);
    }
}
