package hw7;

/**
 * Интерфейс собственного списка.
 *
 * @param <E> тип элементов списка
 */
public interface CustomList<E> extends Iterable<E> {

    /**
     * Добавляет элемент в конец списка.
     *
     * @param element добавляемый элемент
     */
    void add(E element);

    /**
     * Возвращает элемент по индексу.
     *
     * @param index индекс элемента
     * @return элемент списка
     */
    E get(int index);

    /**
     * Удаляет элемент по индексу.
     *
     * @param index индекс элемента
     * @return удаленный элемент
     */
    E remove(int index);

    /**
     * Возвращает количество элементов.
     *
     * @return размер списка
     */
    int size();

    /**
     * Проверяет, является ли список пустым.
     *
     * @return true, если список пуст
     */
    boolean isEmpty();
}