package tsi.discret.math.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import tsi.discret.math.machine.Command;

@Route("details-content")
public class CommandView extends Div {

    private final Grid<Command> grid;

    public CommandView(ListDataProvider<Command> dataProvider) {
        grid = new Grid<>(Command.class, false);
        grid.addColumn(command -> command.getIfSymbol() + "/" + command.getIfState()).setHeader("If Symbol/If State")
                .setAutoWidth(true).setSortable(true).setResizable(true)
                .setFlexGrow(0);
        grid.addColumn(Command::getNewState).setHeader("New State")
                .setAutoWidth(true).setSortable(true).setResizable(true);
        grid.addColumn(Command::getWhatToWrite).setHeader("What to write")
                .setAutoWidth(true).setSortable(true).setResizable(true);
        grid.addColumn(Command::getMovementDirection).setHeader("Direction")
                .setAutoWidth(true).setSortable(true).setResizable(true);
        grid.addColumn(Command::isEndCommand).setHeader("Is end")
                .setAutoWidth(true).setSortable(true).setResizable(true);
        grid.setItems(dataProvider);

        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        add(grid);
    }

    public void select(Command command) {
        grid.select(command);
    }
}
