package me.boxadactle.coordinatesdisplay.gui.config;

import me.boxadactle.coordinatesdisplay.CoordinatesDisplay;
import me.boxadactle.coordinatesdisplay.gui.widget.InvisibleButtonWidget;
import me.boxadactle.coordinatesdisplay.util.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;

public class ChangePositionScreen extends Screen {

    private final MinecraftClient client = MinecraftClient.getInstance();

    Screen parent;

    Vec3d pos;
    ChunkPos chunkPos;
    float cameraYaw;

    boolean lockHudPos = false;

    int x;
    int y;

    public ChangePositionScreen(Screen parent) {
        super(new TranslatableText("screen.coordinatesdisplay.config.position"));
        this.parent = parent;

        this.pos = new Vec3d(Math.random() * 1000, Math.random() * 5, Math.random() * 1000);
        this.chunkPos = new ChunkPos(new BlockPos(pos));
        this.cameraYaw  = (float) Math.random() * 180;

        x = CoordinatesDisplay.CONFIG.hudX;
        y = CoordinatesDisplay.CONFIG.hudY;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        matrices.push();

        matrices.scale(0.9F, 0.9F, 0.9F);

        drawCenteredText(matrices, this.textRenderer, new TranslatableText("screen.coordinatesdisplay.config.position"), (int) ((this.width / 2) / 0.9), 5, ModUtils.WHITE);
        drawCenteredText(matrices, this.textRenderer, new TranslatableText("description.coordinatesdisplay.changeposition"), (int) ((this.width / 2) / 0.9), 20, ModUtils.GRAY);

        matrices.pop();

        drawCenteredText(matrices, this.textRenderer, new TranslatableText("description.coordinatesdisplay.changeposition2"), this.width / 2, this.height - 15, ModUtils.WHITE);

        if (lockHudPos) {
            x = mouseX;
            y = mouseY;
        }

        CoordinatesDisplay.OVERLAY.render(matrices, pos, chunkPos, cameraYaw, null, x, y);

    }

    @Override
    public void init() {
        super.init();

        this.addDrawableChild(new InvisibleButtonWidget(0, 0, this.width, this.height, (button -> lockHudPos = !lockHudPos)));

    }

    @Override
    public void close() {
        CoordinatesDisplay.CONFIG.hudX = x;
        CoordinatesDisplay.CONFIG.hudY = y;

        this.client.setScreen(parent);
    }
}
