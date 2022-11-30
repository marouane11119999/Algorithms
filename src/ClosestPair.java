import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.*;

public class ClosestPair {
    ArrayList<Point> myPointsx=new ArrayList<>();
    ArrayList<Point> myPointsy=new ArrayList<>();
    Point[] myPointsArraySortedx;
    Point[] myPointsArraySortedy;

    public  void addPointsfromFile(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st; int x=0;
        while ((st = br.readLine()) != null){
            String[] splittedString=st.split(" ");
            Point ob= new Point(Float.parseFloat(splittedString[0]), Float.parseFloat(splittedString[1]));
            myPointsx.add(ob);
            myPointsy.add(ob);
        }
        myPointsx.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Float.compare(o1.x, o2.x);
            }
        });
        myPointsy.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Float.compare(o1.y,o2.y);
            }
        });
    }

    public void setToArray(){
        int i=0,j=0;
        myPointsArraySortedx=new Point[myPointsx.size()];
        myPointsArraySortedy=new Point[myPointsx.size()];
        for (Point p: myPointsx) {
            myPointsArraySortedx[i++]=p;
        }
        for (Point p:myPointsy) {
            myPointsArraySortedy[j++]=p;
        }

    }

    public float recurse(int l,int r){
       return closestPair(myPointsArraySortedx,myPointsArraySortedy,l,r);
    }

    public float recurseNew(int l,int r){
        return closestPairNew(myPointsArraySortedx,myPointsArraySortedy,l,r);
    }

    private float closestPairNew(Point[] PointsX, Point[] PointsY, int l, int r) {
        if (PointsX.length<=3) return calculate(PointsX);
        else{
            //construnction of Sets
            int m=PointsX.length/2;

            Point[] Qx=new Point[m];
            Point[] Qy=new Point[m];
            Point[] Rx=new Point[PointsX.length-m];
            Point[] Ry=new Point[PointsY.length-m];
            for (int i = 0; i < m; i++) {
                Qx[i]=PointsX[i];
                Qy[i]=PointsY[i];

            }

            for (int i = m; i < PointsX.length; i++) {
                Rx[i-m]=PointsX[i];
                Ry[i-m]=PointsY[i];
            }

            //Recursively calculating
            float leftD=closestPair(Qx,Qy,l,m);
            float rightD=closestPair(Rx,Ry,m+1,r);
            float minD=Math.min(leftD,rightD);

            //construction of Sy
            ArrayList<Point> Sy=new ArrayList<>();
            float left = PointsX[m].x - minD;
            float right = PointsX[m].x + minD;
            float result=minD;
            for (int i = 0; i<PointsY.length; i++){
                if (left < PointsY[i].x && PointsY[i].x < right){
                    Sy.add(PointsY[i]);
                }
            }

            for (int i = 0; i < Sy.size()-1; i++) {
                for (int j = i+1; j < Math.min(Sy.size(),i+11); j++) {
                    if (Math.abs(Sy.get(i).y-Sy.get(j).y)>minD) break;
                    if (Point.euclideanDistance(Sy.get(i),Sy.get(j))<result) result=Point.euclideanDistance(Sy.get(i),Sy.get(j));
                }
            }
            return result;
        }
    }

    public float recurseLR(int l, int r){
        return closestPairLR(myPointsArraySortedx,myPointsArraySortedy,l,r);
    }

    private float closestPairLR(Point[] PointsX, Point[] PointsY, int l, int r) {
        if (PointsX.length<=3) return calculate(PointsX);
        else{
            //construnction of Sets
            int m=PointsX.length/2;

            Point[] Qx=new Point[m];
            Point[] Qy=new Point[m];
            Point[] Rx=new Point[PointsX.length-m];
            Point[] Ry=new Point[PointsY.length-m];
            for (int i = 0; i < m; i++) {
                Qx[i]=PointsX[i];
                Qy[i]=PointsY[i];

            }

            for (int i = m; i < PointsX.length; i++) {
                Rx[i-m]=PointsX[i];
                Ry[i-m]=PointsY[i];
            }

            //Recursively calculating
            float leftD=closestPair(Qx,Qy,l,m);
            float rightD=closestPair(Rx,Ry,m+1,r);
            float minD=Math.min(leftD,rightD);

            //construction of Sy
            ArrayList<Point> SyL=new ArrayList<>();
            ArrayList<Point> SyR=new ArrayList<>();
            float left = PointsX[m].x - minD;
            float right = PointsX[m].x + minD;
            float result=minD;
            for (int i = 0; i<PointsY.length; i++){
                if (left < PointsY[i].x && PointsY[i].x > right){
                    SyL.add(PointsY[i]);
                }
            }
            for (int i = 0; i<PointsY.length; i++){
                if (left > PointsY[i].x && PointsY[i].x > right){
                    SyR.add(PointsY[i]);
                }
            }

            for (int i = 0; i < SyL.size()-1; i++) {
                for (int j = i+1; j < Math.min(SyR.size(),i+6); j++) {
                    if (Point.euclideanDistance(SyL.get(i),SyR.get(j))<result) result=Point.euclideanDistance(SyL.get(i),SyR.get(j));
                }
            }
            return result;
        }
    }


    public static float calculate(Point[] P) {
        float min = Float.MAX_VALUE;
        float currMin = 0;

        for (int i = 0; i < P.length; i++) {
            for (int j = i + 1; j < P.length; j++) {
                currMin = Point.euclideanDistance(P[i], P[j]);
                if (currMin < min) {
                    min = currMin;
                }
            }
        }
        return min;
    }
    private float closestPair(Point[] PointsX, Point[] PointsY,int l, int r) {
       if (PointsX.length<=3) return calculate(PointsX);
       else{
           //construnction of Sets
           int m=PointsX.length/2;

           Point[] Qx=new Point[m];
           Point[] Qy=new Point[m];
           Point[] Rx=new Point[PointsX.length-m];
           Point[] Ry=new Point[PointsY.length-m];
           for (int i = 0; i < m; i++) {
               Qx[i]=PointsX[i];
               Qy[i]=PointsY[i];

           }

           for (int i = m; i < PointsX.length; i++) {
               Rx[i-m]=PointsX[i];
               Ry[i-m]=PointsY[i];
           }

           //Recursively calculating
           float leftD=closestPair(Qx,Qy,l,m);
           float rightD=closestPair(Rx,Ry,m+1,r);
           float minD=Math.min(leftD,rightD);

           //construction of Sy
           ArrayList<Point> Sy=new ArrayList<>();
           float left = PointsX[m].x - minD;
           float right = PointsX[m].x + minD;
           float result=minD;
           for (int i = 0; i<PointsY.length; i++){
               if (left < PointsY[i].x && PointsY[i].x < right){
                   Sy.add(PointsY[i]);
               }
           }

           for (int i = 0; i < Sy.size()-1; i++) {
               for (int j = i+1; j < Math.min(Sy.size(),i+11); j++) {
                    if (Point.euclideanDistance(Sy.get(i),Sy.get(j))<result) result=Point.euclideanDistance(Sy.get(i),Sy.get(j));
               }
           }
           return result;
       }

    }
    public static void main(String[] args) throws IOException {
        ClosestPair ob=new ClosestPair();
        ob.addPointsfromFile("src/Daten/100000points.txt");
        ob.setToArray();
        System.out.println(Math.sqrt(ob.recurseLR(0,ob.myPointsArraySortedx.length)));



    }
}




