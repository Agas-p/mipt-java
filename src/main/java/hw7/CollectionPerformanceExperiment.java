package hw7;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Сравнивает скорость работы ArrayList и LinkedList.
 */
public class CollectionPerformanceExperiment {
    private static final int COUNT = 10000;
    private static final int WARMUP_COUNT = 2;
    private static final int TEST_COUNT = 5;
    private static long checksum = 0;

    /**
     * Запускает эксперимент.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        System.out.println(
                "Operation\tN\tArrayList\tLinkedList"
        );

        runTest("Add end", 1);
        runTest("Add start", 2);
        runTest("Add middle", 3);
        runTest("Get by index", 4);
        runTest("Remove start", 5);
        runTest("Remove end", 6);

        System.out.println();
        System.out.println("Checksum: " + checksum);

        System.out.println();
        System.out.println("Conclusion:");
        System.out.println(
                "ArrayList stores elements in an array."
        );
        System.out.println(
                "Access by index in ArrayList is fast."
        );
        System.out.println(
                "Adding to the beginning of ArrayList requires shifting elements."
        );
        System.out.println(
                "LinkedList stores elements in connected nodes."
        );
        System.out.println(
                "Access by index in LinkedList requires moving through nodes."
        );
        System.out.println(
                "Removing from the beginning of LinkedList does not require shifting elements."
        );
        System.out.println(
                "Results can be different on different computers."
        );
    }

    private static void runTest(
            String name,
            int operation
    ) {
        for (int i = 0; i < WARMUP_COUNT; i++) {
            measure(false, operation);
            measure(true, operation);
        }

        long arrayTime = 0;
        long linkedTime = 0;

        for (int i = 0; i < TEST_COUNT; i++) {
            arrayTime += measure(false, operation);
            linkedTime += measure(true, operation);
        }

        arrayTime = arrayTime / TEST_COUNT;
        linkedTime = linkedTime / TEST_COUNT;

        System.out.println(
                name + "\t"
                        + COUNT + "\t"
                        + arrayTime + "\t"
                        + linkedTime
        );
    }

    private static long measure(
            boolean linked,
            int operation
    ) {
        List<Integer> list;

        if (linked) {
            list = new LinkedList<>();
        } else {
            list = new ArrayList<>();
        }

        if (operation >= 4) {
            for (int i = 0; i < COUNT; i++) {
                list.add(i);
            }
        }

        long start = System.nanoTime();

        if (operation == 1) {
            for (int i = 0; i < COUNT; i++) {
                list.add(i);
            }
        }

        if (operation == 2) {
            for (int i = 0; i < COUNT; i++) {
                list.add(0, i);
            }
        }

        if (operation == 3) {
            for (int i = 0; i < COUNT; i++) {
                list.add(list.size() / 2, i);
            }
        }

        if (operation == 4) {
            for (int i = 0; i < COUNT; i++) {
                checksum += list.get(i);
            }
        }

        if (operation == 5) {
            while (!list.isEmpty()) {
                checksum += list.remove(0);
            }
        }

        if (operation == 6) {
            while (!list.isEmpty()) {
                checksum += list.remove(
                        list.size() - 1
                );
            }
        }

        long end = System.nanoTime();

        checksum += list.size();

        return end - start;
    }
}