import java.io.File;
import java.util.Scanner;

public class SquareNumbers {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Arguments are invalid. Input filename and output filename are expected.");
            return;
        }
        try (Scanner scanner = new Scanner(new File(args[0]))) {
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {

                }
            }
        } catch (Exception ex) {

        }
    }
}
