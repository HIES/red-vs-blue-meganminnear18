import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.awt.Color;

public class ElectoralMap {

    private static HashMap<String, ArrayList<Subregion>> map = new HashMap<>();

    private static class Subregion {
        String name;
        int[] votes;
        double[] xCoor;
        double[] yCoor;
        Color color;


        private Subregion(double[] x, double[] y) {
            name = null;
            xCoor = x;
            yCoor = y;
        }

        private void setVotes(int rep, int dem, int ind){
            votes[1] = rep;
            votes[2] = dem;
            votes[3] = ind;
        }

        private void setColor(Color setCol){
            color = setCol;
        }

            }



// Reads the region input file and then creates Subregion objects based on # of Subregions in file
// Assigns the Subregions names from the String[] names
// Then calls on getXYCoor to determine the X and Y Coor for each Subregion created
// getXYCoor draws Subregion
// calls on getVotes to assign SubRegion votes --> getVotes returns the greatest of the 3 and sets Color based off those

    public static void visualize(String region) throws FileNotFoundException {
        File inputFile = new File("input/" + region + ".txt");
        File inputFile2 = new File("input/" + region + ".txt");
        Scanner inputYear = new Scanner(inputFile2);

        Scanner inputRegion = new Scanner(inputFile);

        String minCoordinates = inputRegion.nextLine();
        String maxCoordinates = inputRegion.nextLine();

        String[] holdFirst = minCoordinates.split("   ");
        String[] holdSecond = maxCoordinates.split("   ");
        double[] doubleMinCoordinates = new double[2];
        double[] doubleMaxCoordinates = new double[2];

        for (int i = 0; i < 2; i++) {
            doubleMinCoordinates[i] = Double.parseDouble(holdFirst[i]);
            doubleMaxCoordinates[i] = Double.parseDouble(holdSecond[i]);
        }
        int xSize = (int) (doubleMaxCoordinates[0] - doubleMinCoordinates[0]);
        int ySize = (int) (doubleMaxCoordinates[1] - doubleMinCoordinates[1]);

        StdDraw.setCanvasSize(512 * (xSize / ySize), 512);
        StdDraw.setXscale(doubleMinCoordinates[0], doubleMaxCoordinates[0]);
        StdDraw.setYscale(doubleMinCoordinates[1], doubleMaxCoordinates[1]);



        int subRegions = Integer.parseInt(inputRegion.nextLine());
        String[] subNames = new String[subRegions];
        inputRegion.nextLine();


        ArrayList<Subregion> holdSubs = new ArrayList<>();

        String[] reg = new String[subRegions];
        for(int current = 0; current < subRegions; current++)
        {
            subNames[current] = inputRegion.nextLine();
            reg[current] = inputRegion.nextLine();
            int pointsNum = Integer.parseInt(inputRegion.nextLine());
            double[] xCoor = new double[pointsNum];
            double[] yCoor = new double[pointsNum];

            for(int point = 0; point < pointsNum; point++){
                xCoor[point] = inputRegion.nextDouble();
                yCoor[point] = inputRegion.nextDouble();
                inputRegion.nextLine();
            }
            Subregion subObject = new Subregion(xCoor, yCoor);
            subObject.name = subNames[current];
            holdSubs.add(subObject);

            inputRegion.nextLine();

            StdDraw.polygon(xCoor, yCoor);

        }

        map.put(reg[0], holdSubs);

        inputRegion.close();

        System.out.println("size = " + map.size());
    }


    public static void main(String[] args) throws FileNotFoundException{
        visualize("GA");
        System.out.println("keySet is : \n" + map.keySet());
    }
    }




