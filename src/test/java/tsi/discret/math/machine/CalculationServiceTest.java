package tsi.discret.math.machine;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static tsi.discret.math.machine.MovementDirection.*;


class CalculationServiceTest {

    CalculationService calculationService = new CalculationService();

    @Test
    void processCodeToCommands() {
        String inputString = "q0/a=1 q0 >; q1/b=a q3 <; q4/_=!;";

        List<Command> result = calculationService.processCodeToCommands(inputString);

        assertThat(result.get(0).getIfState()).isEqualTo("q0");
        assertThat(result.get(0).getIfSymbol()).isEqualTo("a");
        assertThat(result.get(0).getWhatToWrite()).isEqualTo("1");
        assertThat(result.get(0).getNewState()).isEqualTo("q0");
        assertThat(result.get(0).getMovementDirection()).isEqualTo(RIGHT);
        assertThat(result.get(0).isEndCommand()).isEqualTo(false);

        assertThat(result.get(1).getIfState()).isEqualTo("q1");
        assertThat(result.get(1).getIfSymbol()).isEqualTo("b");
        assertThat(result.get(1).getWhatToWrite()).isEqualTo("a");
        assertThat(result.get(1).getNewState()).isEqualTo("q3");
        assertThat(result.get(1).getMovementDirection()).isEqualTo(LEFT);
        assertThat(result.get(1).isEndCommand()).isEqualTo(false);

        assertThat(result.get(2).getIfState()).isEqualTo("q4");
        assertThat(result.get(2).getIfSymbol()).isEqualTo("");
        assertThat(result.get(2).getWhatToWrite()).isEqualTo(null);
        assertThat(result.get(2).getNewState()).isNull();
        assertThat(result.get(2).getMovementDirection()).isNull();
        assertThat(result.get(2).isEndCommand()).isEqualTo(true);

    }

    @Test
    void processCodeToCommands2() {
        String inputString = "q0/a=b >; q1/b=g d3; q2/c=q3;";

        List<Command> result = calculationService.processCodeToCommands(inputString);
        assertThat(result.get(0).getIfState()).isEqualTo("q0");
        assertThat(result.get(0).getIfSymbol()).isEqualTo("a");
        assertThat(result.get(0).getWhatToWrite()).isEqualTo("b");
        assertThat(result.get(0).getNewState()).isNull();
        assertThat(result.get(0).getMovementDirection()).isEqualTo(RIGHT);
        assertThat(result.get(0).isEndCommand()).isEqualTo(false);

        assertThat(result.get(1).getIfState()).isEqualTo("q1");
        assertThat(result.get(1).getIfSymbol()).isEqualTo("b");
        assertThat(result.get(1).getWhatToWrite()).isEqualTo("g");
        assertThat(result.get(1).getNewState()).isEqualTo("d3");
        assertThat(result.get(1).getMovementDirection()).isNull();
        assertThat(result.get(1).isEndCommand()).isEqualTo(false);

        assertThat(result.get(2).getIfState()).isEqualTo("q2");
        assertThat(result.get(2).getIfSymbol()).isEqualTo("c");
        assertThat(result.get(2).getWhatToWrite()).isNull();
        assertThat(result.get(2).getNewState()).isEqualTo("q3");
        assertThat(result.get(2).getMovementDirection()).isNull();
        assertThat(result.get(2).isEndCommand()).isEqualTo(false);
    }
}