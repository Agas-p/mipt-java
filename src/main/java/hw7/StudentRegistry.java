package hw7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Содержит методы для работы с реестром студентов.
 */
public class StudentRegistry {

    /**
     * Создает HashMap со студентами.
     *
     * @param students массив студентов
     * @return карта студентов
     */
    public static HashMap<Integer, Student> createHashMap(
            Student[] students
    ) {
        HashMap<Integer, Student> map = new HashMap<>();

        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            map.put(student.getId(), student);
        }

        return map;
    }

    /**
     * Создает TreeMap с сортировкой идентификаторов по убыванию.
     *
     * @param students массив студентов
     * @return карта студентов
     */
    public static TreeMap<Integer, Student> createTreeMap(
            Student[] students
    ) {
        TreeMap<Integer, Student> map =
                new TreeMap<>(Collections.reverseOrder());

        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            map.put(student.getId(), student);
        }

        return map;
    }

    /**
     * Ищет студентов с оценкой в заданном диапазоне.
     *
     * @param students карта студентов
     * @param minGrade минимальная оценка
     * @param maxGrade максимальная оценка
     * @return список найденных студентов
     */
    public static List<Student> findStudentsByGradeRange(
            Map<Integer, Student> students,
            double minGrade,
            double maxGrade
    ) {
        if (minGrade < 0
                || maxGrade > 10
                || minGrade > maxGrade) {
            throw new IllegalArgumentException();
        }

        ArrayList<Student> result = new ArrayList<>();

        for (Student student : students.values()) {
            if (student.getGrade() >= minGrade
                    && student.getGrade() <= maxGrade) {
                result.add(student);
            }
        }

        return result;
    }

    /**
     * Возвращает студентов с наибольшими идентификаторами.
     *
     * @param students карта студентов
     * @param n количество студентов
     * @return список студентов
     */
    public static List<Student> getTopNStudentsById(
            NavigableMap<Integer, Student> students,
            int n
    ) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }

        ArrayList<Student> result = new ArrayList<>();

        if (n == 0) {
            return result;
        }

        for (Student student : students.values()) {
            if (result.size() == n) {
                break;
            }

            result.add(student);
        }

        return result;
    }
}