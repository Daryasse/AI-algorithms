import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static String fileTrain = "iris/train.txt";
    private static String fileTest = "iris/test.txt";
    private static final String double_regex = "([0-9]+([.][0-9]*)?|[.][0-9]+)";
    private static final String string_regex = "[A-Z][a-z]+[-][A-Z]?[a-z]+";
    public static ArrayList<Object> train = loadTrainingObjects();
    public static ArrayList<Object> test = loadTestingObjects();
    public static ArrayList<Cluster> clusters = new ArrayList<>();


    public static ArrayList<Object> loadTestingObjects() {
        ArrayList<Object> testObjects = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileTest));
            for (String line : lines) {
                String parts[] = line.split(",");
                ArrayList<Double> parameters = new ArrayList<>();
                String label = new String();
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].matches(double_regex) && i < parts.length - 1) {
                        parameters.add(Double.parseDouble(parts[i]));
                    }
                    if (parts[i].matches(string_regex) && i == parts.length - 1) {
                        label = parts[i];
                    }
                }
                testObjects.add(new Object(parameters, label));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testObjects;
    }

    public static ArrayList<Object> loadTrainingObjects() {
        ArrayList<Object> trainObjects = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileTrain));
            for (String line : lines) {
                String parts[] = line.split(",");
                ArrayList<Double> parameters = new ArrayList<>();
                String label = new String();
                if (!line.isEmpty()) {
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].matches(double_regex) && i < parts.length - 1) {
                            parameters.add(Double.parseDouble(parts[i]));
                        }
                        if (i == parts.length - 1 && parts[i].matches(string_regex)) {
                            label = parts[i];
                        }
                    }
                    trainObjects.add(new Object(parameters, label));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trainObjects;
    }

    public static ArrayList<Cluster> creatingClusters(int a) {
        ArrayList<Cluster> clusters = new ArrayList<>();
        int label = 0;
        for (int i = 0; i < a; i++) {
            Cluster cluster = new Cluster(new Object(train.get(i).getParameters(), String.valueOf(label)));
            clusters.add(cluster);
            label++;
        }
        return clusters;
    }

    public static String trainAlgorithm() {
        ArrayList<ClusterDistances> clusterDistancesList = new ArrayList<>();
        String result ="";
        double sum = 0;
        for (int j = 0; j < train.size(); j++) {
            ArrayList<ClusterDistances> list = new ArrayList<>();
            for (Cluster cluster : clusters) {
                double distance = cluster.distanceCalculator(train.get(j));
                list.add(new ClusterDistances(cluster, train.get(j), distance));
            }
            Collections.sort(list, Comparator.comparingDouble(ClusterDistances::getDistance));
            clusterDistancesList.add(list.get(0));
            sum += list.get(0).distance;
            result += train.get(j).getObjectName() + " " + list.get(0).distance + " " + list.get(0).cluster.objectName + " ";
            System.out.println(train.get(j).getObjectName() + " " + list.get(0).distance + " " + list.get(0).cluster.objectName + " ");
        }
        System.out.println("sum " + sum);
        for (Cluster c : clusters) {
            ArrayList<Object> objectsClusters = new ArrayList<>();
            ArrayList<Object> setosa = new ArrayList<>();
            ArrayList<Object> virginica = new ArrayList<>();
            ArrayList<Object> versicolor = new ArrayList<>();
            for (int i = 0; i < clusterDistancesList.size(); i++) {
                if (clusterDistancesList.get(i).cluster.objectName.equals(c.objectName)) {
                    objectsClusters.add(clusterDistancesList.get(i).object);
                    if (clusterDistancesList.get(i).object.getObjectName().equals("Iris-setosa")){
                        setosa.add(clusterDistancesList.get(i).object);
                    }
                    if (clusterDistancesList.get(i).object.getObjectName().equals("Iris-virginica")){
                        virginica.add(clusterDistancesList.get(i).object);
                    }
                    if (clusterDistancesList.get(i).object.getObjectName().equals("Iris-versicolor")){
                        versicolor.add(clusterDistancesList.get(i).object);
                    }
                }
            }
            System.out.println("Cluster " + c.objectName + ": Iris-setosa " + Math.round(setosa.size()*100/objectsClusters.size() * 100.0)/100.0 +
                    "%, Iris-virginica " + Math.round(virginica.size()*100/objectsClusters.size() * 100.0)/100.0 + "%, Iris-versicolor " +
                    Math.round(versicolor.size()*100/objectsClusters.size() * 100.0)/100.0 + "%" );
            c.updateCentroids(objectsClusters);
        }
        result += "sum " + sum;
        System.out.println();
        return result;
    }
    public static String testAlgorithm() {
        ArrayList<ClusterDistances> clusterDistancesList = new ArrayList<>();
        String result ="";
        double sum = 0;
        for (int j = 0; j < test.size(); j++) {
            ArrayList<ClusterDistances> list = new ArrayList<>();
            for (Cluster cluster : clusters) {
                double distance = cluster.distanceCalculator(test.get(j));
                list.add(new ClusterDistances(cluster, test.get(j), distance));
            }
            Collections.sort(list, Comparator.comparingDouble(ClusterDistances::getDistance));
            clusterDistancesList.add(list.get(0));
            sum += list.get(0).distance;
            result += test.get(j).getObjectName() + " " + list.get(0).distance + " " + list.get(0).cluster.objectName + " ";
            System.out.println(test.get(j).getObjectName() + " " + list.get(0).distance + " " + list.get(0).cluster.objectName + " ");
        }
        System.out.println("sum " + sum);
        for (Cluster c : clusters) {
            ArrayList<Object> objectsClusters = new ArrayList<>();
            ArrayList<Object> setosa = new ArrayList<>();
            ArrayList<Object> virginica = new ArrayList<>();
            ArrayList<Object> versicolor = new ArrayList<>();
            for (int i = 0; i < clusterDistancesList.size(); i++) {
                if (clusterDistancesList.get(i).cluster.objectName.equals(c.objectName)) {
                    objectsClusters.add(clusterDistancesList.get(i).object);
                    if (clusterDistancesList.get(i).object.getObjectName().equals("Iris-setosa")){
                        setosa.add(clusterDistancesList.get(i).object);
                    }
                    if (clusterDistancesList.get(i).object.getObjectName().equals("Iris-virginica")){
                        virginica.add(clusterDistancesList.get(i).object);
                    }
                    if (clusterDistancesList.get(i).object.getObjectName().equals("Iris-versicolor")){
                        versicolor.add(clusterDistancesList.get(i).object);
                    }
                }
            }
            if (objectsClusters.size()!=0)
            System.out.println("Cluster " + c.objectName + ": Iris-setosa " + Math.round(setosa.size()*100/objectsClusters.size() * 100.0)/100.0 +
                    "%, Iris-virginica " + Math.round(virginica.size()*100/objectsClusters.size() * 100.0)/100.0 + "%, Iris-versicolor " +
                    Math.round(versicolor.size()*100/objectsClusters.size() * 100.0)/100.0 + "%" );
        }
        result += "sum " + sum;
        System.out.println();
        return result;
    }


    public static void main(String[] args) {
        System.out.println("Enter number of clusters ");
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        clusters =  creatingClusters(k);
        String result1 = trainAlgorithm();
        String result2 = trainAlgorithm();
        while (!result1.equals(result2)){
            result1 = trainAlgorithm();
            result2 = trainAlgorithm();
        }
        System.out.println("Algorithm is well-trained");
        System.out.println("Test data");
        testAlgorithm();

    }
}
