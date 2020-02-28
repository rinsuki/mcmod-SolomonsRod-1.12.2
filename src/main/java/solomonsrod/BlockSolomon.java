package solomonsrod;

import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class BlockSolomon extends Block {
    public static final PropertyBool CRASH = PropertyBool.create("crash");

    public BlockSolomon() {
        super(Material.GOURD);
        setUnlocalizedName("solomonsBlock");
        setBlockUnbreakable().setResistance(1.0f);
        setDefaultState(this.blockState.getBaseState().withProperty(CRASH, false));
        setRegistryName("solomonsblock");
    }

    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos blockPos) {
        return new AxisAlignedBB(0.0d, 0.1d, 0.0d, 1.0d, 1.0d, 1.0d);
    }

    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState state, Entity entity) {
        if (world.isRemote) {
            if ((entity instanceof EntityCreature) && world.getTotalWorldTime() % 10 == 0) {
                PacketHandler.INSTANCE.sendToServer(new MessagePlayerJump(true, blockPos));
                world.playSound((EntityPlayer) null, ((double) blockPos.getX()) + 0.5d,
                        ((double) blockPos.getY()) + 0.5d, ((double) blockPos.getZ()) + 0.5d, SolomonsRod.crashSound,
                        SoundCategory.BLOCKS, 1.5f, 1.0f);
            } else if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.posX >= ((double) blockPos.getX()) - 0.29d
                        && player.posX <= ((double) blockPos.getX()) + 1.29d
                        && player.posZ >= ((double) blockPos.getZ()) - 0.29d
                        && player.posZ <= ((double) blockPos.getZ()) + 1.29d
                        && player.posY <= ((double) blockPos.getY())) {
                    if (((Boolean) ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class, player,
                            "field_70703_bu")).booleanValue() && !player.capabilities.isFlying
                            && world.getTotalWorldTime() % 2 == 0) {
                        PacketHandler.INSTANCE.sendToServer(new MessagePlayerJump(true, blockPos));
                        world.playSound((EntityPlayer) null, ((double) blockPos.getX()) + 0.5d,
                                ((double) blockPos.getY()) + 0.5d, ((double) blockPos.getZ()) + 0.5d,
                                SolomonsRod.crashSound, SoundCategory.BLOCKS, 1.5f, 1.0f);
                    }
                }
            }
        }
    }

    public void blockCrash(World world, BlockPos blockPos, IBlockState state) {
        if (!((Boolean) world.getBlockState(blockPos).getValue(CRASH)).booleanValue()) {
            world.setBlockState(blockPos, state.withProperty(CRASH, true));
        } else {
            world.setBlockToAir(blockPos);
        }
        world.playSound((EntityPlayer) null, blockPos.add(0.5d, 0.5d, 0.5d), SolomonsRod.crashSound,
                SoundCategory.BLOCKS, 1.5f, 1.0f);
    }

    public int quantityDropped(Random rnd) {
        return 0;
    }

    /* access modifiers changed from: protected */
    @Nonnull
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { CRASH });
    }

    public int getMetaFromState(IBlockState state) {
        if (state == null) {
            return 0;
        }
        if (((Boolean) state.getValue(CRASH)).booleanValue()) {
            return 1;
        }
        return 0;
    }
}
