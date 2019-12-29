package com.duskitty.glowingbeds.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Either;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

import java.util.List;

import static net.minecraft.server.command.CommandManager.literal;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    public PlayerEntityMixin() {
        super();
    }

    @Inject(method = "trySleep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntities(Ljava/lang/Class;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void getEntity(BlockPos pos, CallbackInfoReturnable<Either> cir, double d, double e) {
        List<HostileEntity> list2 = ((PlayerEntity)(Object) this).world.getEntities(HostileEntity.class, new Box((double)pos.getX() - 8.0D, (double)pos.getY() - 5.0D, (double)pos.getZ() - 8.0D, (double)pos.getX() + 8.0D, (double)pos.getY() + 5.0D, (double)pos.getZ() + 8.0D), (hostileEntity) -> {
           hostileEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING,60,1) );
           return hostileEntity.isAngryAt(((PlayerEntity)(Object) this));
        });
    }


}
