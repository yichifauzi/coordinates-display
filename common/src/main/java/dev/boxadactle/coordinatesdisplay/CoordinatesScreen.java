package dev.boxadactle.coordinatesdisplay;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.boxadactle.boxlib.math.geometry.Vec3;
import dev.boxadactle.boxlib.util.*;
import dev.boxadactle.coordinatesdisplay.position.Position;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class CoordinatesScreen extends Screen {

    Position pos;

    int buttonw = 300;
    int buttonh = 20;

    int p = 5;

    public CoordinatesScreen(Position pos) {
        super(new TextComponent("Coordinates Screen"));

        this.pos = pos;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float delta) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, delta);

        Vec3<Double> player = pos.position.getPlayerPos();

        int x = (int)Math.round(player.getX());
        int z = (int)Math.round(player.getZ());
        int y = (int)Math.round(player.getY());

        RenderUtils.drawTextCentered(stack, new TranslatableComponent("message.coordinatesdisplay.at"), this.width / 2, (this.height / 4) - 20, GuiUtils.WHITE);
        RenderUtils.drawTextCentered(stack, new TranslatableComponent("message.coordinatesdisplay.location", x, y, z), this.width / 2, (this.height / 4), GuiUtils.WHITE);
    }

    @Override
    public void init() {
        super.init();

        int bstart = this.height / 2 - 20;

        addRenderableWidget(new Button(this.width / 2 - buttonw / 2, bstart, buttonw, buttonh, new TranslatableComponent("button.coordinatesdisplay.copy"), button -> {
            ClientUtils.getClient().keyboardHandler.setClipboard(ModUtil.parseText(CoordinatesDisplay.CONFIG.get().copyPosMessage, this.pos));
            CoordinatesDisplay.LOGGER.player.info("Copied coordinates to clipboard");
            onClose();
        }));

        addRenderableWidget(new Button(this.width / 2 - buttonw / 2, bstart + (buttonh + p), buttonw, buttonh, new TranslatableComponent("button.coordinatesdisplay.send"), button -> {
            CoordinatesDisplay.LOGGER.player.publicChat(ModUtil.parseText(CoordinatesDisplay.CONFIG.get().posChatMessage, this.pos));
            onClose();
        }));

        addRenderableWidget(new Button(this.width / 2 - buttonw / 2, bstart + (buttonh + p) * 2, buttonw, buttonh, new TranslatableComponent("button.coordinatesdisplay.copytp"), button -> {
            ClientUtils.getClient().keyboardHandler.setClipboard(CoordinatesDisplay.getConfig().teleportMode.toCommand(Position.of(WorldUtils.getPlayer())));
            CoordinatesDisplay.LOGGER.player.info("Copied as TP command");
            onClose();
        }));
    }

    @Override
    public void onClose() {
        ClientUtils.setScreen(null);
        MouseUtils.getMouse().grabMouse();
    }
}