package cz.mendelu.xmackiv.projekt_2048.projekt_2048;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Tile extends Rectangle {
    private int value;
    Label label;

    public Tile() {
        this.value = 0;
        this.label = new Label();
        updateTile();
    }

    public int getTile() {
        return value;
    }

    public void setTile(int value) {
        this.value = value;
        updateTile();
    }

    /**
     * @return - vrací nám buď prázdný string pokud je hodnota rovna 0 nebo jeho hodnotu jako string
     */
    public String getValueAsString(){
        String value_string;
        if(value==0){
            value_string = "";
        } else{
            value_string = String.valueOf(value);
        }
        return value_string;
    }

    /**
     * Porovnává toto a zadané políčko, zda jsou stejné hodnoty.
     *
     * @param tile - druhé políčko se kterým je aktuální porovnávané
     * @return - vrací hodnotu true/false pokud políčka mají/nemají stejnou hodnotu
     */
    public boolean equals(Tile tile){
        return this.value == tile.value;
    }

    /**
     * @return - vrací barvu pozadí daného políčka, barva je závislá na hodnotě políčka
     */
    public Color getBackground(){
        try {
            switch (value) {
                case 2:		return Color.rgb(238, 228, 218, 1.0); //238 228 218 1.0      0xeee4da
                case 4: 	return Color.rgb(237, 224, 200, 1.0); //237, 224, 200, 1.0   0xede0c8
                case 8: 	return Color.rgb(242, 177, 121, 1.0); //242, 177, 121, 1.0   0xf2b179
                case 16: 	return Color.rgb(245, 149, 99, 1.0); //245, 149, 99, 1.0     0xf59563
                case 32: 	return Color.rgb(246, 124, 95, 1.0); //246, 124, 95, 1.0     0xf67c5f
                case 64:	return Color.rgb(246, 94, 59, 1.0 ); //246, 94, 59, 1.0      0xf65e3b
                case 128:	return Color.rgb(237, 207, 114, 1.0); //237, 207, 114, 1.0   0xedcf72
                case 256: 	return Color.rgb(237, 204, 97, 1.0); //237, 204, 97, 1.0     0xedcc61
                case 512: 	return Color.rgb(237, 200, 80, 1.0); //237, 200, 80, 1.0     0xedc850
                case 1024: 	return Color.rgb(237, 197, 63, 1.0); //237, 197, 63, 1.0     0xedc53f
                case 2048: 	return Color.rgb(237, 194, 46, 1.0); //237, 194, 46, 1.0     0xedc22e
            }
            return Color.rgb(205, 193, 180, 1.0); //0xcdc1b4
        }catch (NumberFormatException e) {
            return Color.rgb(205, 193, 180, 1.0); // default color
        }
    }

    /**
     * @return - vrací barvu popředí daného políčka
     */
    public Color getForeground() {
        try {
            Color foreground;
            foreground = Color.rgb(119, 110, 101, 1.0); //0x776e65
            return foreground;
        } catch (NumberFormatException e) {
            // Zpracování případu, kdy dojde k chybě při parsování (např., hodnota není celé číslo)
            return Color.rgb(119, 110, 101, 1.0); // výchozí foreground barva
        }
    }

    /**
     * Obnoví políčko s jeho novými hodnotami (jeho text, pozadí a popředí)
     */
    public void updateTile() {
        this.label.setText(this.getValueAsString());
        this.label.setAlignment(Pos.CENTER);
        setFill(getBackground());
        setStroke(getForeground());
    }

    /**
     * Kontroluje zda je políčko "prázdné" respektive rovno 0.
     *
     * @return - vrací hodnotu true pokud je hodnota daného políčka rovna 0
     */
    public boolean isEmpty() {
        return value==0;
    }
}
