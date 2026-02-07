package com.terifarti.voidfiller.screen;

import com.terifarti.voidfiller.blockentity.VoidGeneratorBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class VoidGeneratorScreen extends Screen {

    private final VoidGeneratorBlockEntity be;

    public VoidGeneratorScreen(VoidGeneratorBlockEntity be) {
        super(Component.literal("Void Generator"));
        this.be = be;
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTicks) {
        super.render(g, mouseX, mouseY, partialTicks);

        int x = this.width / 2 - 100;
        int y = this.height / 2 - 80;

        g.fill(x, y, x + 200, y + 160, 0xAA000000);

        g.drawString(this.font, "Void Generator", x + 10, y + 10, 0xFFFFFF);
        ItemStack fuelStack = be.getInventory().getStackInSlot(0);
        g.drawString(this.font, "Fuel: " + fuelStack.getCount() + " bs", x + 10, y + 55, 0xFFFFFF);

        int burnWidth = (int) (be.getBurnTime() * 0.5);
        g.fill(x + 10, y + 85, x + 10 + burnWidth, y + 100, 0xFFFF5500);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}