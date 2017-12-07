import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class ElectoralMap {

    public static void visualize(String region) throws FileNotFoundException {
        File inputFile = new File(region + ".txt");
        Scanner inputObject = new Scanner(inputFile);

        String minCoordinates = inputObject.nextLine();
        String maxCoordinates = inputObject.nextLine();
        String[] holdFirst = new String[2];
        String[] holdSecond = new String[2];
        double[] doubleMinCoordinates = new double[2];
        double[] doubleMaxCoordinates = new double[2];

        holdFirst = minCoordinates.split(",");
        holdSecond = maxCoordinates.split(",");
        for (int i = 0; i < 2; i++) {
            doubleMinCoordinates[i] = Double.parseDouble(holdFirst[i]);
            doubleMaxCoordinates[i] = Double.parseDouble(holdSecond[i]);
        }
        int xSize = (int) (doubleMaxCoordinates[0] - doubleMinCoordinates[0]);
        int ySize = (int) (doubleMaxCoordinates[1] - doubleMinCoordinates[1]);

        StdDraw.setCanvasSize(512 * (xSize / ySize), 512);
        StdDraw.setXscale(0, xSize);
        StdDraw.setYscale(0, ySize);

        inputObject.nextLine();
        inputObject.nextLine();
        inputObject.nextLine();
        double pointsNum = Double.parseDouble(inputObject.nextLine());
        for (int runup = 0; runup < pointsNum && inputObject.hasNextLine(); runup++) {
            StdDraw.point(inputObject.nextDouble() - doubleMinCoordinates[0], inputObject.nextDouble() - doubleMinCoordinates[1]);
            if (runup == pointsNum - 1) {
                inputObject.nextLine();
                inputObject.nextLine();
                pointsNum = Double.parseDouble(inputObject.nextLine());
                runup = 0;
            }
        }
        String holder = inputObject.nextLine();


        inputObject.close();
        StdDraw.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        visualize("USA");
    }
    }



