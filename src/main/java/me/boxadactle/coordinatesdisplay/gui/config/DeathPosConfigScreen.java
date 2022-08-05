package me.boxadactle.coordinatesdisplay.gui.config;

import me.boxadactle.coordinatesdisplay.CoordinatesDisplay;
import me.boxadactle.coordinatesdisplay.gui.widget.InvisibleButtonWidget;
import me.boxadactle.coordinatesdisplay.util.ModUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.text.DecimalFormat;

public class DeathPosConfigScreen extends Screen {
    int p = 2;
    int p1 = p / 2;
    int th = 10;
    int tp = 4;

    int largeButtonW = 300;
    int smallButtonW = 150 - p;
    int tinyButtonW = 75;
    int buttonHeight = 20;

    int start = 20;

    Screen parent;

    String deathx;
    String deathy;
    String deathz;

    public DeathPosConfigScreen(Screen parent) {
        super(new TranslatableText("screen.coordinatesdisplay.config.deathpos", CoordinatesDisplay.MOD_NAME, CoordinatesDisplay.MOD_VERSION));
        this.parent = parent;

        DecimalFormat d = new DecimalFormat("0.00");

        deathx = d.format(Math.random() * 1000);
        deathy = d.format(Math.random() * 100);
        deathz = d.format(Math.random() * 1000);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        drawCenteredText(matrices, this.textRenderer, new TranslatableText("screen.coordinatesdisplay.config.deathpos", CoordinatesDisplay.MOD_NAME, CoordinatesDisplay.MOD_VERSION), this.width / 2, 5, ModUtils.WHITE);

        String pos = CoordinatesDisplay.DeathposColorPrefix + new TranslatableText("message.coordinatesdisplay.location", deathx, deathy, deathz).getString();
        Text deathPos = new TranslatableText("message.coordinatesdisplay.deathpos", pos);
        drawCenteredText(matrices, this.textRenderer, deathPos, this.width / 2, (int) (this.width / 1.5), ModUtils.WHITE);

        super.render(matrices, mouseX,  mouseY, delta);
    }

    protected void init() {
        super.init();

        this.addDrawableChild(new ButtonWidget(this.width / 2 - largeButtonW / 2, this.height - buttonHeight - p, largeButtonW, buttonHeight, new TranslatableText("button.coordinatesdisplay.back"), (button) -> this.client.setScreen(parent)));

        initButtons();
    }

    private void initButtons() {
        // show death pos in chat
        this.addDrawableChild(new ButtonWidget(this.width / 2 - largeButtonW / 2, start, largeButtonW, buttonHeight, new TranslatableText("button.coordinatesdisplay.deathpos.deathscreen", (CoordinatesDisplay.CONFIG.displayPosOnDeathScreen ? ModUtils.TRUE : ModUtils.FALSE)), (button) -> {
            CoordinatesDisplay.CONFIG.displayPosOnDeathScreen = !CoordinatesDisplay.CONFIG.displayPosOnDeathScreen;
            button.setMessage(new TranslatableText("button.coordinatesdisplay.deathpos.deathscreen", (CoordinatesDisplay.CONFIG.displayPosOnDeathScreen ? ModUtils.TRUE : ModUtils.FALSE)));
        }, (button, matrices, mouseX, mouseY) -> {
            if (button.isHovered()) {
                this.renderTooltip(matrices, new TranslatableText("description.coordinatesdisplay.deathpos.deathscreen"), mouseX, mouseY);
            }
        }));

        this.addDrawableChild(new InvisibleButtonWidget(0, 0, 10, 10, (button -> CoordinatesDisplay.LOGGER.info("hi bitch"))));

        // chunk data
        this.addDrawableChild(new ButtonWidget(this.width / 2 - largeButtonW / 2, start + (buttonHeight + p), largeButtonW, buttonHeight, new TranslatableText("button.coordinatesdisplay.deathpos.chat", (CoordinatesDisplay.CONFIG.showDeathPosInChat ? ModUtils.TRUE : ModUtils.FALSE)), (button) -> {
            CoordinatesDisplay.CONFIG.showDeathPosInChat = !CoordinatesDisplay.CONFIG.showDeathPosInChat;
            button.setMessage(new TranslatableText("button.coordinatesdisplay.deathpos.chat", (CoordinatesDisplay.CONFIG.showDeathPosInChat ? ModUtils.TRUE : ModUtils.FALSE)));
        }, (button, matrices, mouseX, mouseY) -> {
            if (button.isHovered()) {
                this.renderTooltip(matrices, new TranslatableText("description.coordinatesdisplay.deathpos.chat"), mouseX, mouseY);
            }
        }));
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
