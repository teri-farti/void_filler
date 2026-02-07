package com.terifarti.voidfiller.screen;

import com.terifarti.voidfiller.blockentity.VoidExtractorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class VoidExtractorScreen extends Screen {

    private final VoidExtractorBlockEntity be;

    public VoidExtractorScreen(VoidExtractorBlockEntity be) {
        super(Component.literal("Void Extractor"));
        this.be = be;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTicks) {
        super.render(g, mouseX, mouseY, partialTicks);

        int x = this.width / 2 - 100;
        int y = this.height / 2 - 80;

        g.fill(x, y, x + 200, y + 160, 0xAA000000);

        g.drawString(this.font, "Void Extractor", x + 10, y + 10, 0xFFFFFF);


        g.drawString(this.font, "Energy: " + be.getEnergy(), x + 10, y + 40, 0x55FF55);


        int progressWidth = (int) (be.getProgress() * 1.5);
        g.fill(x + 10, y + 85, x + 10 + progressWidth, y + 100, 0xFFAA00FF);

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
