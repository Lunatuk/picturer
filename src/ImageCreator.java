import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageCreator {

    private static final int BUFFER_SIZE = 1048576; // 1 мб

    public static void createImage(String hexFilePath, String outputImagePath) throws IOException {
        File hexFile = new File(hexFilePath);
        long fileSize = hexFile.length();

        long totalPixels = fileSize / 3;
        int width = (int) Math.ceil(Math.sqrt(totalPixels));
        int height = (int) Math.ceil(totalPixels / (double) width);

//        System.out.println("Размеры изображения: " + width + "x" + height);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();

        try (FileInputStream hexInputStream = new FileInputStream(hexFile)) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            int x = 0;
            int y = 0;

            while ((bytesRead = hexInputStream.read(buffer)) != -1) {
                int bufferIndex = 0;

                while (bufferIndex + 2 < bytesRead) {
                    if (x >= width) {
                        x = 0;
                        y++;
                    }
                    if (y >= height) {
                        break;
                    }

                    int r = buffer[bufferIndex++] & 0xFF;
                    int g = buffer[bufferIndex++] & 0xFF;
                    int b = buffer[bufferIndex++] & 0xFF;

                    raster.setPixel(x, y, new int[]{r, g, b});
                    x++;
                }
            }
        }

        ImageIO.write(image, "png", new File(outputImagePath));
    }
}
