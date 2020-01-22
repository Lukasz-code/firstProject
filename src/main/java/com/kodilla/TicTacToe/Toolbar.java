package com.kodilla.TicTacToe;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private TicTacToe ticTacToe;

    public Toolbar(TicTacToe ticTacToe) {
        this.ticTacToe = ticTacToe;

        Button clear = new Button("Start new game");
        clear.setOnAction(this::handleClear);

        Button player_vs_player = new Button("Player vs Player");
        player_vs_player.setOnAction((this::handlePvP));

        Button player_vs_computer = new Button("Player vs Computer");
        player_vs_computer.setOnAction(this::handlePvE);

        this.getItems().addAll(clear, player_vs_computer, player_vs_player);
    }



    private void handlePvE(ActionEvent actionEvent) {
        ticTacToe.setCompTurn(true);


    }

    private void handlePvP(ActionEvent actionEvent) {
        ticTacToe.setCompTurn(false);
        ticTacToe.setTurnX(true);


    }

    private void handleClear(ActionEvent actionEvent) {
        ticTacToe.clearFields();


    }
}