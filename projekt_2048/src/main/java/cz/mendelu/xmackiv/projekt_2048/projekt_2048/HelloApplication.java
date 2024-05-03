package cz.mendelu.xmackiv.projekt_2048.projekt_2048;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("2048!");
        Board game = new Board();

        BorderPane root = new BorderPane();
        VBox topBox = new VBox();
        topBox.setSpacing(10);
        topBox.setPadding(new Insets(10));

        // Přidání názvu hry
        javafx.scene.control.Label titleLabel = new javafx.scene.control.Label("2048!");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        topBox.getChildren().add(titleLabel);

        // Přidání skóre
        javafx.scene.control.Label scoreLabel = new javafx.scene.control.Label("Score: " + game.getScore());
        topBox.getChildren().add(scoreLabel);

        // Přidání herního gridu
        topBox.getChildren().add(game.gridPane);
        root.setTop(topBox);

        // Vytvoření vrstvy pro zobrazení výsledků
        VBox resultsLayer = new VBox();
        resultsLayer.setAlignment(Pos.CENTER);
        resultsLayer.setStyle("-fx-background-color: rgba(205, 193, 180, 1.0);"); // Průhledný bílý pozadí

        // Vytvoření labelu pro zobrazení výsledků
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        resultsLayer.getChildren().add(resultLabel);

        // Přidání tlačítka pro restart hry
        Button restartButton = new Button("New Game");
        restartButton.setStyle("-fx-background-color: #f0ece2; -fx-text-fill: black; -fx-font-size: 14px;");
        restartButton.setOnMouseEntered(e -> {
            restartButton.setStyle("-fx-background-color: #dfd3c3; -fx-text-fill: black; -fx-font-size: 14px;");
        });
        restartButton.setOnMouseExited(e -> {
            restartButton.setStyle("-fx-background-color: #f0ece2; -fx-text-fill: black; -fx-font-size: 14px;");
        });
        restartButton.setOnAction(e -> {
            game.resetGame();
            resultsLayer.setVisible(false);
        });
        resultsLayer.getChildren().add(restartButton);

        // Nastavení vrstvy nad herní plochou
        root.setCenter(resultsLayer);
        resultsLayer.setVisible(false);

        BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(230, 226, 215), null, null);
        Background background = new Background(backgroundFill);
        root.setBackground(background);

        Scene myScene = new Scene(root, 385, 550);

        // po stlačení tlačítka se zkontroluje zda ještě není konec hry, pokud ne tak se zavolá pohyb
        myScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    game.resetGame();
                    resultsLayer.setVisible(false);
                }
                if (!game.canMove() || (!game.win && !game.canMove())) {
                    game.lose = true;
                    resultLabel.setText("Game over! You lose!");
                    resultsLayer.setVisible(true);
                }
                if (game.hasWon()) {
                    game.win = true;
                    resultLabel.setText("Congratulations! You won!");
                    resultsLayer.setVisible(true);
                }
                if (!game.win && !game.lose) {
                    switch (event.getCode()) {
                        case DOWN:
                            game.action(Board.Direction.DOWN);
                            break;
                        case UP:
                            game.action(Board.Direction.UP);
                            break;
                        case LEFT:
                            game.action(Board.Direction.LEFT);
                            break;
                        case RIGHT:
                            game.action(Board.Direction.RIGHT);
                            break;
                    }
                }
                scoreLabel.setText("Score: " + game.getScore());
                game.relocate(330, 390);
            }
        });
        game.gridPane.getChildren().add(game);
        stage.setScene(myScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}