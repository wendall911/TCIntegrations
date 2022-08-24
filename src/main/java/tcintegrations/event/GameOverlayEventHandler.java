package tcintegrations.event;

import java.util.ArrayList;
import java.util.Arrays;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.api.Lib;
import blusunrize.immersiveengineering.api.utils.FastEither;
import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.items.VoltmeterItem;
import blusunrize.immersiveengineering.common.network.MessageRequestEnergyUpdate;
import blusunrize.immersiveengineering.common.network.MessageRequestRedstoneUpdate;
import blusunrize.immersiveengineering.common.register.IEItems;
import blusunrize.immersiveengineering.common.util.Utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.modifiers.armor.MultiVisionModifier;

public class GameOverlayEventHandler {

    @SubscribeEvent()
    public void onRenderOverlayPost(RenderGameOverlayEvent.PostLayer event) {
        if (ModList.get().isLoaded(ModIntegration.IE_MODID)) {
            int scaledWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int scaledHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            Player player = Minecraft.getInstance().player;
            boolean hasVoltmeter = false;

            if (player != null && event.getOverlay() == ForgeIngameGui.HUD_TEXT_ELEMENT) {
                ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);

                for (InteractionHand hand : InteractionHand.values()) {
                    if (!player.getItemInHand(hand).isEmpty()) {
                        ItemStack equipped = player.getItemInHand(hand);

                        if (equipped.getItem() == IEItems.Tools.VOLTMETER.get()) {
                            hasVoltmeter = true;
                        }
                    }
                }

                if (!stack.isEmpty() && !hasVoltmeter) {
                    CompoundTag tags = stack.getTag();

                    if (tags != null && tags.contains(MultiVisionModifier.VOLTMETER)) {
                        PoseStack transform = new PoseStack();

                        MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
                        renderVoltmeterOverlay(player, scaledWidth, scaledHeight, transform, buffer);

                        buffer.endBatch();
                    }
                }
            }
        }
    }

    /*
     * Derived from https://github.com/BluSunrize/ImmersiveEngineering/blob/ee7d8664aa7c4b5a2e73ed39b14233af496d239e/src/main/java/blusunrize/immersiveengineering/client/ClientEventHandler.java#L569
     *
     * BluSunrize
     * Copyright (c) 2017
     *
     * This code is licensed under "Blu's License of Common Sense"
     * Details can be found in the license file in the root folder of this project
     */

    private void renderVoltmeterOverlay(Player player, float scaledWidth, float scaledHeight, PoseStack transform, MultiBufferSource buffer) {
        HitResult rrt = ClientUtils.mc().hitResult;
        FastEither<BlockPos, Integer> pos = null;

        if (rrt instanceof BlockHitResult mop) {
            pos = FastEither.left(mop.getBlockPos());
        }
        else if (rrt instanceof EntityHitResult ehr) {
            pos = FastEither.right(ehr.getEntity().getId());
        }

        if (pos == null) return;

        ArrayList<String> text = new ArrayList<>();

        boolean matches = VoltmeterItem.lastEnergyUpdate.pos().equals(pos);
        long sinceLast = player.level.getGameTime() - VoltmeterItem.lastEnergyUpdate.measuredInTick();

        if (!matches || sinceLast > 20) {
            ImmersiveEngineering.packetHandler.sendToServer(new MessageRequestEnergyUpdate(pos));
        }

        if (VoltmeterItem.lastEnergyUpdate.isValid() && matches) {
            int maxStorage = VoltmeterItem.lastEnergyUpdate.capacity();
            int storage = VoltmeterItem.lastEnergyUpdate.stored();
            String storageText = Utils.toScientificNotation(storage, "0##", 100000);
            String capacityText = Utils.toScientificNotation(maxStorage, "0##", 100000);

            text.addAll(Arrays.asList(I18n.get(Lib.DESC_INFO + "energyStored", "<br>"
                        + storageText + " / " + capacityText).split("<br>")));
        }

        if (pos.isLeft()) {
            matches = VoltmeterItem.lastRedstoneUpdate.pos().equals(pos.leftNonnull());
            sinceLast = player.level.getGameTime() - VoltmeterItem.lastRedstoneUpdate.measuredInTick();

            if (!matches || sinceLast > 20) {
                ImmersiveEngineering.packetHandler.sendToServer(new MessageRequestRedstoneUpdate(pos.leftNonnull()));
            }

            if (VoltmeterItem.lastRedstoneUpdate.isSignalSource() && matches) {
                text.addAll(Arrays.asList(I18n.get(Lib.DESC_INFO + "redstoneLevel", "<br>"
                        + VoltmeterItem.lastRedstoneUpdate.rsLevel()).split("<br>")));
            }
        }

        int col = 0xffffff;
        int i = 0;

        RenderSystem.enableBlend();

        for (String s : text) {
            if (s != null) {
                s = s.trim();

                int w = ClientUtils.font().width(s);

                ClientUtils.font().drawInBatch(
                        s, scaledWidth / 2 - w / 2f,
                        scaledHeight / 2 + 4 + (i++) * (ClientUtils.font().lineHeight + 2), col,
                        false, transform.last().pose(), buffer, false,
                        0, 0xf000f0
                );
            }
        }

        RenderSystem.disableBlend();
    }

}
