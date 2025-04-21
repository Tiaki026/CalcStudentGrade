package classroom;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

/**
 * Проверка файлов
 */
public class Validation {

    /**
     * Проверяет что имя файла соответствует формату:
     * - Формат "Фамилия Имя Отчество.txt"
     * - Каждое слово минимум 2 символа
     * - Только кириллические символы
     */
    public static boolean isValidStudentFilename(String filename) {
        if (!filename.endsWith(".txt")) {
            return false;
        }

        String namePart = filename.substring(0, filename.length() - 4);
        String[] nameParts = namePart.split(" ");

        if (nameParts.length != 3) {
            return false;
        }

        for (String part : nameParts) {
            if (part.length() < 2) {
                return false;
            }

            if (!part.matches("[А-Яа-яЁё]+")) {
                return false;
            }
        }

        return true;
    }

    /**
     * Извлекает и обрабатывает оценки по предметам каждого ученика.
     */
    public static void processStudentFile(
            BufferedReader reader,
            List<Integer> grades,
            Map<String, Double> subjectAvg
    ) throws Exception {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (!line.contains("-")) {
                throw new IllegalArgumentException(
                        "Некорректный формат строки: '" + line +
                                "'. Должно быть: 'Предмет - Оценка'"
                );
            }

            String[] parts = line.split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Некорректный формат строки: '" + line + "'");
            }

            String subject = parts[0].trim();
            if (subject.isEmpty()) {
                throw new IllegalArgumentException("Отсутствует название предмета");
            }

            String gradeStr = parts[1].trim();
            try {
                int grade = Integer.parseInt(gradeStr);

                if (grade < 3 || grade > 5) {
                    throw new IllegalArgumentException(
                            "Некорректная оценка: " + grade +
                                    ". Должна быть от 3 до 5"
                    );
                }

                grades.add(grade);
                subjectAvg.merge(subject, (double) grade, (old, newVal) -> (old + newVal) / 2);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Оценка должна быть числом: '" + gradeStr + "'");
            }
        }
    }
}