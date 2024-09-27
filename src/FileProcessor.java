import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileProcessor {

    private static final String[] HEX_ARRAY = new String[256];

    static {
        for (int i = 0; i < 256; i++) {
            HEX_ARRAY[i] = String.format("%02X", i);
        }
    }

    public static void convertFileToHex(String filePath) throws IOException {
        File hexString = new File("testOut.txt");
        hexString.createNewFile();

        try (FileOutputStream outputStream = new FileOutputStream(hexString);
             FileInputStream fileInputStream = new FileInputStream(filePath)) {

            byte[] buffer = new byte[10485760]; // буфер для чтения файла (10 MB)
            int bytesRead;

            StringBuilder hexBuffer = new StringBuilder();
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    hexBuffer.append(HEX_ARRAY[buffer[i] & 0xFF]);
                }

                outputStream.write(hexBuffer.toString().getBytes());
                hexBuffer.setLength(0);
            }
        }
    }
}