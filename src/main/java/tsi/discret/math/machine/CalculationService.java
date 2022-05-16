package tsi.discret.math.machine;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CalculationService {

    private static final HashMap<String, Object> STRING_OBJECT_HASH_MAP = new HashMap<>();

    //q2/c=a q3 >;

    // _ empty symbol or empty state
    // ! tape end
    // <state>/<alphabet_symbol>=<new_symbol> <new_state> <direction>;

    //TODO make scrub symbol after not by index for [b >] [q1 <]
//    public List<Command> processCodeToCommands(String code) {
//        String cleaned = code.replace("\n", "");
//        return Arrays.stream(cleaned.split(";")).map(s -> {
//            String ifState = StringUtils.substringBefore(s, "=")
//                    .replace(" ", "")
//                    .split("/")[0]; // q2
//            String ifSymbol = StringUtils.substringBefore(s, "=")
//                    .replace(" ", ""
//                    ).split("/")[1]; // c
//
//            if (ifSymbol.equals("_")) {
//                ifSymbol = "";
//            }
//
//
//            String equation = StringUtils.substringAfter(s, "=");
//            boolean isEnd = equation.contains("!");
//
//            if (isEnd) {
//                return new Command(ifState, ifSymbol, "", "", null, true);
//            }
//
//            String[] s1 = equation.split(" ");
//            String writeData = s1[0];//a
//            String newState = s1[1];//q3
//            MovementDirection direction = MovementDirection.convert(s1[2].charAt(0)); // >
//
//
//            ////
//
//            return new Command(ifState, ifSymbol, writeData, newState, direction, false);
//        }).collect(Collectors.toList());
//    }

    public List<Command> processCodeToCommands(String code) {
        String cleaned = code.replace("\n", "");
        return Arrays.stream(cleaned.split(";")).map(s -> {
            String ifState = null;
            String ifSymbol = null;
            AtomicReference<String> writeData = new AtomicReference<>(null);
            AtomicReference<String> newState = new AtomicReference<>(null);
            AtomicReference<MovementDirection> direction = new AtomicReference<>(null);
            AtomicReference<Boolean> isEnd = new AtomicReference<>(false);


            String ifString = StringUtils.substringBefore(s, "=").replace(" ", "");
            String whatToDo = StringUtils.substringAfter(s, "=");

            ifState = StringUtils.substringBefore(ifString, "/");
            ifSymbol = StringUtils.substringAfter(ifString, "/");

            Arrays.stream(whatToDo.split(" "))
                    .map(s1 -> s1.replace(" ", ""))
                    .forEach(piece -> {
                        if (piece.length() == 1 && piece.matches("\\w")) {
                            writeData.set(piece);
                        }

                        if (piece.length() == 2 && piece.matches("[a-z]\\d")) {
                            newState.set(piece);
                        }
                        if (piece.matches(">|<|\\|")) {
                            direction.set(MovementDirection.convert(piece.charAt(0)));
                        }

                        if (piece.matches("!")) {
                            isEnd.set(true);
                        }

                    });


            return new Command(ifState, ifSymbol, writeData.get(), newState.get(), direction.get(), isEnd.get());
        }).collect(Collectors.toList());
    }

}
