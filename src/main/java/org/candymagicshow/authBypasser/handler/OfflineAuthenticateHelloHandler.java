package org.candymagicshow.authBypasser.handler;

import com.zenith.feature.api.sessionserver.SessionServerApi;
import com.zenith.network.client.ClientSession;
import com.zenith.network.codec.PacketHandler;
import com.zenith.util.config.Config;
import org.candymagicshow.authBypasser.AuthBypasserPlugin;
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundHelloPacket;
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundKeyPacket;

import javax.crypto.SecretKey;

import static com.zenith.Globals.CONFIG;

public class OfflineAuthenticateHelloHandler implements PacketHandler<ClientboundHelloPacket, ClientSession> {
    @Override
    public ClientboundHelloPacket apply(final ClientboundHelloPacket packet, final ClientSession session) {
        if (CONFIG.authentication.accountType != Config.Authentication.AccountType.OFFLINE) {
            return packet;
        }
        if (!AuthBypasserPlugin.CONFIG.bypassAuthenticateHello || !packet.isShouldAuthenticate()) {
            return packet;
        }

        final SecretKey key = SessionServerApi.INSTANCE.generateClientKey();
        if (key == null) {
            session.disconnect("Failed to generate secret key.");
            return null;
        }

        AuthBypasserPlugin.LOG.info(
            "Bypassing authenticate=true hello for offline account: {}",
            session.getProfile().getName()
        );
        session.send(
            new ServerboundKeyPacket(packet.getPublicKey(), key, packet.getChallenge()),
            future -> session.enableEncryption(key)
        );
        return null;
    }
}
