import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class EmptyMap {

    public static void visualize(String region) throws FileNotFoundException {
        File inputFile = new File("./src/input/" + region + ".txt");
        Scanner inputObject = new Scanner(inputFile);

        String minCoordinates = inputObject.nextLine();
        String maxCoordinates = inputObject.nextLine();
        double[] doubleMinCoordinates = new double[2];
        double[] doubleMaxCoordinates = new double[2];

        String[] holdFirst = minCoordinates.split("   ");
        String[] holdSecond = maxCoordinates.split("   ");
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
        inputObject.nextLine();
        double pointsNum = Double.parseDouble(inputObject.nextLine());
        double[] xCoor = new double[(int)pointsNum];
        double[] yCoor = new double[(int)pointsNum];

        for (int runup = 0; runup < pointsNum && inputObject.hasNextLine(); runup++) {
            xCoor[runup] = inputObject.nextDouble();
            yCoor[runup] = inputObject.nextDouble();
            System.out.println("x-coordinates: "+ xCoor[runup] + " y-coordinate: "+yCoor[runup]);

            if (runup == pointsNum - 1) {
                StdDraw.polygon(xCoor, yCoor);
                System.out.println(inputObject.nextLine());
                System.out.println(inputObject.nextLine());
                System.out.println(inputObject.nextLine());
                System.out.println(inputObject.nextLine());
                pointsNum = Double.parseDouble(inputObject.nextLine());
                xCoor = new double[(int)pointsNum];
                yCoor = new double[(int)pointsNum];
                runup = 0;
            }
        }

        inputObject.close();
        StdDraw.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        visualize("FL");
    }
}



