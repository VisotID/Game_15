// Автор: Высоцкая И.Д.
package com.inessa.game_in_15;

import javafx.application.Application; // Импортирует базовый класс для JavaFX приложений
import javafx.fxml.FXMLLoader; // Импортирует класс для загрузки FXML-файлов
import javafx.scene.Scene; // Импортирует класс для создания сцены
import javafx.stage.Stage; // Импортирует класс для создания окна приложения
import java.io.IOException; // Импортирует исключение для обработки ошибок ввода-вывода

/**
 * Главный класс приложения "Пятнашки", отвечающий за запуск JavaFX приложения.
 */
public class GameApplication extends Application {

    /**
     * Инициализирует и отображает главное окно приложения.
     * @param stage Главное окно приложения
     * Бросается исключение, если возникает ошибка при загрузке FXML-файла
     */
    @Override
    public void start(Stage stage) throws IOException { // Переопределяет метод start
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("game_15.fxml")); // Создает загрузчик для FXML-файла
        Scene scene = new Scene(fxmlLoader.load(), 690, 510); // Создает сцену с размерами 690x510
        stage.setTitle("In 15"); // Устанавливает заголовок окна
        stage.setScene(scene); // Устанавливает сцену для окна
        stage.show(); // Отображает окно
    }

    /**
     * Точка входа в приложение, запускает JavaFX приложение.
     * @param args Аргументы командной строки
     */
    public static void main(String[] args) { // Определяет главный метод приложения
        launch(); // Запускает JavaFX приложение
    }
}