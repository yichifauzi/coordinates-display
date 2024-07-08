package dev.boxadactle.coordinatesdisplay.hud.modifier;

import dev.boxadactle.boxlib.math.geometry.Dimension;
import dev.boxadactle.boxlib.math.geometry.Rect;
import dev.boxadactle.boxlib.math.geometry.Vec2;
import dev.boxadactle.coordinatesdisplay.config.StartCorner;
import dev.boxadactle.coordinatesdisplay.hud.HudPositionModifier;

public class TopLeftModifier implements HudPositionModifier {
    @Override
    public Vec2<Integer> translateVector(Vec2<Integer> original, Dimension<Integer> window, StartCorner currentCorner) {
        int translatedX = original.getX();
        int translatedY = original.getY();

        int x = original.getX();
        int y = original.getY();

        int windowWidth = window.getWidth();
        int windowHeight = window.getHeight();

        switch (currentCorner) {
            case TOP_RIGHT:
                translatedX = windowWidth - x;
                break;
            case BOTTOM_LEFT:
                translatedY = windowHeight - y;
                break;
            case BOTTOM_RIGHT:
                translatedX = windowWidth - x;
                translatedY = windowHeight - y;
                break;
            // For TOP_LEFT corner, no translation needed
            default:
                break;
        }

        return new Vec2<>(translatedX, translatedY);
    }

    @Override
    public Rect<Integer> translateRect(Rect<Integer> rect, Dimension<Integer> window, StartCorner currentCorner) {
        int translatedX = rect.getX();
        int translatedY = rect.getY();

        switch (currentCorner) {
            case TOP_RIGHT:
                translatedX = window.getWidth() - rect.getX() - rect.getWidth();
                break;
            case BOTTOM_LEFT:
                translatedY = window.getHeight() - rect.getY() - rect.getHeight();
                break;
            case BOTTOM_RIGHT:
                translatedX = window.getWidth() - rect.getX() - rect.getWidth();
                translatedY = window.getHeight() - rect.getY() - rect.getHeight();
                break;
            default:
                break;
        }

        Rect<Integer> r = rect.clone();
        r.setX(translatedX);
        r.setY(translatedY);
        return r;
    }
}
