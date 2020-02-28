package solomonsrod;

import javax.annotation.Nonnull;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSolomon extends Item {
    private static final double PLAYER_ARM_HEIGHT = 1.29d;

    public ItemSolomon() {
        setUnlocalizedName("solomonRod");
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxDamage(192);
        setHasSubtypes(true);
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
        if (worldIn.isRemote) {
            return ItemSolomon.super.onItemRightClick(worldIn, playerIn, hand);
        }
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        BlockPos availablePos = getAvailablePosition(playerIn);
        if (worldIn.isAirBlock(availablePos)) {
            worldIn.setBlockState(availablePos, SolomonsRod.solomonsBlock.getDefaultState());
            worldIn.playSound((EntityPlayer) null, ((double) availablePos.getX()) + 0.5d, ((double) availablePos.getY()) + 0.5d, ((double) availablePos.getZ()) + 0.5d, SolomonsRod.createSound, SoundCategory.BLOCKS, 1.5f, 1.0f);
            playerIn.swingArm(hand);
            itemStackIn.damageItem(1, playerIn);
        } else {
            worldIn.playSound((EntityPlayer) null, ((double) availablePos.getX()) + 0.5d, ((double) availablePos.getY()) + 0.5d, ((double) availablePos.getZ()) + 0.5d, SolomonsRod.nocrashSound, SoundCategory.BLOCKS, 1.5f, 1.0f);
            playerIn.swingArm(hand);
        }
        return ItemSolomon.super.onItemRightClick(worldIn, playerIn, hand);
    }

    public static BlockPos getAvailablePosition(EntityPlayer player) {
        int pitch = (int) ((((getRotate((double) player.rotationPitch) + 105.0d) + 360.0d) % 360.0d) / 60.0d);
        int yaw = (int) ((((getRotate((double) player.rotationYaw) + 22.5d) + 360.0d) % 360.0d) / 45.0d);
        int y = 2 - pitch;
        int x = ((yaw & 3) == 0 || !(pitch == 1 || pitch == 2)) ? 0 : (yaw & 4) == 0 ? -1 : 1;
        int z = ((yaw & 3) == 2 || !(pitch == 1 || pitch == 2)) ? 0 : ((yaw + -2) & 4) == 0 ? -1 : 1;
        return new BlockPos(MathHelper.ceil(player.posX + (((double) x) * PLAYER_ARM_HEIGHT)) - 1, MathHelper.ceil((pitch == 0 ? 0.62d : 0.0d) + ((double) y) + player.posY), MathHelper.ceil(player.posZ + (((double) z) * PLAYER_ARM_HEIGHT)) - 1);
    }

    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.getBlockState(pos).getBlock() == SolomonsRod.solomonsBlock) {
            worldIn.setBlockToAir(pos);
            worldIn.playSound((EntityPlayer) null, ((double) pos.getX()) + 0.5d, ((double) pos.getY()) + 0.5d, ((double) pos.getZ()) + 0.5d, SolomonsRod.eraseSound, SoundCategory.BLOCKS, 1.5f, 1.0f);
            return EnumActionResult.SUCCESS;
        }
        if (worldIn.getBlockState(pos.offset(facing)).getBlock() == Blocks.AIR) {
            if (worldIn.mayPlace(SolomonsRod.solomonsBlock, pos.offset(facing), false, facing, playerIn)) {
                worldIn.setBlockState(pos.offset(facing), SolomonsRod.solomonsBlock.getDefaultState());
                worldIn.playSound((EntityPlayer) null, ((double) pos.getX()) + 0.5d, ((double) pos.getY()) + 0.5d, ((double) pos.getZ()) + 0.5d, SolomonsRod.createSound, SoundCategory.BLOCKS, 1.5f, 1.0f);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

    private static double getRotate(double rt) {
        double i = rt;
        while (i < 0.0d) {
            i += 360.0d;
        }
        return i;
    }

    public int getItemEnchantability() {
        return 1;
    }
}
