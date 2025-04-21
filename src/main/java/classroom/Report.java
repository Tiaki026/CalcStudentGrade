package classroom;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Создание отчета
 */
public class Report {

    /**
     * Вычисляет среднее значение списка оценок.
     */
    public static double calculateAverage(List<Integer> grades) {
        return grades.stream().mapToInt(i -> i).average().orElse(0);
    }

    /**
     * Генерирует текстовый отчет на основе средних оценок.
     */
    public static String generateReport(
            Map<String, Double> subjectAvg,
            Map<String, Double> studentAvg) {
        StringBuilder report = new StringBuilder();

        appendSubjectAverages(report, subjectAvg);

        if (!studentAvg.isEmpty()) {
            appendBestAndWorstStudents(report, studentAvg);
            report.append("\nВсего учеников: ").append(studentAvg.size());
        }

        return report.toString();
    }

    /**
     * Добавляет в отчет раздел со средними оценками по предметам.
     */
    private static void appendSubjectAverages(
            StringBuilder report,
            Map<String, Double> subjectAvg) {
        report.append("Средние баллы по предметам:\n");
        subjectAvg.forEach((subject, avg) ->
                report.append(String.format("%s - %.2f%n", subject, avg))
        );
    }

    /**
     * Добавляет в отчет информацию о лучшем и худшем ученике.
     */
    private static void appendBestAndWorstStudents(
            StringBuilder report,
            Map<String, Double> studentAvg) {
        Map.Entry<String, Double> best = Collections.max(studentAvg.entrySet(),
                Map.Entry.comparingByValue());
        Map.Entry<String, Double> worst = Collections.min(studentAvg.entrySet(),
                Map.Entry.comparingByValue());

        report.append("\nЛучший ученик:\n")
                .append(String.format("%s (средний балл - %.2f)%n", best.getKey(), best.getValue()))
                .append("\nХудший ученик:\n")
                .append(String.format("%s (средний балл - %.2f)%n", worst.getKey(), worst.getValue()));
    }

    /**
     * Выводит отчет на консоль и сохраняет в файл.
     */
    public static void outputResults(String report, File dir) throws Exception {
        System.out.println("\n⬇️ Результат ⬇️\n");
        System.out.println(report);

        saveReportToFile(report, dir);
        System.out.println("\n✅ Отчет сохранен в файл 'Отчет.txt'");
    }

    /**
     * Сохраняет файл "Отчет.txt" в директории.
     */
    private static void saveReportToFile(String report, File dir) throws Exception {
        try (PrintWriter writer = new PrintWriter(new File(dir, "Отчет.txt"), StandardCharsets.UTF_8)) {
            writer.print(report);
        }
    }
}