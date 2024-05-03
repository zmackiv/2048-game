package cz.mendelu.xmackiv.projekt_2048.projekt_2048;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Board extends javafx.scene.canvas.Canvas {
    private int grid_size = 0;
    private Tile[][] tiles;
    int score = 0;
    boolean win = false;
    boolean lose = false;
    private Random random;
    GridPane gridPane = new GridPane();

    enum Direction {
        DOWN,
        UP,
        LEFT,
        RIGHT
    }

    public Board() {
        this.grid_size = 4;
        setFocused(true);
        resetGame();
    }

    //pokud budeme chtít jiný grid size
    public Board(int grid_size) {
        this.grid_size = grid_size;
        setFocused(true);
        resetGame();
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Nastavuje novou hru - resetuje skore, výhru, prohru, všechna políčka nastaví na hodnotu 0.
     * Přidá dvě nové políčka pro začátek hry a zavolá Gridpane pro vizuální vzhled hry.
     */
    void resetGame() {
        score = 0;
        win = false;
        lose = false;

        tiles = new Tile[grid_size][grid_size];
        random = new Random();
        for (int row = 0; row < grid_size; row++) {
            for (int col = 0; col < grid_size; col++) {
                Tile tile = new Tile();
                tiles[row][col] = tile;
                tiles[row][col].setTile(0);
            }
        }
        addTile();
        addTile();
        setupGridPane();
    }

    public int getScore() {
        return score;
    }

    public int getGridSize() {
        return grid_size;
    }

    /**
     * Pomocí Gridpane inicializuje hrací desku, nastavuje vzhled a velikost políček a jejich popisků.
     * Přidává políčka a popisky na odpovídající pozice v Gridpane
     */
    private void setupGridPane() {
        for (int row = 0; row < grid_size; row++) {
            for (int col = 0; col < grid_size; col++) {
                Tile tile = tiles[row][col];
                tile.setWidth(80);
                tile.setHeight(80);
                tile.setArcWidth(10);
                tile.setArcHeight(10);
                GridPane.setMargin(tile, new Insets(5));
                Label label = new Label(tile.getValueAsString());
                label.setFont(Font.font("Times", FontWeight.BOLD, 25));
                label.setMinSize(90, 80);
                label.setAlignment(Pos.CENTER);
                tile.updateTile();
                gridPane.add(tile, col, row);
                gridPane.add(label, col, row);

                tile.label = label;
            }
        }
    }

    /**
     * Ze seznamu volných míst si vybere libovolné souřadnice kam přidělí nové políčko.
     * Políčko může mít hodnotu 2 nebo 4, s pravděpodobností 90% to bude hodnota 2.
     * Dále také aktualizuje vizuální reprezentaci nového políčka (přidá pozadí, popředí, label)
     */
    private void addTile() {
        List<Tile> list = availableSpace();
        if (!list.isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            Tile emptyTile = list.get(index);
            emptyTile.setTile(Math.random() < 0.9 ? 2 : 4);
            emptyTile.label.setText(emptyTile.getValueAsString());
            emptyTile.setFill(emptyTile.getBackground());
            emptyTile.setStroke(emptyTile.getForeground());
        }
    }

    /**
     * Vrací seznam volných políček na hracím poli, pokud je seznam prázdný => nejsou žádné volné políčka.
     *
     * @return - metoda vrací seznam volných políček
     */
    private List<Tile> availableSpace() {
        List<Tile> list = new ArrayList<>(grid_size * grid_size);
        for (int row = 0; row < grid_size; row++) {
            for (int col = 0; col < grid_size; col++) {
                if (tiles[row][col].isEmpty()) {
                    list.add(tiles[row][col]);
                }
            }
        }
        return list;
    }

    /**
     * Zjišťuje zda se da ještě pohybovat po hracím poli, tím že zkontroluje zda pole není plné,
     * a taky zda nejde sloučit dvě políčka v horizontálním nebo vertikálním směru.
     *
     * @return - vrací hodnotu v případě, že se v hracím poli stále dá pohybovat
     */
    public boolean canMove() {
        if (!isFull()) {
            return true;
        }
        for (int row = 0; row < grid_size; row++) {
            for (int col = 0; col < grid_size - 1; col++) {
                Tile current = tiles[row][col];
                Tile next = tiles[row][col + 1];

                if (current.equals(next)) {
                    return true;
                }
            }
        }
        for (int col = 0; col < grid_size; col++) {
            for (int row = 0; row < grid_size - 1; row++) {
                Tile current = tiles[row][col];
                Tile below = tiles[row + 1][col];

                if (current.equals(below)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Provede kontrolu zda je hrací plocha plná.
     *
     * @return - vrací hodnotu true v případě že list s volnými políčky je prázdný
     */
    private boolean isFull() {
        return availableSpace().isEmpty();
    }

    /**
     * Projde popořadě každé políčko a aktualizuje popisky ve virtuálním rozhraní.
     */
    private void updateLabels() {
        for (int row = 0; row < grid_size; row++) {
            for (int col = 0; col < grid_size; col++) {
                tiles[row][col].label.setText(tiles[row][col].getValueAsString());
            }
        }
    }

    /**
     * Projde popořadě každé políčko a kontroluje zda jej hodnota není 2048,
     * pokud je podmínka splněna vrátí hodnotu true.
     *
     * @return vrátí hodnotu true pokud jakékoliv políčko ve hře dosáhlo hodnoty 2048
     */
    public boolean hasWon() {
        for (int row = 0; row < grid_size; row++) {
            for (int col = 0; col < grid_size; col++) {
                Tile tile = tiles[row][col];
                if (tile.getTile() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Dle zadaného směru se políčka posunou na takové souřadnice jako kdyby směřovali DOWN.
     * Díky čemuž, můžeme mít jen jednu funkci pro pohyb políček (funkce action),
     * čímž se zamezí, že by každý směr měl samostatnou funkci pro pohyb políček
     *
     * @param direction - směr v jakém se mají políčka posunout (DOWN, UP, LEFT, RIGHT).
     * @param x - x-ová souřadnice políčka
     * @param y - y-ová souřadnice políčka
     * @return - vracíme nové pořadí políček
     */
    public Tile getTileByDirection(Direction direction, int x, int y) {
        switch (direction) {
            case DOWN:
                return tiles[x][y];
            case UP:
                return tiles[grid_size - x - 1][grid_size - y - 1];
            case LEFT:
                return tiles[y][grid_size - x - 1];
            case RIGHT:
                return tiles[grid_size - y - 1][x];
        }
        return tiles[x][y];
    }

    /**
     * Provede posun políček dle zadaného směru, který se převzal jako input hráče.
     * Sečte políčka se stejnou hodnotou a pokud dojde k pohybu tak přidá nové políčko a aktualizuje popisky hry.
     *
     * @param direction - směr v jakém se mají políčka posunout (DOWN, UP, LEFT, RIGHT).
     */
    public void action(Direction direction) {
        boolean moved = false;
        //průchod skrz sloupce a pak řádky gridu
        for (int col = 0; col < grid_size; col++) {
            Tile currentTile = getTileByDirection(direction, grid_size - 1, col);
            for (int row = grid_size - 2; row >= 0; row--) {
                if (currentTile.isEmpty()) {
                    currentTile = getTileByDirection(direction, row, col);
                    continue;
                }
                Tile nextTile = getTileByDirection(direction, row, col);

                if (nextTile.isEmpty()) {
                    continue;
                }
                // políčka se stejnými hodnotamai se spoji
                if (nextTile.equals(currentTile)) {
                    int value = nextTile.getTile() * 2;
                    score += value;
                    nextTile.setTile(0);
                    currentTile.setTile(value);
                    moved = true;
                }
                currentTile = getTileByDirection(direction, row, col);
            }
        }
        // neprázdné políčka se posunou dolů na konec gridu
        for (int i = 0; i < grid_size - 1; i++) {
            for (int col = 0; col < grid_size; col++) {
                for (int row = grid_size - 2; row >= 0; row--) {
                    Tile currentTile = getTileByDirection(direction, row, col);
                    Tile nextTile = getTileByDirection(direction, row + 1, col);

                    if (!currentTile.isEmpty() && nextTile.isEmpty()) {
                        nextTile.setTile(currentTile.getTile());
                        currentTile.setTile(0);
                        moved = true;
                    }
                }
            }
        }
        //pokud dojde k posunu, tak se přidá nové políčko a obnoví se popisky políček
        if (moved) {
            addTile();
            updateLabels();
        }
    }
}
