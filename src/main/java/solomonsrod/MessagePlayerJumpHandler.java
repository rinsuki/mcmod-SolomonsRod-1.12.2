package solomonsrod;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePlayerJumpHandler implements IMessageHandler<MessagePlayerJump, IMessage> {
    public IMessage onMessage(MessagePlayerJump message, MessageContext ctx) {
        if (!message.isJumping) {
            return null;
        }
        World world = ctx.getServerHandler().player.getEntityWorld();
        BlockPos blockPos = message.getBlockPos();
        blockCrash(world, blockPos, world.getBlockState(blockPos));
        return null;
    }

    public void blockCrash(World world, BlockPos blockPos, IBlockState state) {
        if (!((Boolean) world.getBlockState(blockPos).getValue(BlockSolomon.CRASH)).booleanValue()) {
            world.setBlockState(blockPos, state.withProperty(BlockSolomon.CRASH, true));
        } else {
            world.setBlockToAir(blockPos);
        }
        world.playSound((EntityPlayer) null, ((double) blockPos.getX()) + 0.5d, ((double) blockPos.getY()) + 0.5d, ((double) blockPos.getZ()) + 0.5d, SolomonsRod.crashSound, SoundCategory.BLOCKS, 1.5f, 1.0f);
    }
}
