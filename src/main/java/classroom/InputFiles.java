package classroom;

import java.io.File;
import java.util.Scanner;

/**
 * Ввод данных
 */
public class InputFiles {
    /**
     * Запрашивает у пользователя путь к папке с файлами.
     */
    public static String getInputPath() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к папке ➡️:");
        return scanner.nextLine();
    }

    /**
     * Получает список файлов из указанной директории.
     * Игнорирует файл "Отчет.txt".
     */
    public static File[] getStudentFiles(File dir) {
        return dir.listFiles((d, name) -> name.endsWith(".txt") && !name.equals("Отчет.txt"));
    }
}