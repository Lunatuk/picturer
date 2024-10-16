import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileProcessor {

    private static final String[] HEX_ARRAY = new String[256];
    private static final int BUFFER_SIZE = 1024 * 1024 * 50; // 50 MB буфер для чтения
    private static final int HEX_BUFFER_LIMIT = 1024 * 1024 * 100; // 100 MB лимит для накопления hex в памяти

    static {
        for (int i = 0; i < 256; i++) {
            HEX_ARRAY[i] = String.format("%02X", i);
        }
    }

    public static void convertFileToHex(String filePath) throws IOException {
        File hexString = new File("hexString.txt");
        hexString.createNewFile();

        File inputFile = new File(filePath);
        long fileSize = inputFile.length();
        long totalBytesRead = 0;

        long startTime = System.nanoTime();

        try (FileOutputStream outputStream = new FileOutputStream(hexString);
             FileInputStream fileInputStream = new FileInputStream(filePath)) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            StringBuilder hexBuffer = new StringBuilder();
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                long currentTime = System.nanoTime();
                totalBytesRead += bytesRead;

                for (int i = 0; i < bytesRead; i++) {
                    hexBuffer.append(HEX_ARRAY[buffer[i] & 0xFF]);
                }

                if (hexBuffer.length() >= HEX_BUFFER_LIMIT) {
                    outputStream.write(hexBuffer.toString().getBytes());
                    hexBuffer.setLength(0);
                }

                double elapsedTimeInSeconds = (currentTime - startTime) / 1_000_000_000.0;
                double processingSpeed = totalBytesRead / elapsedTimeInSeconds;
                long remainingBytes = fileSize - totalBytesRead;
                double estimatedTimeLeft = remainingBytes / processingSpeed;

                System.out.printf("\rПрогресс: %.2f%%, оставшееся время: %.2f сек",
                        (totalBytesRead / (double) fileSize) * 100,
                        estimatedTimeLeft);
            }

            if (!hexBuffer.isEmpty()) {
                outputStream.write(hexBuffer.toString().getBytes());
            }
        }

        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1_000_000;

        if (duration < 1000) {
            System.out.println("\nПолное время выполнения конвертации: " + duration + " мс");
        } else { System.out.println("\nПолное время выполнения конвертации: " + duration / 1000 + " секунд"); }
    }
}
