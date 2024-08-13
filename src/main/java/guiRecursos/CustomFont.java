
package guiRecursos;

/* 🚀 Developed by NelsonJGP */
import java.awt.Font;
import java.io.InputStream;

public class CustomFont {
    public static Font loadMinecraftFont() {
        return loadFont("/fonts/Minecraft.ttf", 12f);
    }
    
    public static Font loadCustomAnotherFont(float tamaño) {
        return loadFont("/fonts/Inter-Medium.ttf", tamaño);
    }
    
    public static Font loadAnotherFont() {
        return loadFont("/fonts/Inter-Medium.ttf", 12f);
    }

    private static Font loadFont(String fontPath, float size) {
        try {
            // Cargar la fuente personalizada desde el archivo TTF
            InputStream fontStream = CustomFont.class.getResourceAsStream(fontPath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            customFont = customFont.deriveFont(size); // Tamaño de la fuente
            return customFont;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}