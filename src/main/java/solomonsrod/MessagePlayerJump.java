package solomonsrod;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessagePlayerJump implements IMessage {
    public boolean isJumping;
    public int posX;
    public int posY;
    public int posZ;

    public MessagePlayerJump() {
    }

    public MessagePlayerJump(boolean jump, BlockPos blockPos) {
        this.isJumping = jump;
        this.posX = blockPos.getX();
        this.posY = blockPos.getY();
        this.posZ = blockPos.getZ();
    }

    public void fromBytes(ByteBuf buf) {
        this.isJumping = buf.readBoolean();
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.isJumping);
        buf.writeInt(this.posX);
        buf.writeInt(this.posY);
        buf.writeInt(this.posZ);
    }

    public BlockPos getBlockPos() {
        return new BlockPos(this.posX, this.posY, this.posZ);
    }
}
