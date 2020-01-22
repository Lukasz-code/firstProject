package com.kodilla.TicTacToe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TicTacToe extends Application {

    private boolean playable = true;
    private boolean turnX = true;
    private Tile[][] board = new Tile[3][3];
    private List<Combo> winConditions = new ArrayList<>();
    private Image imageback = new Image("siatka.png");
    private boolean compTurn = false;
    private Pane root = new Pane();

    public void setTurnX(boolean turnX) {
        this.turnX = turnX;
    }
    public void setCompTurn(boolean compTurn) {
        this.compTurn = compTurn;
    }

    private Parent createContent() {

        root.setPrefSize(600, 600);
        BackgroundImage backgroundImage = new BackgroundImage(imageback,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        root.setBackground(new Background(backgroundImage));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);

                root.getChildren().add(tile);

                board[j][i] = tile;
            }
        }

        for (int y = 0; y < 3; y++) {
            winConditions.add(new Combo(board[0][y], board[1][y], board[2][y]));
        }

        for (int x = 0; x < 3; x++) {
            winConditions.add(new Combo(board[x][0], board[x][1], board[x][2]));
        }

        winConditions.add(new Combo(board[0][0], board[1][1], board[2][2]));
        winConditions.add(new Combo(board[2][0], board[1][1], board[0][2]));

        return root;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("TicTacToe");
        primaryStage.setResizable(false);

        Toolbar toolbar = new Toolbar(this);
        root.getChildren().add(toolbar);

        primaryStage.show();
    }

    private void computerTurn () {
        List<Tile> tiles = root.getChildren().stream()
                .filter(node -> {
                    if (node instanceof Tile) {
                        Tile tile = (Tile) node;
                        return tile.isEmpty();
                    }
                    return false;
                })
                .map(node -> (Tile) node)
                .collect(Collectors.toList());

        Random random = new Random();
        int randomNumber = random.nextInt(tiles.size());
        Tile tile = tiles.get(randomNumber);
        tile.drawO();
        turnX = true;
        checkState();
    }
    public void clearFields(){
        root.getChildren().stream()
                .forEach(node -> {
                    if (node instanceof Tile) {
                        Tile tile = (Tile) node;
                        tile.clear();
                    }
                });
        playable = true;
        turnX = true;
    }

    private void checkState() {
        for (Combo combo : winConditions) {
            if (combo.isComplete()) {
                playable = false;
                break;
            }
        }
    }

    private class Combo {
        private Tile[] tiles;
        public Combo(Tile... tiles) {
            this.tiles = tiles;
        }

        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    private class Tile extends StackPane {
        private Text text = new Text();


        public boolean isEmpty () {
            return text.getText().isEmpty();
        }

        public void playerX () {
            if (!turnX)
                return;

            drawX();
            turnX = false;

            checkState();
        }
        public void playerO () {
            if (turnX)
                return;

            drawO();
            turnX = true;

            checkState();
        }


        public Tile() {
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.TRANSPARENT);

            text.setFont(Font.font(110));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event -> {
                System.out.println("playable: " + playable);
                System.out.println("printX: " + turnX);
                System.out.println("computerTurn: " + compTurn);

                if (!playable)
                    return;

                if (event.getButton() == MouseButton.SECONDARY) {

                    playerX();
                    if (compTurn) {
                        computerTurn();
                    }
                }
                if (event.getButton() == MouseButton.PRIMARY){
                    playerO();}

            });
        }

        public String getValue() {
            return text.getText();
        }

        private void drawX() {
            text.setText("X");
        }

        private void drawO() {
            text.setText("O");
        }
        private void clear() {
            text.setText ("");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}