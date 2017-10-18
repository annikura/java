package ru.spbau.annikura.spiral;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordinateTest {
    @Test
    public void createInstance() {
        Coordinate point = new Coordinate(5, 7);
    }

    @Test
    public void getX() {
        Coordinate point = new Coordinate(5, 7);
        assertEquals(5, point.getX());
        point.add(new Coordinate(3, 0));
        assertEquals(8, point.getX());
    }

    @Test
    public void getY() {
        Coordinate point = new Coordinate(5, 7);
        assertEquals(7, point.getY());
        point.add(new Coordinate(4, 5));
        assertEquals(12, point.getY());
    }

    @Test
    public void inSquare() {
        Coordinate point = new Coordinate(0, 0);
        assertEquals(true, point.inSquare(-3, -4, 3, 1));
        assertEquals(true, point.inSquare(0, -1, 3, 3));
        assertEquals(true, point.inSquare(-5, 0, 3, 3));
        assertEquals(false, point.inSquare(-9, -1, 0, 0));
        assertEquals(false, point.inSquare(5, 5, 0, 0));
        assertEquals(false, point.inSquare(0, 5, 0, 0));
    }

    @Test
    public void add() {
        Coordinate point = new Coordinate(2, 1);
        point.add(new Coordinate(7, 4));
        assertEquals(9, point.getX());
        assertEquals(5, point.getY());
        point.add(new Coordinate(-1, -2));
        point.add(new Coordinate(0, 0));
        assertEquals(8, point.getX());
        assertEquals(3, point.getY());
    }

    @Test
    public void turn90DegLeftSimple() {
        Coordinate point = new Coordinate(0, 1);
        point.turn90DegLeft();
        assertEquals(-1, point.getX());
        assertEquals(0, point.getY());
        point.turn90DegLeft();
        assertEquals(0, point.getX());
        assertEquals(-1, point.getY());
        point.turn90DegLeft();
        assertEquals(1, point.getX());
        assertEquals(0, point.getY());
        point.turn90DegLeft();
        assertEquals(0, point.getX());
        assertEquals(1, point.getY());
    }

    @Test
    public void turn90DegLeft() {
        Coordinate point = new Coordinate(2, 3);
        point.turn90DegLeft();
        assertEquals(-3, point.getX());
        assertEquals(2, point.getY());
        point.turn90DegLeft();
        assertEquals(-2, point.getX());
        assertEquals(-3, point.getY());
        point.turn90DegLeft();
        assertEquals(3, point.getX());
        assertEquals(-2, point.getY());
        point.turn90DegLeft();
        assertEquals(2, point.getX());
        assertEquals(3, point.getY());
    }
}
