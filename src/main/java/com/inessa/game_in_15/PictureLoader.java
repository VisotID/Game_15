// Автор: Высоцкая И.Д.
package com.inessa.game_in_15;

import javafx.scene.image.Image; // Импортирует класс для изображений
import javafx.scene.image.WritableImage; // Импортирует класс для изменяемых изображений
import java.io.File; // Импортирует класс для файлов
import java.io.FileInputStream; // Импортирует класс для чтения файлов
import java.io.IOException; // Импортирует исключение ввода-вывода
import java.nio.file.Files; // Импортирует класс для файловой системы
import java.nio.file.StandardCopyOption; // Импортирует опции копирования
import java.util.ArrayList; // Импортирует класс для списков
import java.util.Arrays; // Импортирует класс для массивов
import java.util.Collections; // Импортирует класс для коллекций
import java.util.List; // Импортирует интерфейс для списков

/**
 * Класс для загрузки и обработки изображений для игры "Пятнашки".
 */
public class PictureLoader {

    /**
     * Список поддерживаемых расширений файлов изображений.
     */
    static final List<String> IMAGE_FILES_EXTENSIONS = Collections.unmodifiableList(Arrays.asList("*.jpg", "*.png", "*.jpeg")); // Задает расширения файлов

    /**
     * Путь к директории для хранения изображений.
     */
    private static final String RES_DIRECTORY_PATH = "img";

    /**
     * Имя файла для полного изображения.
     */
    private static final String FULL_IMAGE_NAME = "full.jpg";

    /**
     * Объект файла для полного изображения.
     */
    private File imageFile = new File(RES_DIRECTORY_PATH + File.separator + FULL_IMAGE_NAME);

    /**
     * Количество строк для разрезки изображения.
     */
    private int rows;

    /**
     * Количество столбцов для разрезки изображения.
     */
    private int columns;

    /**
     * Полное изображение.
     */
    private Image image = null;

    /**
     * Список фрагментов изображения.
     */
    private List<Image> images = null;

    /**
     * Удаляет старые ресурсы и создает новые
     */
    private void deleteOldResources() {
        File resDirectory = new File(RES_DIRECTORY_PATH); // Создает объект директории
        if (resDirectory.exists()) { // Проверяет существование директории
            resDirectory.delete(); // Удаляет директорию
        }
        resDirectory.mkdir(); // Создает новую директорию
    }

    /**
     * Обрезает изображение до квадратной формы.
     * @return Обрезанное квадратное изображение
     * @throws IOException Если возникает ошибка при чтении файла
     */
    private Image trimToSquare() throws IOException {
        FileInputStream fis = new FileInputStream(imageFile); // Открывает поток чтения
        Image image = new Image(fis); // Загружает изображение
        fis.close(); // Закрывает поток
        double size = Math.min(image.getWidth(), image.getHeight()); // Находит минимальный размер
        double x = 0; // Задает начальную координату X
        double y = 0; // Задает начальную координату Y
        if (image.getWidth() > size) { // Проверяет ширину
            x = (image.getWidth() - size) / 2; // Центрирует по X
        } else {
            if (image.getHeight() > size) { // Проверяет высоту
                y = (image.getHeight() - size) / 2; // Центрирует по Y
            }
        }
        return new WritableImage(image.getPixelReader(), (int)x, (int)y, (int)size, (int)size); // Возвращает обрезанное изображение
    }

    /**
     * Разрезает изображение на фрагменты для игры.
     * @return Список фрагментов изображения
     */
    private List<Image> cropImage() {
        int size = (int)(image.getWidth() / rows); // Вычисляет размер фрагмента
        List<Image> images = new ArrayList<>(); // Создает список фрагментов
        for (int i = 0; i < columns; i++) { // Перебирает столбцы
            for (int j = 0; j < rows; j++) { // Перебирает строки
                images.add(new WritableImage(image.getPixelReader(), i * size, j * size, size, size)); // Добавляет фрагмент
            }
        }
        return images; // Возвращает список фрагментов
    }

    /**
     * Возвращает полное изображение.
     * @return Полное изображение
     */
    public Image getImage() {
        return image; // Возвращает изображение
    }

    /**
     * Возвращает список фрагментов изображения.
     * @return Список фрагментов
     */
    public List<Image> getCroppedImages() {
        return images; // Возвращает список
    }

    /**
     * Конструктор для создания загрузчика изображений.
     * @param file Файл изображения
     * @param rows Количество строк
     * @param columns Количество столбцов
     * @throws IOException Если возникает ошибка при обработке файла
     */
    PictureLoader(File file, int rows, int columns) throws IOException {
        deleteOldResources(); // Удаляет старые ресурсы
        Files.copy(file.toPath(), imageFile.toPath(), StandardCopyOption.REPLACE_EXISTING); // Копирует файл
        this.rows = rows; // Устанавливает строки
        this.columns = columns; // Устанавливает столбцы
        image = trimToSquare(); // Обрезает изображение
        images = cropImage(); // Разрезает изображение
    }
}