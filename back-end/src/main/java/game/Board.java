package game;

public class Board {
    private final Player[][] cells;

    public Board() {
        this.cells = new Player[3][3];
    }

    private Board(Player[][] cells) {
        this.cells = cells;
    }

    public Player getCell(int x, int y) {
        return this.cells[y][x];
    }

    public Board updateCell(int x, int y, Player player) {
        Player[][] newCells = new Player[3][3];
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                newCells[row][col] = this.cells[row][col];
        newCells[y][x] = player;
        return new Board(newCells);
    }
}
