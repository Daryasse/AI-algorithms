import java.util.ArrayList;

public class Cluster {
    public ArrayList<Double> centroids = new ArrayList<>();;
    public String objectName;

    public Cluster(Object object){
//        int size = object.getParameters().size();
//        for (int i = 0; i<size; i++){
//            centroids.add((Math.random() * 3) + 2);
//        }
        centroids = object.getParameters();
        objectName = object.getObjectName();
    }

    public double distanceCalculator(Object object){
        double distance = 0;
        if (centroids.size() == object.getParameters().size()) {
            int size = object.getParameters().size();
            for (int i = 0; i < size; i++) {
                double distanceDif = Double.valueOf(centroids.get(i) - object.getParameters().get(i));
                double distance1 = Math.pow(distanceDif, 2);
                distance += distance1;
            }
        }
        return Math.sqrt(distance);
    }

    public void updateCentroids(ArrayList<Object> objects){


        for (int i = 0; i< centroids.size(); i++){
            double center = 0;
            int y = 0;
            for (int j = 0; j<objects.size(); j++) {
                center = center + objects.get(j).getParameters().get(i);
                y++;
            }
            int number = objects.size();
            centroids.set(i, center/y);
        }
    }

    public String getObjectName() {
        return objectName;
    }
}
