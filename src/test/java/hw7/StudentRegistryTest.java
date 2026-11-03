package hw7;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для StudentRegistry.
 */
public class StudentRegistryTest {

    @Test
    public void gradeRangeTest() {
        Student[] students = {
                new Student(1, "Alex", 5.0),
                new Student(2, "Bob", 7.0),
                new Student(3, "John", 9.0)
        };

        HashMap<Integer, Student> map =
                StudentRegistry.createHashMap(students);

        List<Student> result =
                StudentRegistry.findStudentsByGradeRange(
                        map,
                        5.0,
                        7.0
                );

        assertEquals(2, result.size());
        assertTrue(result.contains(students[0]));
        assertTrue(result.contains(students[1]));
    }

    @Test
    public void emptyGradeRangeTest() {
        HashMap<Integer, Student> map =
                new HashMap<>();

        List<Student> result =
                StudentRegistry.findStudentsByGradeRange(
                        map,
                        0,
                        10
                );

        assertTrue(result.isEmpty());
    }

    @Test
    public void wrongRangeTest() {
        final HashMap<Integer, Student> map =
                new HashMap<>();

        Executable executable = new Executable() {
            @Override
            public void execute() {
                StudentRegistry.findStudentsByGradeRange(
                        map,
                        8,
                        5
                );
            }
        };

        assertThrows(
                IllegalArgumentException.class,
                executable
        );
    }

    @Test
    public void gradeLessThanZeroTest() {
        final HashMap<Integer, Student> map =
                new HashMap<>();

        Executable executable = new Executable() {
            @Override
            public void execute() {
                StudentRegistry.findStudentsByGradeRange(
                        map,
                        -1,
                        5
                );
            }
        };

        assertThrows(
                IllegalArgumentException.class,
                executable
        );
    }

    @Test
    public void gradeMoreThanTenTest() {
        final HashMap<Integer, Student> map =
                new HashMap<>();

        Executable executable = new Executable() {
            @Override
            public void execute() {
                StudentRegistry.findStudentsByGradeRange(
                        map,
                        5,
                        11
                );
            }
        };

        assertThrows(
                IllegalArgumentException.class,
                executable
        );
    }

    @Test
    public void topStudentsTest() {
        Student[] students = {
                new Student(1, "Alex", 5.0),
                new Student(5, "Bob", 7.0),
                new Student(3, "John", 9.0)
        };

        TreeMap<Integer, Student> map =
                StudentRegistry.createTreeMap(students);

        List<Student> result =
                StudentRegistry.getTopNStudentsById(
                        map,
                        2
                );

        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getId());
        assertEquals(3, result.get(1).getId());
    }

    @Test
    public void zeroStudentsTest() {
        Student[] students = {};

        TreeMap<Integer, Student> map =
                StudentRegistry.createTreeMap(students);

        List<Student> result =
                StudentRegistry.getTopNStudentsById(
                        map,
                        0
                );

        assertTrue(result.isEmpty());
    }

    @Test
    public void nMoreThanSizeTest() {
        Student[] students = {
                new Student(1, "Alex", 5.0),
                new Student(5, "Bob", 7.0),
                new Student(3, "John", 9.0)
        };

        TreeMap<Integer, Student> map =
                StudentRegistry.createTreeMap(students);

        List<Student> result =
                StudentRegistry.getTopNStudentsById(
                        map,
                        10
                );

        assertEquals(3, result.size());
        assertEquals(5, result.get(0).getId());
        assertEquals(3, result.get(1).getId());
        assertEquals(1, result.get(2).getId());
    }

    @Test
    public void negativeNTest() {
        Student[] students = {};

        final TreeMap<Integer, Student> map =
                StudentRegistry.createTreeMap(students);

        Executable executable = new Executable() {
            @Override
            public void execute() {
                StudentRegistry.getTopNStudentsById(
                        map,
                        -1
                );
            }
        };

        assertThrows(
                IllegalArgumentException.class,
                executable
        );
    }
}