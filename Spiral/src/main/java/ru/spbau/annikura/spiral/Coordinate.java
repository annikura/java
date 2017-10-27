package ru.spbau.annikura.spiral;

/**
 * A simple class to store  x-y coordinates, turn vectors
 * stored in them or add coordinates to each other.
 */
public class Coordinate {
    private int x, y;

    /**
     * Constructs a coordinate pair (x, y).
     * @param x first coordinate
     * @param y second coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * X coordinate getter.
     * @return x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Y coordinate getter.
     * @return y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Checks whether the point lies in the given rectangular
     * (left and bottom sides are included, upper and right sides are not).
     * @param leftX is an x coordinate for the lower left corner of rectangular.
     * @param leftY is an y coordinate for the lower left corner of rectangular.
     * @param rightX is an x coordinate for the upper right corner of rectangular.
     * @param rightY is an y coordinate for the lower upper right of rectangular.
     * @return true if a point lies in the rectangular, false otherwise.
     */
    public boolean inSquare(int leftX, int leftY, int rightX, int rightY) {
        return x >= leftX && x < rightX && y >= leftY && y < rightY;
    }

    /**
     * Adds the given coordinate pair to the object coordinates.
     * @param other a coordinate pair which will be added.
     */
    public void add(final Coordinate other) {
        x += other.x;
        y += other.y;
    }

    /**
     * Turns the current coordinate pair on 90 degrees left relatively (0, 0) point.
     */
    public void turn90DegLeft() {
        int tmpX = x;
        x = -y;
        y = tmpX;
    }
}
