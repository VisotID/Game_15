package com.inessa.game_in_15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Модель игры "Пятнашки", содержащая игровую логику.
 */
public class GameModel {

    /** Количество строк в игровой сетке (4). */
    private final int ROWS = 4;

    /** Количество столбцов в игровой сетке (4). */
    private final int COLUMNS = 4;

    /** Список, хранящий текущий порядок фрагментов. */
    private List<Integer> orderList;

    /** Счетчик сделанных ходов. */
    private int moves;

    /**
     * Конструктор, инициализирующий начальное состояние.
     */
    public GameModel() {
        this.moves = 0;
        this.orderList = new ArrayList<>();
        for (int i = 0; i < ROWS * COLUMNS; i++) {
            orderList.add(i);
        }
    }

    /**
     * Возвращает текущий порядок фрагментов.
     * @return Список порядка фрагментов
     */
    public List<Integer> getOrderList() {
        return Collections.unmodifiableList(orderList);
    }

    /**
     * Возвращает количество ходов.
     * @return Количество ходов
     */
    public int getMoves() {
        return moves;
    }

    /**
     * Перемешивает фрагменты, обеспечивая решаемую комбинацию.
     */
    public void shuffle() {
        do {
            orderList = new ArrayList<>(); // Список номеров
            for (int i = 0; i < ROWS * COLUMNS; i++) { // Перебор индексов
                orderList.add(i); // Добавление номера фрагмента
            }
            Collections.shuffle(orderList); // Перемешивает список
        } while (evenChecker(orderList)); // Проверка решаемости
        moves = 0;
    }

    /**
     * Проверяет, является ли текущая комбинация фрагментов нерешаемой.
     * @param list Список порядка фрагментов
     * @return true, если комбинация нерешаема, иначе false
     */
    private boolean evenChecker(List<Integer> list) {
        int sum = 0; // Счетчик
        for (int i = 0; i < list.size(); i++) { // Проходим по листу
            if (list.get(i) == 15) continue; // если ячейка последняя, завершаем текущую итерацию данного цикла
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j) == 15) continue; // если ячейка последняя, завершаем текущую итерацию данного цикла
                if (list.get(i) > list.get(j)) { // если предыдущий элемент больше следующего
                    sum++; // Увеличиваем счетчик
                }
            }
            if (list.get(i) == 15) { // если ячейка последняя
                sum += 1 + i / COLUMNS; // Добавляет позицию пустой клетки
            }
        }
        return sum % 2 == 1; // Возвращает результат проверки
    }

    /**
     * Проверяет, достигнута ли победа (все фрагменты на своих местах).
     * @return true, если победа достигнута, иначе false
     */
    public boolean isVictory() {
        for (int i = 0; i < orderList.size() - 1; i++) { // Перебирает порядок
            if (orderList.get(i + 1) < orderList.get(i)) { // Проверяет нарушение порядка
                return false; // Возвращает false при нарушении
            }
        }
        return true; // Возвращает true при победе
    }

    /**
     * Перемещает фрагмент, если это возможно.
     * @param row Ряд кликнутой клетки
     * @param column Столбец кликнутой клетки
     * @param hiddenRow Ряд пустой клетки
     * @param hiddenColumn Столбец пустой клетки
     * @return true, если перемещение выполнено, иначе false
     */
    public boolean moveTile(int row, int column, int hiddenRow, int hiddenColumn) {
        if (row == hiddenRow && (column - 1 == hiddenColumn || column + 1 == hiddenColumn) || // Проверяем наличие пустой ячейки рядом с ячейкой на которую кликнули мышкой
                (column == hiddenColumn && (row - 1 == hiddenRow || row + 1 == hiddenRow))) {
            Collections.swap(orderList, hiddenColumn * ROWS + hiddenRow, column * ROWS + row); // Меняем местами кликнутую ячейку и пустую
            moves++; // Прибавляем шаг
            return true;
        }
        return false;
    }
}