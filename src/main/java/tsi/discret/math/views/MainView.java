package tsi.discret.math.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import tsi.discret.math.machine.CalculationService;
import tsi.discret.math.machine.Command;
import tsi.discret.math.views.tape.TapTile;
import tsi.discret.math.views.tape.Tape;

import java.util.*;

@Route("")
public class MainView extends VerticalLayout {
    private final ListDataProvider<Command> commandsDataProvider = new ListDataProvider<>(new ArrayList<>());
    private final CalculationService calculationService = new CalculationService();
    private final CommandView commandListView;
    private final TextField stateHeadState = new TextField("Current head state", "q0");
    private boolean continueExecuting = false;
    private int currentHeadPosition = 2;
    private ArrayList<TapTile> tapTiles;


    public MainView() {

        TextArea inputDataTextArea = new TextArea();
        inputDataTextArea.setWidthFull();
        inputDataTextArea.setHeight("5em");
        inputDataTextArea.setMaxHeight("5em");
        inputDataTextArea.setLabel("Input string");
        inputDataTextArea.setValue("aabbab");

        TextArea codeTextArea = new TextArea();
//        codeTextArea.setSizeFull();
        codeTextArea.setMinHeight("10em");
        codeTextArea.setMinWidth("25em");
        codeTextArea.setLabel("Turing code");
        codeTextArea.getStyle().set("padding", "1em");

        codeTextArea.addValueChangeListener(event -> {
            List<Command> commands = calculationService.processCodeToCommands(codeTextArea.getValue());
            refreshCommandDataProvider(commands);
        });

        codeTextArea.setValue("""
                q0/a=b q0 >; q0/b=a q0 >; q0/_=!;
                """);

//        Button stepButton = new Button("One step");
//        Button startButton = new Button("Start");
//        Button loadDataButton = new Button("Load data");
//        Button resetButton = new Button("Reset");

//        stepButton.addClickListener(event -> {
//            List<Command> commands = calculationService.processCodeToCommands(codeTextArea.getValue());
//            refreshCommandDataProvider(commands);
//            stepTuringMachine(commands);
//        });
//
//        startButton.addClickListener(event -> {
//            List<Command> commands = calculationService.processCodeToCommands(codeTextArea.getValue());
//            refreshCommandDataProvider(commands);
//            getUI().ifPresent(ui -> ui.access(() -> {
//                continueExecuting = true;
//                recursiveStepTuringMachine(commands);
//            }));
//        });
//
//
//        loadDataButton.addClickListener(event -> {
//            String inputString = inputDataTextArea.getValue();
//            for (int i = 0; i < inputString.length(); i++) {
//                char charAt = inputString.charAt(i);
//                tapTiles.get(currentHeadPosition + i).setValue(String.valueOf(charAt));
//            }
//
//
//        });
//
//        resetButton.addClickListener(event -> {
//            tapTiles.forEach(tapTile -> tapTile.setValue(""));
//            int center = tapTiles.size() / 2;
//            tapTiles.get(center).focus();
//            currentHeadState = "q0";
//        });

        VerticalLayout tapeContainer = new VerticalLayout(constructTape(15), inputDataTextArea);
        tapeContainer.setSizeFull();

        IntegerField cellCountFiled = new IntegerField("Cell count");
        cellCountFiled.setPlaceholder("Cell count");
        cellCountFiled.setClearButtonVisible(true);
        cellCountFiled.setValueChangeMode(ValueChangeMode.LAZY);
        cellCountFiled.setValue(15);
        cellCountFiled.addValueChangeListener(event -> {
            tapeContainer.removeAll();
            tapeContainer.add(constructTape(cellCountFiled.getValue()), inputDataTextArea);
            resetState();
            resetSelectedCellBorder(null);
        });


        stateHeadState.getStyle().set("padding-left", "10px");

        MenuBar menuBar = new MenuBar();
        menuBar.addItem("One step", event -> {
            List<Command> commands = calculationService.processCodeToCommands(codeTextArea.getValue());
            refreshCommandDataProvider(commands);
            stepTuringMachine(commands);
        });
        menuBar.addItem("Start", event -> {
            List<Command> commands = calculationService.processCodeToCommands(codeTextArea.getValue());
            refreshCommandDataProvider(commands);
            getUI().ifPresent(ui -> ui.access(() -> {
                continueExecuting = true;
                recursiveStepTuringMachine(commands);
            }));
        });
        menuBar.addItem("Load data", event -> {
            String inputString = inputDataTextArea.getValue();
            for (int i = 0; i < inputString.length(); i++) {
                char charAt = inputString.charAt(i);
                tapTiles.get(currentHeadPosition + i).setValue(String.valueOf(charAt));
            }
        });
        menuBar.addItem("Reset", event -> resetState());
        menuBar.getStyle().set("padding-right", "1em");

        commandListView = createCommandList();

        FlexLayout bottomLayoutFlex = new FlexLayout();
        bottomLayoutFlex.add(codeTextArea, commandListView);
        bottomLayoutFlex.setFlexDirection(FlexLayout.FlexDirection.ROW);
        bottomLayoutFlex.setSizeFull();
        bottomLayoutFlex.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        bottomLayoutFlex.setJustifyContentMode(JustifyContentMode.START);
        bottomLayoutFlex.getStyle().set("padding", "2em");

        HorizontalLayout bottomLayout = new HorizontalLayout(bottomLayoutFlex);
        bottomLayout.setPadding(true);
        bottomLayout.setSizeFull();

        resetState();
        resetSelectedCellBorder(null);


        FlexLayout menuLayoutFlex = new FlexLayout();
        menuLayoutFlex.add(menuBar, cellCountFiled, stateHeadState);
        menuLayoutFlex.setFlexDirection(FlexLayout.FlexDirection.ROW);
        menuLayoutFlex.setSizeFull();
        menuLayoutFlex.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        menuLayoutFlex.setJustifyContentMode(JustifyContentMode.START);

        HorizontalLayout menuBarLayout = new HorizontalLayout(menuLayoutFlex);
        menuBarLayout.setAlignItems(Alignment.BASELINE);
        menuBarLayout.setPadding(true);

        HorizontalLayout menuBarLayoutWithHelp = new HorizontalLayout(menuBarLayout, createHelp());
        menuBarLayoutWithHelp.setSizeFull();

        addRoundBorder(menuBarLayout);
        addRoundBorder(tapeContainer);
        addRoundBorder(bottomLayout);

        add(menuBarLayoutWithHelp, tapeContainer, bottomLayout);
    }

    private CommandView createCommandList() {
        CommandView commandView = new CommandView(commandsDataProvider);
        commandView.setSizeFull();
        commandView.setMaxHeight("90em");
        commandView.setMaxWidth("40em");
        commandView.getStyle().set("padding", "2.3em");
        return commandView;
    }

    private HorizontalLayout constructTape(int cellCount) {
        Tape tape = new Tape(cellCount);
        tapTiles = tape.getTapTiles();

        Scroller scroller = new Scroller();
        scroller.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);
        scroller.setContent(tape);

        tapTiles.forEach(tapTile -> tapTile.addFocusListener(event -> {
            TapTile oldTile = tapTiles.get(currentHeadPosition);
            currentHeadPosition = tapTiles.indexOf(tapTile);
            resetSelectedCellBorder(oldTile);
        }));

        HorizontalLayout horizontalLayout = new HorizontalLayout(scroller);
        horizontalLayout.setSizeFull();

        return horizontalLayout;
    }

    private HorizontalLayout createHelp() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.setPadding(true);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.END);

        Button aboutButton = new Button("About");
        aboutButton.addThemeVariants(ButtonVariant.LUMO_LARGE);

        Dialog dialog = new Dialog();
        dialog.setHeight("50%");
        dialog.setWidth("50%");

        Tab welcome = new Tab("Welcome");
        Tab buttons = new Tab("Buttons");
        Tab fields = new Tab("Fields");
        Tab code = new Tab("Code");
        Tab quickGuide = new Tab("Quick guide");

        TextArea textArea = new TextArea();
        textArea.setReadOnly(true);
        textArea.setSizeFull();

        String welcomeString = """
                Welcome, you try to use Turing machine simulator\s
                that written like course project. Below you could\s
                see short description how to use this machine.\s
                """;
        textArea.setValue(welcomeString);

        Tabs tabs = new Tabs(welcome, buttons, fields, code, quickGuide);
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = event.getSelectedTab();
            textArea.clear();
            if (selectedTab.equals(welcome)) {
                textArea.setValue(welcomeString);
            } else if (selectedTab.equals(buttons)) {
                textArea.setValue("""
                        Buttons: \n
                        "One step" -    run one machine step (got trough all rules and execute once)\s
                        "Start" -       run machine steps infinity times until exit rule executed (! symbol)\s
                        "Load data" -   Load data from "Input string" field, starting from current head position\s
                        "Reset" -       reset head state and position to default\s
                                        """);
            } else if (selectedTab.equals(fields)) {
                textArea.setValue("""
                        Fields: \n
                        "Cell count" -   set tape length\s
                        "Current head    state" - represent current head state, also allow change it\s
                        "Tape cells" -   Scrollable tape with cells, each cell allow write any symbol\s
                        "Input string" - Fields where you could define data written by symbol, when click "Load button"\s
                        "Turing code" -  Fields where you could define data written by symbol, when click "Load button"\s
                                                
                        Table witch rules - show last rule used and all rules that processed
                                        """);
            } else if (selectedTab.equals(code)) {
                textArea.setValue("""
                        Code: \n
                        q0/a=b q1 >; \n
                        "q0" Statement when executed this rule (any char + number )\s
                        "a"  Symbol when executed this rule\s
                        "="  after this you could define steps that machine should perform\s
                             in any order, in any counts ("b >","b" "q1 b")\s
                                * "> | <"  right stay left\s
                                * "b"      what symbol to write\s
                                * "q1"     new state\s                    
                                        """);
            } else if (selectedTab.equals(quickGuide)) {
                textArea.setValue("""
                        Quick guide
                                                
                                                
                        1) Define max "Cell count" and select start position by clicking on cell or stay default (tape middle)\s      
                        2) Write data manually in each cell or use "Input string" and "Load data" button\s
                        3) Define initial state in "Current head state" filed or stay default\s
                        4) Write turing code according the rules\s       
                        5) Press one step to run one step forward or "Start" to execute to the end
                                                
                                                
                        If some one doesn't work try to use Step button and check rule table\s
                                                
                                        """);
            }

        });
        tabs.setSelectedTab(welcome);
        fireEvent(new ClickEvent<>(welcome));

        VerticalLayout verticalLayout = new VerticalLayout(textArea);
        verticalLayout.setPadding(true);

        dialog.add(tabs, verticalLayout);

        aboutButton.addClickListener(event -> dialog.open());

        Image image = new Image("/logo.gif", "Logo.gif");
        image.setHeight("10em");
        horizontalLayout.add(image, aboutButton, dialog);


        addRoundBorder(horizontalLayout);
        return horizontalLayout;
    }


    public boolean stepTuringMachine(List<Command> commands) {
        for (Command command : commands) {
            TapTile tapTile = tapTiles.get(currentHeadPosition);
            String currentHeadState = stateHeadState.getValue();
            if (command.getIfState().equals(currentHeadState) && tapTile.getValue().equals(command.getIfSymbol())) {

                commandListView.select(command);

                if (command.isEndCommand()) {
                    return false;
                }

                if (StringUtils.isNotBlank(command.getWhatToWrite())) {
                    tapTile.setValue(command.getWhatToWrite());
                }
                if (Objects.nonNull(command.getNewState())) {
                    stateHeadState.setValue(command.getNewState());
                }
                if (Objects.nonNull(command.getMovementDirection())) {
                    switch (command.getMovementDirection()) {
                        case LEFT -> {
                            currentHeadPosition = currentHeadPosition - 1;
                            resetSelectedCellBorder(tapTile);
                        }
                        case RIGHT -> {
                            currentHeadPosition = currentHeadPosition + 1;
                            resetSelectedCellBorder(tapTile);
                        }
                    }
                }
                break;
            }
        }
        return true;
    }

    @SneakyThrows
    public void recursiveStepTuringMachine(List<Command> commands) {
        if (continueExecuting) {
            continueExecuting = stepTuringMachine(commands);
            recursiveStepTuringMachine(commands);
        }
    }

    private void resetSelectedCellBorder(TapTile oldTile) {
        Optional.ofNullable(oldTile).ifPresent(tapTile -> oldTile.removeBorder());
        TapTile tapTile = tapTiles.get(currentHeadPosition);
        tapTile.addBorder();
    }

    private void refreshCommandDataProvider(Collection<Command> commands) {
        Collection<Command> items = commandsDataProvider.getItems();
        items.removeAll(items);
        items.addAll(commands);
        commandsDataProvider.refreshAll();
    }

    private void resetState() {
        tapTiles.forEach(tapTile -> tapTile.setValue(""));
        int center = tapTiles.size() / 2;
        tapTiles.get(center).focus();
        stateHeadState.setValue("q0");
    }

    private void addRoundBorder(Component component) {
        component.getElement().getStyle().set("border", "3px solid LightSlateGray");
        component.getElement().getStyle().set("border-radius", "30px");
    }
}
