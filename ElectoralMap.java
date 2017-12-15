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
        String region;
        int[] votes = new int[3];
        double[] xCoor;
        double[] yCoor;
        Color color;


        private Subregion() {

        }

        private void setXYCoor(double[] x, double[] y){
            xCoor = x;
            yCoor = y;
        }

        private void setVotes(int rep, int dem, int ind){
            votes[0] = rep;
            votes[1] = dem;
            votes[2] = ind;
        }

        private void setColor(Color setCol){
            color = setCol;
            StdDraw.setPenColor(color);
        }

            }



// Reads the region input file and then creates Subregion objects based on # of Subregions in file
// Assigns the Subregions names from the String[] names
// Then calls on getXYCoor to determine the X and Y Coor for each Subregion created
// getXYCoor draws Subregion
// calls on getVotes to assign SubRegion votes --> getVotes returns the greatest of the 3 and sets Color based off those

    public static void visualize(String region, String year) throws FileNotFoundException {
        getGeoData(region);
        getVotes(region, year);

    }

    public static void getGeoData(String region) throws FileNotFoundException {
        File inputFile = new File("input/" + region + ".txt");
        File inputFile2 = new File("input/" + region + ".txt");

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
        inputRegion.nextLine();



        String[] reg = new String[subRegions];
        for(int current = 0; current < subRegions; current++)
        {
            ArrayList<Subregion> holdSubs = new ArrayList<>();
            Subregion subObject = new Subregion();
            subObject.name = inputRegion.nextLine();
            subObject.region = inputRegion.nextLine();

            int pointsNum = Integer.parseInt(inputRegion.nextLine());
            double[] xCoor = new double[pointsNum];
            double[] yCoor = new double[pointsNum];

            for(int point = 0; point < pointsNum; point++){
                xCoor[point] = inputRegion.nextDouble();
                yCoor[point] = inputRegion.nextDouble();
                inputRegion.nextLine();
            }
            subObject.setXYCoor(xCoor, yCoor);
            if(map.containsKey(subObject.name))
                map.get(subObject.name).add(subObject);

            else{
                holdSubs.add(subObject);
                map.put(subObject.name, holdSubs);
            }



            inputRegion.nextLine();



        }

            //map.put(map2.put(reg[0], holdSubs);

            //make new scanner object that opens up a specific voting year of the region
            //reads a line and assigns the subregion the corresponding 3 different vote counts

        }



    public static void getVotes(String region, String year) throws FileNotFoundException{
        File inputFile = new File("input/" + region + year + ".txt");
        Scanner yearObject = new Scanner(inputFile);

        yearObject.nextLine();

        while(yearObject.hasNextLine()) {
            String[] holder = yearObject.nextLine().split(",");
            ArrayList<Subregion> currentArray = map.get(holder[0]);
            int repVotes = Integer.parseInt(holder[1]);
            int demVotes = Integer.parseInt(holder[2]);
            int indVotes = Integer.parseInt(holder[3]);
            int i = 0;
            Subregion currentR = currentArray.get(i);

                if(repVotes > demVotes && repVotes > indVotes) {
                currentR.setColor(Color.RED);
            }
            else if(demVotes > repVotes && demVotes > indVotes) {
                currentR.setColor(Color.BLUE);
            }
            else if(indVotes > demVotes && indVotes > repVotes) {
                currentR.setColor(Color.GRAY);
                }


            if(currentArray.size() > 1){
            while(i < currentArray.size()){
                StdDraw.filledPolygon(currentR.xCoor, currentR.yCoor);
                i++;
                }
            }

            else{
                StdDraw.filledPolygon(currentR.xCoor, currentR.yCoor);
            }


            }

        yearObject.close();

        }





    public static void main(String[] args) throws FileNotFoundException{
        visualize("GA", "2012");
    }
    }




