package tsi.discret.math.views.tape;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;

@CssImport("./css/tape-input.css")
public class TapTile extends TextField {
    public TapTile(String text) {
        super();
        setValue(text);
        setMaxLength(1);
        setWidth(2F, Unit.EM);
//        setHeight(5F, Unit.EM);
//        setMinHeight(2F, Unit.EM);
//        setMinWidth(2F, Unit.EM);
        getStyle().set("font-size", "1.5em");
        getStyle().set("font-weight", "bold");
        setValueChangeMode(ValueChangeMode.ON_CHANGE);
        addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);


        getStyle().set("padding-top", "0px");
        getStyle().set("padding-bottom", "0");
        getStyle().set("margin-top", "0");
        getStyle().set("margin-bottom", "0");

    }

    public void addBorder() {
        getStyle().set("border", "2px solid Gray");
        getStyle().set("border-radius", "10px");
    }

    public void removeBorder() {
        getStyle().remove("border");
    }
}

