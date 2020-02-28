package solomonsrod;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(dependencies = "required-after:forge@[13.20.0,)", modid = "solomonsrod", name = "SolomonsRod", useMetadata = true, version = SolomonsRod.MOD_VERSION)
public class SolomonsRod {
    public static final String MOD_DEPENDENCIES = "required-after:forge@[13.20.0,)";
    public static final String MOD_ID = "solomonsrod";
    public static final String MOD_NAME = "SolomonsRod";
    public static final String MOD_VERSION = "1.12.2-1.3.1";
    public static ResourceLocation bamSoundRL = new ResourceLocation(MOD_ID, "solomon.bam");
    public static SoundEvent bamSound = new SoundEvent(bamSoundRL).setRegistryName(bamSoundRL);
    public static ResourceLocation crashSoundRL = new ResourceLocation(MOD_ID, "solomon.crash");
    public static SoundEvent crashSound = new SoundEvent(crashSoundRL).setRegistryName(crashSoundRL);
    public static ResourceLocation createSoundRL = new ResourceLocation(MOD_ID, "solomon.create");
    public static SoundEvent createSound = new SoundEvent(createSoundRL).setRegistryName(createSoundRL);
    public static Item demonsRod;
    public static ResourceLocation eraseSoundRL = new ResourceLocation(MOD_ID, "solomon.erase");
    public static SoundEvent eraseSound = new SoundEvent(eraseSoundRL).setRegistryName(eraseSoundRL);
    public static ResourceLocation nocrashSoundRL = new ResourceLocation(MOD_ID, "solomon.nocrash");
    public static SoundEvent nocrashSound = new SoundEvent(nocrashSoundRL).setRegistryName(nocrashSoundRL);
    public static Block solomonsBlock;
    public static Item solomonsRod;

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.init();

        if (event.getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new RenderSelectionBox());
        }
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        solomonsBlock = new BlockSolomon();
        event.getRegistry().register(solomonsBlock);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        solomonsRod = new ItemSolomon().setRegistryName(MOD_ID);
        demonsRod = new ItemDemonsRod(solomonsRod).setRegistryName("demonsrod");
        event.getRegistry().registerAll(solomonsRod, demonsRod);
    }

    @SubscribeEvent
    public void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(crashSound, bamSound, createSound, nocrashSound, eraseSound);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
        registerBlockClient(solomonsBlock, "solomonsblock");
        registerItemClient(solomonsRod, MOD_ID);
        registerItemClient(demonsRod, "demonsrod");
    }

    private void registerBlockClient(Block block, String name) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation("solomonsrod:" + name, "inventory"));
    }

    private void registerItemClient(Item item, String name) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation("solomonsrod:" + name, "inventory"));
    }
}
