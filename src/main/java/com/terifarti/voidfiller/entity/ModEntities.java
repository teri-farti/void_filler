package com.terifarti.voidfiller.entity;

import com.terifarti.voidfiller.voidfiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, voidfiller.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<VoidEnemy>> VOID_ENEMY =
            ENTITIES.register("void_enemy",
                    () -> EntityType.Builder.of(VoidEnemy::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.95f)
                            .build("void_enemy"));
}