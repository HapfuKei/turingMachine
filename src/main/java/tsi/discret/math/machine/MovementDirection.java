package tsi.discret.math.machine;

public enum MovementDirection {
    LEFT, RIGHT, STAY;

    public static MovementDirection convert(char s) {
        if (s == '<') {
            return LEFT;
        } else if (s == '>') {
            return RIGHT;
        } else if (s == '|') {
            return STAY;
        } else {
            throw new IllegalArgumentException("Not supported direction");
        }
    }
}
