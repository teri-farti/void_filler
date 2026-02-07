// voidfiller.java
package com.terifarti.voidfiller;

import com.terifarti.voidfiller.block.ModBlocks;
import com.terifarti.voidfiller.blockentity.ModBlockEntities;
import com.terifarti.voidfiller.entity.ModEntities;
import com.terifarti.voidfiller.entity.VoidEnemy;
import com.terifarti.voidfiller.item.ModItems;
import com.terifarti.voidfiller.item.VoidArmorMaterial;
import com.terifarti.voidfiller.item.VoidToolTier;
import com.terifarti.voidfiller.screen.VoidExtractorScreen;
import com.terifarti.voidfiller.world.ModFeatures;
import com.terifarti.voidfiller.world.VoidUnderbedrockFeature;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.terifarti.voidfiller.event.*;


@Mod(voidfiller.MODID)
public class voidfiller {

    public static final String MODID = "voidfiller";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.VOID_ENEMY.get(), VoidEnemy.createAttributes().build());
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.VOID_ENEMY.get(), context ->
                new net.minecraft.client.renderer.entity.MobRenderer<VoidEnemy, net.minecraft.client.model.HumanoidModel<VoidEnemy>>(
                        context,
                        new net.minecraft.client.model.HumanoidModel<>(context.bakeLayer(net.minecraft.client.model.geom.ModelLayers.ZOMBIE)),
                        0.5f
                ) {
                    @Override
                    public ResourceLocation getTextureLocation(VoidEnemy entity) {
                        return ResourceLocation.fromNamespaceAndPath("voidfiller", "textures/entity/void_enemy.png");
                    }
                }
        );
    }



    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> VOID_FILLER_TAB =
            CREATIVE_MODE_TABS.register("void_filler_tab", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.voidfiller"))
                            .icon(() -> ModItems.SOLID_VOID_ITEM.get().getDefaultInstance())
                            .displayItems((params, output) -> {

                                ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));

                                ModBlocks.BLOCKS.getEntries().forEach(block ->
                                        output.accept(block.get().asItem()));
                            })
                            .build()
            );
    public voidfiller(IEventBus modEventBus, ModContainer modContainer) {
        ModFeatures.FEATURES.register(modEventBus);
        VoidArmorMaterial.ARMOR_MATERIALS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerAttributes);

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(this::registerRenderers);
        }

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(VoidEvents.class);
        NeoForge.EVENT_BUS.register(BedrockBreakHandler.class);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }





    private void commonSetup(FMLCommonSetupEvent event) {

        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
