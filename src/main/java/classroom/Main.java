package classroom;

import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Программа для анализа успеваемости учеников.
 * Читает файлы с оценками, вычисляет средние баллы,
 * определяет лучшего и худшего ученика,
 * генерирует отчет и сохраняет его в файл.
 */
public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setProperty("file.encoding", "UTF-8");
        try {
            String path = InputFiles.getInputPath();
            File dir = new File(path);
            File[] studentFiles = InputFiles.getStudentFiles(dir);

            if (studentFiles == null || studentFiles.length == 0) {
                System.out.println("❌ Неверный путь к файлам учеников или папка пуста");
                return;
            }

            Map<String, Double> subjectAvg = new TreeMap<>();
            Map<String, Double> studentAvg = new HashMap<>();
            StudentFiles.processStudentFiles(studentFiles, subjectAvg, studentAvg);

            String report = Report.generateReport(subjectAvg, studentAvg);
            Report.outputResults(report, dir);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}