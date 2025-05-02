package game;

import java.util.Arrays;

public class GameState {
    private final Cell[] cells;
    private final String currentPlayer;
    private final String winner;

    private GameState(Cell[] cells, String currentPlayer, String winner) {
        this.cells = cells;
        this.currentPlayer = currentPlayer;
        this.winner = winner;
    }

    public static GameState forGame(Game game) {
        Cell[] cells = getCells(game.getBoard());
        String current = game.getPlayer() == Player.PLAYER0 ? "X" : "O";
        Player winner = game.getWinner();
        String winnerStr = winner == null ? "" : (winner == Player.PLAYER0 ? "X" : "O");
        return new GameState(cells, current, winnerStr);
    }

    public Cell[] getCells() {
        return this.cells;
    }

    @Override
    public String toString() {
        return """
                {
                    "cells": %s,
                    "currentPlayer": "%s",
                    "winner": "%s"
                }
                """.formatted(Arrays.toString(this.cells), this.currentPlayer, this.winner);
    }

    /**
     * Inner class representing each cell of the board.
     */
    static class Cell {
        private final int x;
        private final int y;
        private final String text;
        private final boolean playable;

        Cell(int x, int y, String text, boolean playable) {
            this.x = x;
            this.y = y;
            this.text = text;
            this.playable = playable;
        }

        @Override
        public String toString() {
            return """
                    {
                        "text": "%s",
                        "playable": %b,
                        "x": %d,
                        "y": %d
                    }
                    """.formatted(this.text, this.playable, this.x, this.y);
        }
    }

    private static Cell[] getCells(Board board) {
        Cell[] cells = new Cell[9];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                String text = "";
                boolean playable = false;
                Player player = board.getCell(x, y);
                if (player == Player.PLAYER0)
                    text = "X";
                else if (player == Player.PLAYER1)
                    text = "O";
                else
                    playable = true;
                cells[3 * y + x] = new Cell(x, y, text, playable);
            }
        }
        return cells;
    }
}
