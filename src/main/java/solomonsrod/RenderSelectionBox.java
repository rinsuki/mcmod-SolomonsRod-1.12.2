package solomonsrod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderSelectionBox {
    @SubscribeEvent
    public void onRenderSelectionBox(RenderWorldLastEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        World world = Minecraft.getMinecraft().world;
        if (player != null && world != null) {
            ItemStack itemStack = player.getHeldItemMainhand();
            if (itemStack.isEmpty()) {
                return;
            }
            if ((itemStack.getItem() instanceof ItemSolomon) || (itemStack.getItem() instanceof ItemDemonsRod)) {
                BlockPos objectMouseOverPos = getBlockPosIfMouseHitBlock();
                BlockPos blockPos = objectMouseOverPos != null ? objectMouseOverPos : ItemSolomon.getAvailablePosition(player);
                if (world.isAirBlock(blockPos)) {
                    renderBlockSelectionBox(blockPos, world, player, event.getPartialTicks());
                }
            }
        }
    }

    public void renderBlockSelectionBox(BlockPos blockPos, World world, EntityPlayer player, float partialTickItem) {
        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.4f);
        GL11.glLineWidth(2.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        RenderGlobal.drawSelectionBoundingBox(world.getBlockState(blockPos).getBoundingBox(world, blockPos).offset(blockPos).grow(0.002d, 0.002d, 0.002d).offset(-(player.lastTickPosX + ((player.posX - player.lastTickPosX) * ((double) partialTickItem))), -(player.lastTickPosY + ((player.posY - player.lastTickPosY) * ((double) partialTickItem))), -(player.lastTickPosZ + ((player.posZ - player.lastTickPosZ) * ((double) partialTickItem)))), 0.0f, 0.0f, 0.0f, 0.4f);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    private BlockPos getBlockPosIfMouseHitBlock() {
        Minecraft mc = Minecraft.getMinecraft();
        RayTraceResult objectMouseOver = mc.objectMouseOver;
        if (objectMouseOver == null || !objectMouseOver.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
            return null;
        }
        if (mc.world.getBlockState(objectMouseOver.getBlockPos()).getBlock() == SolomonsRod.solomonsBlock) {
            return objectMouseOver.getBlockPos();
        }
        return objectMouseOver.getBlockPos().offset(objectMouseOver.sideHit);
    }
}
