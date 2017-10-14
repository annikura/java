import ru.spbau.annikura.spiral.SpiralTest;
import ru.spbau.annikura.spiral.CoordinateTest;

public class RunTests {
    public static void main (String[] args){
        CoordinateTest.createInstance();
        CoordinateTest.getX();
        CoordinateTest.getY();
        CoordinateTest.add();
        CoordinateTest.inSquare();
        CoordinateTest.turn90DegLeftSimple();
        CoordinateTest.turn90DegLeft();

        System.out.println("Start testing printUnspirialized:");
        SpiralTest.printUnspiralized();
        System.out.println("End testing printUnspirialized.");

        SpiralTest.unspiralizeIntoListSimple1x1();
        SpiralTest.unspiralizeIntoListSimple3x3();
        SpiralTest.unspiralizeIntoList3x3();
        SpiralTest.sortMatrix1x3();
        SpiralTest.sortMatrix3x3();
        SpiralTest.sortMatrix1x0();

        System.out.println("OK. All tests passed.");
    }
}
