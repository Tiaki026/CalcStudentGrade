package classroom;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Работа с файлами учеников
 */
public class StudentFiles {

    /**
     * Обрабатывает файлы заполняя средний балл.
     */
    public static void processStudentFiles(
            File[] files,
            Map<String, Double> subjectAvg,
            Map<String, Double> studentAvg) {
        for (File file : files) {
            String filename = file.getName();

            if (!Validation.isValidStudentFilename(filename)) {
                System.out.println(
                        "⚠️ Некорректное имя файла: " + filename +
                                " - должно быть в формате 'Фамилия Имя Отчество.txt' и каждое слово минимум 2 буквы"
                );
                continue;
            }

            String studentName = filename.replace(".txt", "");
            List<Integer> grades = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                Validation.processStudentFile(reader, grades, subjectAvg);

                if (grades.isEmpty()) {
                    System.out.println("⚠️ Файл не содержит оценок: " + filename);
                    continue;
                }

                double avg = Report.calculateAverage(grades);
                studentAvg.put(studentName, avg);
            } catch (Exception e) {
                System.out.println("❌ Ошибка в файле " + filename + ": " + e.getMessage());
            }
        }
    }
}