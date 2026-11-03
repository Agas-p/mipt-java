package hw7;

/**
 * Хранит информацию о студенте.
 */
public final class Student {
    private final int id;
    private final String name;
    private final double grade;

    /**
     * Создает нового студента.
     *
     * @param id идентификатор студента
     * @param name имя студента
     * @param grade оценка студента
     */
    public Student(int id, String name, double grade) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (grade < 0 || grade > 10) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    /**
     * Возвращает идентификатор.
     *
     * @return идентификатор студента
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает имя.
     *
     * @return имя студента
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает оценку.
     *
     * @return оценка студента
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Сравнивает студентов.
     *
     * @param object объект для сравнения
     * @return true, если студенты равны
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null) {
            return false;
        }

        if (getClass() != object.getClass()) {
            return false;
        }

        Student student = (Student) object;

        return id == student.id
                && Double.compare(grade, student.grade) == 0
                && name.equals(student.name);
    }

    /**
     * Возвращает хеш-код студента.
     *
     * @return хеш-код
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + Double.hashCode(grade);

        return result;
    }
}