package hw7;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализация динамического массива.
 *
 * @param <E> тип элементов списка
 */
public class CustomArrayList<E> implements CustomList<E> {
    private Object[] array;
    private int size;

    /**
     * Создает пустой список с начальной емкостью 10.
     */
    public CustomArrayList() {
        array = new Object[10];
        size = 0;
    }

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element добавляемый элемент
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        if (size == array.length) {
            increaseArray();
        }

        array[size] = element;
        size++;
    }

    /**
     * Возвращает элемент по индексу.
     *
     * @param index индекс элемента
     * @return элемент списка
     */
    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) array[index];
    }

    /**
     * Удаляет элемент по индексу.
     *
     * @param index индекс элемента
     * @return удаленный элемент
     */
    @Override
    public E remove(int index) {
        checkIndex(index);

        E removed = (E) array[index];

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }

        size--;
        array[size] = null;

        return removed;
    }

    /**
     * Возвращает количество элементов.
     *
     * @return размер списка
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверяет, является ли список пустым.
     *
     * @return true, если список пуст
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Возвращает итератор для обхода элементов.
     *
     * @return итератор
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                E element = (E) array[index];
                index++;

                return element;
            }
        };
    }

    private void increaseArray() {
        int newLength = array.length + array.length / 2;
        Object[] newArray = new Object[newLength];

        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }

        array = newArray;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
}