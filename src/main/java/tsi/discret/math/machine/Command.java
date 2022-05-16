package tsi.discret.math.machine;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Command {
    String ifState;
    String ifSymbol;
    String whatToWrite;
    String newState;
    MovementDirection movementDirection;
    boolean endCommand;

    public String getIfSymbol() {
        if (Objects.nonNull(whatToWrite) && ifSymbol.equals("_")) return "";
        return ifSymbol;
    }

    public String getWhatToWrite() {
        if (Objects.nonNull(whatToWrite) && whatToWrite.equals("_")) return "";
        return whatToWrite;
    }
}
