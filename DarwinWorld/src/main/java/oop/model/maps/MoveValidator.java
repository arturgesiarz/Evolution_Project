package oop.model.maps;

import oop.model.Vector2d;

public interface MoveValidator {
    /**
     * Function checks if there is a pole in a given position
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if is a Pole, False if isn't
     */
    boolean isPole(Vector2d position);

    /**
     * the function returns the refreshed value of the vector if there is
     * either a hole or the left/right end of the map at that location,
     * if none of these things, it just leaves the vector the same
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return Vector of new position
     */
    Vector2d teleporation(Vector2d position);

}
