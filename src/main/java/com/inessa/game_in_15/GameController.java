// Автор: Высоцкая И.Д.
package com.inessa.game_in_15;

import javafx.fxml.FXML; // Импортирует аннотацию для привязки FXML
import javafx.scene.Node; // Импортирует класс для узлов интерфейса
import javafx.scene.control.Button; // Импортирует класс для кнопок
import javafx.scene.control.Label; // Импортирует класс для текстовых меток
import javafx.scene.image.Image; // Импортирует класс для изображений
import javafx.scene.layout.*; // Импортирует классы для компоновки
import javafx.stage.FileChooser; // Импортирует класс для выбора файлов
import java.io.IOException; // Импортирует исключение ввода-вывода
import java.util.ArrayList; // Импортирует класс для списков
import java.util.Arrays; // Импортирует класс для массивов
import java.util.Collections; // Импортирует класс для работы с коллекциями
import java.util.List; // Импортирует интерфейс для списков

/**
 * Контроллер для управления интерфейсом игры "Пятнашки"
 */
public class GameController {

    /** Главная панель интерфейса */
    @FXML
    public BorderPane mainPane;

    /** Кнопка для открытия изображения. */
    @FXML
    public Button openButton;

    /** Сетка 4x4 для размещения фрагментов изображения. */
    @FXML
    public GridPane grid;

    /** Панель для отображения полного изображения. */
    @FXML
    public AnchorPane fullImagePane;

    /** Панель для фрагмента изображения в позиции (0,0). */
    @FXML
    public AnchorPane imagePane00;

    /** Панель для фрагмента изображения в позиции (0,1). */
    @FXML
    public AnchorPane imagePane01;

    /** Панель для фрагмента изображения в позиции (0,2). */
    @FXML
    public AnchorPane imagePane02;

    /** Панель для фрагмента изображения в позиции (0,3). */
    @FXML
    public AnchorPane imagePane03;

    /** Панель для фрагмента изображения в позиции (1,0). */
    @FXML
    public AnchorPane imagePane10;

    /** Панель для фрагмента изображения в позиции (1,1). */
    @FXML
    public AnchorPane imagePane11;

    /** Панель для фрагмента изображения в позиции (1,2). */
    @FXML
    public AnchorPane imagePane12;

    /** Панель для фрагмента изображения в позиции (1,3). */
    @FXML
    public AnchorPane imagePane13;

    /** Панель для фрагмента изображения в позиции (2,0). */
    @FXML
    public AnchorPane imagePane20;

    /** Панель для фрагмента изображения в позиции (2,1). */
    @FXML
    public AnchorPane imagePane21;

    /** Панель для фрагмента изображения в позиции (2,2). */
    @FXML
    public AnchorPane imagePane22;

    /** Панель для фрагмента изображения в позиции (2,3). */
    @FXML
    public AnchorPane imagePane23;

    /** Панель для фрагмента изображения в позиции (3,0). */
    @FXML
    public AnchorPane imagePane30;

    /** Панель для фрагмента изображения в позиции (3,1). */
    @FXML
    public AnchorPane imagePane31;

    /** Панель для фрагмента изображения в позиции (3,2). */
    @FXML
    public AnchorPane imagePane32;

    /** Панель для скрытого (пустого) фрагмента. */
    @FXML
    public AnchorPane hiddenImagePane;

    /** Метка для отображения количества сделанных ходов. */
    @FXML
    public Label moveLabel;

    /** Количество строк в игровой сетке (4). */
    private final int ROWS = 4;

    /** Количество столбцов в игровой сетке (4). */
    private final int COLUMNS = 4;

    /** Список панелей с фрагментами изображения. */
    private List<AnchorPane> imageList;

    /** Поле модели игры */
    private GameModel model;

    /**
     * Инициализация
     */
    public void initialize() {
        model = new GameModel();
        moveLabel.setText(String.valueOf(model.getMoves()));
        initImageList();
    }

    /**
     * Инициализация списка панелей с фрагментами изображения
     */
    private void initImageList() {
        this.imageList = Collections.unmodifiableList(Arrays.asList( // Создает неизменяемый список
                imagePane00, imagePane10, imagePane20, imagePane30, // Добавляет панели первой строки
                imagePane01, imagePane11, imagePane21, imagePane31, // Добавляет панели второй строки
                imagePane02, imagePane12, imagePane22, imagePane32, // Добавляет панели третьей строки
                imagePane03, imagePane13, imagePane23, hiddenImagePane // Добавляет панели четвертой строки
        ));
    }

    /**
     * Устанавливает фрагменты изображения на соответствующие панели.
     * @param images Список фрагментов изображения
     */
    private void setUpImages(List<Image> images) {
        for (int i = 0; i < images.size(); i++) { // Перебирает изображения
            BackgroundImage backgroundImage = new BackgroundImage(images.get(i), BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(imageList.get(i).getWidth(),
                    imageList.get(i).getHeight(), true, true, true, true)); // Создает фоновое изображение
            imageList.get(i).setBackground(new Background(backgroundImage)); // Устанавливает фон панели
        }
        imageList.get(ROWS * COLUMNS - 1).setVisible(false); // Скрывает пустую панель
    }

    /**
     * Инициализирует игровую сетку, размещая фрагменты в случайном порядке.
     */
    @FXML
    private void initGridPane() {
        grid.getChildren().clear(); // Очищает элементы сетки
        model.shuffle(); //Перемешивает порядок фрагментов
        List<Integer> orderList = model.getOrderList();
        for (int column = 0, i = 0; column < COLUMNS; column++) { // Перебирает столбцы
            for (int row = 0; row < ROWS; row++, i++) { // Перебирает строки
                AnchorPane imagePane = imageList.get(orderList.get(i)); // Получает панель
                imagePane.setDisable(false); // Включает взаимодействие
                grid.add(imagePane, column, row); // Добавляет панель в сетку
            }
        }
        moveLabel.setText(String.valueOf(model.getMoves()));
    }

    /**
     * Обрабатывает клик по клетке, перемещая фрагмент, если это возможно.
     * @param mouseEvent Событие клика мыши
     */
    public void cellClickAction(javafx.scene.input.MouseEvent mouseEvent) {
        AnchorPane clickedPane = (AnchorPane) mouseEvent.getSource(); // Получаем кликнутую панель
        int row = GridPane.getRowIndex(clickedPane); // Получаем строку кликнутой панели
        int column = GridPane.getColumnIndex(clickedPane); // Получаем столбец кликнутой панели
        int hiddenRow = GridPane.getRowIndex(hiddenImagePane); // Получаем строку пустой панели
        int hiddenColumn = GridPane.getColumnIndex(hiddenImagePane); // Получаем столбец пустой панели
        if (model.moveTile(row, column, hiddenRow, hiddenColumn)) { // Проверяем возможность перемещения
            grid.getChildren().remove(clickedPane); // Удаляем кликнутую панель
            grid.getChildren().remove(hiddenImagePane); // Удаляем пустую панель
            grid.add(clickedPane, hiddenColumn, hiddenRow); // Добавляем кликнутую панель
            grid.add(hiddenImagePane, column, row); // Добавляем пустую панель
            moveLabel.setText(String.valueOf(model.getMoves()));
        }
        if (model.isVictory()) {
            Victory();
        }
    }

    /**
     * Обрабатывает победу, отображая финальное состояние игры.
     */
    private void Victory() {
        hiddenImagePane.setVisible(true); // Показываем пустую панель
        fullImagePane.setVisible(false); // Скрываем полное изображение
        openButton.setVisible(true); // Показываем кнопку открытия
        for (Node node : grid.getChildren()) { // Перебираем элементы сетки
            node.setDisable(true); // Отключаем взаимодействие
        }
        moveLabel.setText("Победа! Шаги: " + String.valueOf(model.getMoves()));
    }

    /**
     * Загружает и обрабатывает выбранное изображение для игры.
     */
    private void openImage() {
        FileChooser fileChooser = new FileChooser(); // Создает диалог выбора файла
        fileChooser.setTitle("Открыть изображение"); // Устанавливает заголовок диалога
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Изображение" + PictureLoader.IMAGE_FILES_EXTENSIONS, PictureLoader.IMAGE_FILES_EXTENSIONS)); // Добавляет фильтр
        try {
            PictureLoader pictureLoader = new PictureLoader(fileChooser.showOpenDialog(mainPane.getScene().getWindow()), ROWS, COLUMNS); // Создает загрузчик изображения
            BackgroundImage backgroundImage = new BackgroundImage(pictureLoader.getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(fullImagePane.getWidth(), fullImagePane.getHeight(), true, true, true, true)); // Создает фоновое изображение
            fullImagePane.setBackground(new Background(backgroundImage)); // Устанавливает фон
            fullImagePane.setVisible(true); // Показывает полное изображение
            openButton.setVisible(false); // Скрывает кнопку открытия
            initImageList(); // Инициализирует список панелей
            setUpImages(pictureLoader.getCroppedImages()); // Устанавливает фрагменты
            initGridPane(); // Инициализирует сетку
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обрабатывает действие открытия изображения
     */
    public void openImageAction() {
        grid.setVisible(false); // Скрывает сетку
        openImage(); // Загружает изображение
        grid.setVisible(true); // Показывает сетку
    }
}