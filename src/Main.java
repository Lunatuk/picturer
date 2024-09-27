import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            String filePath = "E:\\Tanki\\res\\packages\\vehicles_level_10_hd-part2.pkg";
            FileProcessor.convertFileToHex(filePath);
            System.out.println("Файл успешно конвертирован в hex-код и сохранён в hexString.txt");
        } catch (IOException e) {
            System.err.println("Произошла ошибка при обработке файла: " + e.getMessage());
            e.printStackTrace();
        }
    }
}