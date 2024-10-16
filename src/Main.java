import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
//            String filePath = "C:\\Users\\ivana\\OneDrive\\Рабочий стол\\Резюме.docx";
//            String filePath = "C:\\Users\\ivana\\OneDrive\\Рабочий стол\\все что связано с учебой.7z";
//            String filePath = "C:\\Users\\ivana\\OneDrive\\Рабочий стол\\Games.7z";
            String filePath = "C:\\Users\\ivana\\OneDrive\\Рабочий стол\\фотки и видео с стола\\6iFH1fsXxU8.jpg";
            String hexFilePath = "hexString.txt";
            String outputImagePath = "outputImage.png";

            FileProcessor.convertFileToHex(filePath);
            System.out.println("Файл успешно конвертирован в hex-код и сохранён в hexString.txt");

            ImageCreator.createImage(hexFilePath, outputImagePath);
            System.out.println("Изображение успешно создано и сохранено в " + outputImagePath);
        } catch (IOException e) {
            System.err.println("Произошла ошибка при обработке файла: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
