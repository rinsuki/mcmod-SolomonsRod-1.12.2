package solomonsrod;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(SolomonsRod.MOD_ID.toLowerCase());

    public static void init() {
        INSTANCE.registerMessage(MessagePlayerJumpHandler.class, MessagePlayerJump.class, 0, Side.SERVER);
    }
}
