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

    public static MapDirection fromValue(int value) {
        for (MapDirection direction : MapDirection.values()) {
            if (direction.getValue() == value) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Nieprawidłowa wartość kierunku: " + value);
    }

    public Vector2d toUnitVector(){
        return switch(this){
            case NORTH      -> new Vector2d(0,1);
            case NORTH_EAST -> new Vector2d(1,1);
            case EAST       -> new Vector2d(1,0);
            case SOUTH_EAST -> new Vector2d(1,-1);
            case SOUTH      -> new Vector2d(0,-1);
            case SOUTH_WEST -> new Vector2d(-1,-1);
            case WEST       -> new Vector2d(-1,0);
            case NORTH_WEST -> new Vector2d(-1,1);
        };
    }
    public MapDirection getOpositeDirection(){
        return switch(this){
            case NORTH      -> SOUTH;
            case NORTH_EAST -> SOUTH_WEST;
            case EAST       -> WEST;
            case SOUTH_EAST -> NORTH_EAST;
            case SOUTH      -> NORTH;
            case SOUTH_WEST -> NORTH_WEST;
            case WEST       -> EAST;
            case NORTH_WEST -> SOUTH_EAST;
        };
    }

    @Override
    public String toString(){
        return switch(this){
            case NORTH      -> "Północ";
            case NORTH_EAST -> "Północny-Wschód";
            case EAST       -> "Wschód";
            case SOUTH_EAST -> "Południowy-Wschód";
            case SOUTH      -> "Południe";
            case SOUTH_WEST -> "Południowy-Zachód";
            case WEST       -> "Zachód";
            case NORTH_WEST -> "Północny-Zachód";
        };
    }
}
