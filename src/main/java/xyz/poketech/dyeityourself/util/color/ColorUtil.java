package xyz.poketech.dyeityourself.util.color;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector4f;

public final class ColorUtil {

    public static @NotNull
    Vector3f toFloat(int rgb) {
        int r = rgb >> 16 & 255;
        int g = rgb >> 8 & 255;
        int b = rgb & 255;
        return new Vector3f(r / 255F, g / 255F, b / 255F);
    }

    public static @NotNull Vector4f toFloat4(int rgb) {
        int r = rgb >> 16 & 255;
        int g = rgb >> 8 & 255;
        int b = rgb & 255;
        return new Vector4f(r / 255F, g / 255F, b / 255F, 1);
    }

    public static int[] toRGB(int rgb) {
        int r = rgb >> 16 & 255;
        int g = rgb >> 8 & 255;
        int b = rgb & 255;

        return new int[]{r,g,b};
    }

    public static int getRGB(@NotNull Vector3f rgb) {
        return getRGB(rgb.x(), rgb.y(), rgb.z());
    }

    public static int getRGB(float r, float g, float b) {
        return getRGB((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    public static int getRGBA(@NotNull Vector4f col) {
        return getRGBA(col.x(), col.y(), col.z(), col.w());
    }

    public static int getRGBA(float r, float g, float b, float a) {
        return getRGBA((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
    }

    public static int getARGB(float r, float g, float b, float a) {
        return getARGB((int) (a * 255), (int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    public static int getRGB(int r, int g, int b) {
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    public static int getARGB(int r, int g, int b, int a) {
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    public static int getRGBA(int r, int g, int b, int a) {
        return (r & 0xFF) << 24 | (g & 0xFF) << 16 | (b & 0xFF) << 8 | (a & 0xFF);
    }

    public static int toHex(int r, int g, int b) {
        int hex = 0;
        hex = hex | ((r) << 16);
        hex = hex | ((g) << 8);
        hex = hex | ((b));
        return hex;
    }

    private ColorUtil() {
    }
}
