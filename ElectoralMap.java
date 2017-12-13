import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.Color;

public class ElectoralMap {

    private class Subregion {
        String name;
        int[] votes;
        double[] xCoor;
        double[] yCoor;
        Color color;


        private Subregion() {

        }


        public void getXYCoor(Scanner inputObject) throws FileNotFoundException {

                inputObject.nextLine();
                inputObject.nextLine();
                int pointsNum = Integer.parseInt(inputObject.nextLine());

                for (int point = 0; point < pointsNum; point++) {
                    xCoor[point] = inputObject.nextDouble();
                    yCoor[point] = inputObject.nextDouble();
                    inputObject.nextLine();
                }
                StdDraw.polygon(xCoor, yCoor);

            }
        
        public void getVotes()
        }

// Reads the region input file and then creates Subregion objects based on # of Subregions in file
// Assigns the Subregions names from the String[] names
// Then calls on getXYCoor to determine the X and Y Coor for each Subregion created
// getXYCoor draws Subregion
// calls on getVotes to assign SubRegion votes --> getVotes returns the greatest of the 3 and sets Color based off those

    public void visualize(String region) throws FileNotFoundException {
        File inputFile = new File("input/" + region + ".txt");
        Scanner inputObject = new Scanner(inputFile);

        String minCoordinates = inputObject.nextLine();
        String maxCoordinates = inputObject.nextLine();

        int subRegions = Integer.parseInt(inputObject.nextLine());
        String[] subNames = new String[subRegions];
        inputObject.nextLine();

        for (int i = 0; i < subRegions; i++) {
            Subregion sub = new Subregion();
            sub.name = subNames[i];
            sub.getXYCoor(inputObject);

        }



    }

}

