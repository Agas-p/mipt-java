package hw7;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для CustomArrayList.
 */
public class CustomArrayListTest {

    @Test
    public void emptyListTest() {
        CustomArrayList<Integer> list =
                new CustomArrayList<>();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void addTest() {
        CustomArrayList<Integer> list =
                new CustomArrayList<>();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    public void increaseTest() {
        CustomArrayList<Integer> list =
                new CustomArrayList<>();

        for (int i = 0; i < 15; i++) {
            list.add(i);
        }

        assertEquals(15, list.size());

        for (int i = 0; i < 15; i++) {
            assertEquals(i, list.get(i));
        }
    }

    @Test
    public void getTest() {
        CustomArrayList<Integer> list =
                new CustomArrayList<>();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    public void removeFirstTest() {
        CustomArrayList<Integer> list =
                new CustomArrayList<>();

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(0);

        assertEquals(10, removed);
        assertEquals(20, list.get(0));
        assertEquals(2, list.size());
    }

    @Test
    public void removeMiddleTest() {
        CustomArrayList<Integer> list =
                new CustomArrayList<>();

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(1);

        assertEquals(20, removed);
        assertEquals(30, list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    public void removeLastTest() {
        CustomArrayList<Integer> list =
                new CustomArrayList<>();

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(2);

        assertEquals(30, removed);
        assertEquals(2, list.size());
    }

    @Test
    public void nullTest() {
        final CustomArrayList<Integer> list =
                new CustomArrayList<>();

        Executable executable = new Executable() {
            @Override
            public void execute() {
                list.add(null);
            }
        };

        assertThrows(
                NullPointerException.class,
                executable
        );
    }

    @Test
    public void wrongGetIndexTest() {
        final CustomArrayList<Integer> list =
                new CustomArrayList<>();

        list.add(10);

        Executable negativeIndex = new Executable() {
            @Override
            public void execute() {
                list.get(-1);
            }
        };

        Executable sizeIndex = new Executable() {
            @Override
            public void execute() {
                list.get(list.size());
            }
        };

        assertThrows(
                IndexOutOfBoundsException.class,
                negativeIndex
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                sizeIndex
        );
    }

    @Test
    public void wrongRemoveIndexTest() {
        final CustomArrayList<Integer> list =
                new CustomArrayList<>();

        list.add(10);

        Executable negativeIndex = new Executable() {
            @Override
            public void execute() {
                list.remove(-1);
            }
        };

        Executable sizeIndex = new Executable() {
            @Override
            public void execute() {
                list.remove(list.size());
            }
        };

        assertThrows(
                IndexOutOfBoundsException.class,
                negativeIndex
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                sizeIndex
        );
    }

    @Test
    public void iteratorTest() {
        CustomArrayList<Integer> list =
                new CustomArrayList<>();

        list.add(10);
        list.add(20);

        final Iterator<Integer> iterator =
                list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next());
        assertEquals(20, iterator.next());
        assertFalse(iterator.hasNext());

        Executable executable = new Executable() {
            @Override
            public void execute() {
                iterator.next();
            }
        };

        assertThrows(
                NoSuchElementException.class,
                executable
        );
    }
}