import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.awt.Color;
import java.util.Set;
import java.util.*;

public class BlendedAmerica {

    private static HashMap<String, ArrayList<Subregion>> map = new HashMap<>();
    private static int pointsNum;

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
        }

    }


    public static void visualize(String region, String year) throws FileNotFoundException {
        getGeoData(region);
        getVotes(region, year);
        draw();

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



        for(int current = 0; current < subRegions; current++)
        {
            ArrayList<Subregion> holdSubs = new ArrayList<>();
            Subregion subObject = new Subregion();
            subObject.name = inputRegion.nextLine();
            subObject.region = inputRegion.nextLine();

            pointsNum = Integer.parseInt(inputRegion.nextLine());
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


    }



    public static void getVotes(String region, String year) throws FileNotFoundException {
        File inputFile = new File("input/" + region + year + ".txt");
        Scanner yearObject = new Scanner(inputFile);

        yearObject.nextLine();

        while (yearObject.hasNextLine()) {
            String[] holder = yearObject.nextLine().split(",");
            ArrayList<Subregion> currentArray = map.get(holder[0]);

            for (int i = 0; i < currentArray.size(); i++) {
                int repVotes = Integer.parseInt(holder[1]);
                int demVotes = Integer.parseInt(holder[2]);
                int indVotes = Integer.parseInt(holder[3]);
                Subregion currentR = currentArray.get(i);
                currentR.setVotes(repVotes, demVotes, indVotes);
            }

        }
            yearObject.close();

        }

    public static void draw(){

        String[] holdKeys = new String[pointsNum];
        Set<String> key = map.keySet();
        System.out.println(key);
        holdKeys = key.toArray(holdKeys);

        int x = 0;
        while(!map.isEmpty()) {

            ArrayList<Subregion> currentArray = map.get(holdKeys[x]);

            for (int i = 0; i < currentArray.size(); i++) {
                Subregion currentR = currentArray.get(i);
                double sum = (double) (currentR.votes[0]+ currentR.votes[1] + currentR.votes[2]);
                int a1 = (int)((currentR.votes[0]*250)/sum);
                int a2 = (int)((currentR.votes[2]*250)/sum);
                int a3 = (int)((currentR.votes[1]*250)/sum);
                Color blend = new Color(a1, a2, a3);
                StdDraw.setPenColor(blend);
                StdDraw.filledPolygon(currentR.xCoor, currentR.yCoor);
            }
            map.remove(holdKeys[x]);
            x++;
        }
    }





    public static void main(String[] args) throws FileNotFoundException{
        visualize("GA", "2012");
    }
}




