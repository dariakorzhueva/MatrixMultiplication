import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Очередь задач
 */
class Buffer {
    MultiplyInterfece mi;
    Queue<MultiplyInterfece> clQueue = new ConcurrentLinkedQueue<MultiplyInterfece>();

    /**
     * Инициализация
     */
    public Buffer() {
    }

    /**
     * Взятие элемента из очереди
     */
    public MultiplyInterfece get() {
        return clQueue.poll();
    }

    /**
     * Добавление элемент в вочередь
     *
     * @param mi функциональный интерфейс с получением результирующей строки
     */
    public void put(MultiplyInterfece mi) {
        this.mi = mi;
        clQueue.add(mi);
    }

    /**
     * Проверка очереди на пустоту
     */
    public boolean isEmpty(){
        if(clQueue.isEmpty())
            return true;
        else
            return false;
    }

    /**
     * Получение размера очереди
     */
    public int getSize(){
        return clQueue.size();
    }
}