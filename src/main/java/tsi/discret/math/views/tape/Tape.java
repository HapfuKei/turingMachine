package tsi.discret.math.views.tape;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.ArrayList;

//@Tag("div")
//@CssImport("./css/scrollbar.css")
public class Tape extends HorizontalLayout {

    private final ArrayList<TapTile> tapTiles = new ArrayList<>();

    public Tape(int cellCount) {
        getStyle().set("display", "inline-flex");
        getStyle().set("margin-bottom", "10px");

        for (int i = 0; i < cellCount; i++) {
            TapTile tapTile = new TapTile("");
            add(tapTile);
            tapTiles.add(tapTile);
        }
    }

    public ArrayList<TapTile> getTapTiles() {
        return tapTiles;
    }

}
