import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.awt.Color;
import java.util.Set;
import java.util.*;

public class ElectoralMap {

    static HashMap<String, HashMap<String, ArrayList<Subregion>>> map = new HashMap<>();
    static int pointsNum;

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


            if(map.containsKey(subObject.region)) {
                HashMap<String, ArrayList<Subregion>> tempOuterMap = map.get(subObject.region);

                ArrayList<Subregion> tempInnerArrayList = tempOuterMap.get(subObject.name);

                if(tempInnerArrayList == null){
                    ArrayList<Subregion> newSubList = new ArrayList<>();
                    newSubList.add(subObject);
                    tempInnerArrayList = newSubList;
                    map.get(subObject.region).put(subObject.name, tempInnerArrayList);
                }

                else
                tempInnerArrayList.add(subObject);
            }

            else{
                holdSubs.add(subObject);
                HashMap<String, ArrayList<Subregion>> innermap = new HashMap<>();
                innermap.put(subObject.name, holdSubs);
                map.put(subObject.region, innermap);
            }



            inputRegion.nextLine();



        }


        }


    public static void getVotes(String region, String year) throws FileNotFoundException{
        for(String reg: map.keySet()){
            File inputFile = new File("input/" + reg + year + ".txt");
            Scanner inputObject = new Scanner(inputFile);
            HashMap<String, ArrayList<Subregion>> currentHashMap = map.get(reg);

            inputObject.nextLine();

            while(inputObject.hasNextLine()){
                String[] holder = inputObject.nextLine().split(",");
                ArrayList<Subregion> currentSub = new ArrayList<>();

                if(currentHashMap.get(holder[0]) == null && reg.equals("VA"))
                  currentSub = currentHashMap.get(holder[0] + " city");

                else if(currentHashMap.get(holder[0]) == null && reg.equals("LA"))
                    currentSub = currentHashMap.get(holder[0] + " Parish");

                else
                    currentSub = currentHashMap.get(holder[0]);

                int repVotes = Integer.parseInt(holder[1]);
                int demVotes = Integer.parseInt(holder[2]);
                int indVotes = Integer.parseInt(holder[3]);

                for(int i = 0; i < currentSub.size(); i++){

                    if (repVotes > demVotes && repVotes > indVotes)
                        currentSub.get(i).setColor(Color.RED);

                    else if (demVotes > repVotes && demVotes > indVotes)
                        currentSub.get(i).setColor(Color.BLUE);

                    else
                        currentSub.get(i).setColor(Color.LIGHT_GRAY);
                }


            }
        }
    }




    public static void draw(){
        for(String k: map.keySet()){
            HashMap<String, ArrayList<Subregion>> innermap = map.get(k);

            for(String reg: innermap.keySet()){
            ArrayList<Subregion> holdSubs = innermap.get(reg);

            for(int i = 0; i < holdSubs.size(); i++) {
                Subregion currentR = holdSubs.get(i);
                System.out.println("Region: " + currentR.region + " SubRegion: " + currentR.name + " Color: " + currentR.color);
                StdDraw.setPenColor(currentR.color);
                StdDraw.filledPolygon(holdSubs.get(i).xCoor, holdSubs.get(i).yCoor);
            }
            }
        }

    }




    public static void main(String[] args) throws FileNotFoundException{
        visualize("USA-county", "2016");
    }
    }




