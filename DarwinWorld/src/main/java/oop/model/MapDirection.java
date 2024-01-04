package oop.model;

public enum MapDirection {
    NORTH(0),
    NORTH_EAST(1),
    EAST(2),
    SOUTH_EAST(3),
    SOUTH(4),
    SOUTH_WEST(5),
    WEST(6),
    NORTH_WEST(7);
    private final int turnNumber;
    MapDirection (int turnNumber) {
        this.turnNumber = turnNumber;
    }
    public int getValue() {
        return turnNumber;
    }
    @Override
    public String toString(){
        return switch(this){
            case NORTH -> "Północ";
            case NORTH_EAST -> "Północny-Wschód";
            case EAST -> "Wschód";
            case SOUTH_EAST -> "Południowy-Wschód";
            case SOUTH -> "Południe";
            case SOUTH_WEST -> "Południowy-Zachód";
            case WEST -> "Zachód";
            case NORTH_WEST -> "Północny-Zachód";
        };
    }
}
