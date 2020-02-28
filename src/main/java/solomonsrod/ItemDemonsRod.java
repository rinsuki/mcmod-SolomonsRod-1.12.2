package solomonsrod;

import javax.annotation.Nonnull;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDemonsRod extends Item {
    private final Item solomonsRod;

    public ItemDemonsRod(Item rod) {
        setUnlocalizedName("demonsRod");
        setMaxDamage(192);
        setCreativeTab(CreativeTabs.TOOLS);
        setHasSubtypes(true);
        this.solomonsRod = rod;
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (player.getEntityWorld().isRemote) {
            return false;
        }
        if (!(entity instanceof EntityAnimal) && !(entity instanceof EntitySlime) && !(entity instanceof EntityVillager) && !(entity instanceof EntityWaterMob)) {
            return false;
        }
        player.swingArm(EnumHand.MAIN_HAND);
        entity.setDead();
        stack.damageItem(1, player);
        player.getEntityWorld().playSound((EntityPlayer) null, entity.posX + 0.5d, entity.posY + 0.5d, entity.posZ + 0.5d, SolomonsRod.bamSound, SoundCategory.BLOCKS, 1.5f, 1.0f);
        for (int i = 0; i < 8; i++) {
            player.getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX + Math.random(), entity.posY + Math.random(), entity.posZ + Math.random(), 0.0d, 0.0d, 0.0d, new int[0]);
        }
        return true;
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
        return this.solomonsRod.onItemRightClick(worldIn, playerIn, hand);
    }

    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return this.solomonsRod.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    public int getItemEnchantability() {
        return this.solomonsRod.getItemEnchantability();
    }
}
