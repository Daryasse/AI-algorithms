import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class KnnAlgorithm {
    private static final String double_regex = "([0-9]+([.][0-9]*)?|[.][0-9]+)";
    private static final String type_regex = "[A-Z][a-z]+/s[A-z][a-z]+";
    private static String fileTrain = "iris/train.txt";
    private static String fileTest = "iris/test.txt";
    private static ArrayList<Iris> train = loadTrainingIrises();
    private static ArrayList<Iris> test = loadTestingIrises();
    private static ArrayList<testIrisDistanse> irisDistances = distancesForTest();
    private static final List<String> IrisClassify = Arrays.asList("Iris-virginica", "Iris-setosa", "Iris-versicolor");


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input a train file path: ");
        String wayTrain = scanner.next();
        if (!wayTrain.isEmpty())
            fileTrain = wayTrain;
        System.out.print("Input a test file path: ");
        String wayTest = scanner.next();
        if (!wayTest.isBlank() && !wayTest.isEmpty())
            fileTest = wayTest;
        System.out.print("Input a k number: ");
        int n = scanner.nextInt();
        System.out.println("Do you want to analyze custom values: yes or no?");
        String answer = scanner.next();
        if (answer.equals("no")) {
            System.out.println("then let's switch to file data analysis");
        }
        if (answer.equals("yes")) {
            System.out.println("Enter the first parameter");
            double p1 = scanner.nextDouble();
            System.out.println("Enter the second parameter");
            double p2 = scanner.nextDouble();
            System.out.println("Enter the third parameter");
            double p3 = scanner.nextDouble();
            System.out.println("Enter the fourth parameter");
            double p4 = scanner.nextDouble();
        }
        defindSortOfIris(n);
    }

    public static ArrayList<Iris> loadTestingIrises() {

        ArrayList<Iris> testIris = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileTest));
            for (String line : lines) {
                String parts[] = line.split(",");
                ArrayList<Double> parameters = new ArrayList<>();
                String type = new String();
                for (int i = 0; i< parts.length; i++){
                   if (parts[i].matches(double_regex)){
                      parameters.add(Double.valueOf(parts[i]));
                    }
                   if (parts[i].matches(type_regex) && i == parts.length-1){
                       type = parts[i] ;
                   }
                }
                testIris.add(new Iris(parameters, type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testIris;
    }

    public static ArrayList<Iris> loadTrainingIrises() {
        ArrayList<Iris> trainIris = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileTrain));
            for (String line : lines) {
                String parts[] = line.split(",");
                ArrayList<Double> parameters = new ArrayList<>();
                String type = new String();
                for (int i = 0; i< parts.length; i++){
                    if (parts[i].matches(double_regex)){
                        parameters.add(Double.valueOf(parts[i]));
                    }
                    if (parts[i].matches(type_regex) && i == parts.length-1){
                        type = parts[i] ;
                    }
                }
                trainIris.add(new Iris(parameters, type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trainIris;
    }

    public static ArrayList<testIrisDistanse> distancesForTest() {
        ArrayList<testIrisDistanse> irisDistances = new ArrayList<>();
        for (int i = 0; i < test.size(); i++) {
            ArrayList<distancesToTrainingData> distancesFromIrisToTrainingData = new ArrayList<>();
            for (int j = 0; j < train.size(); j++) {
                double distance = 0;
                distance = distanceCalculator(test.get(i), train.get(j));
                distancesFromIrisToTrainingData.add(new distancesToTrainingData(distance, test.get(i).getIrisName()));

            }
            irisDistances.add(new testIrisDistanse(test.get(i).getIrisName(), distancesFromIrisToTrainingData));
        }
        return irisDistances;
    }

    public static void defindCustomSortOfIris(int n, CustomIris Iris) {
        List<distancesToTrainingData> distancesFromIris = new ArrayList<>();
        HashMap<String, Integer> topMinDistances = new HashMap<>();
        for (int i = 0; i < train.size(); i++) {
            double dist = distanceCalculator(train.get(i),new Iris(new ArrayList(Arrays.asList(Iris.getParameters())), ""));
            distancesFromIris.add(new distancesToTrainingData(dist, train.get(i).getIrisName()));
        }
        Collections.sort(distancesFromIris, Comparator.comparingDouble(distancesToTrainingData::getDistance));
        for (int i = 0; i < n; i++) {
            if (topMinDistances.containsKey(distancesFromIris.get(i).getIrisName())) {
                topMinDistances.put(distancesFromIris.get(i).getIrisName(),
                        topMinDistances.get(distancesFromIris.get(i).getIrisName()) + 1);
            } else {
                topMinDistances.put(distancesFromIris.get(i).getIrisName(), 1);
            }

        }
        List<Integer> list = new ArrayList<>();
        list.addAll(topMinDistances.values());
        Collections.sort(list, Collections.reverseOrder());


        if (topMinDistances.get(IrisClassify.get(0)) == list.get(0)) {
            System.out.println("Provided " + IrisClassify.get(0));
        }
        if (topMinDistances.get(IrisClassify.get(1)) == list.get(0)) {
            System.out.println("Provided " + IrisClassify.get(1));
        }
        if (topMinDistances.get(IrisClassify.get(2)) == list.get(0)) {
            System.out.println("Provided " + IrisClassify.get(2));
        }

    }

    public static void defindSortOfIris(int n) {
        int counter = 0;
        for (int i = 0; i < irisDistances.size(); i++) {
            Collections.sort(irisDistances.get(i).getArrayDistances(), Comparator.comparingDouble(distancesToTrainingData::getDistance));
            HashMap<String, Integer> topMinDistances = new HashMap<>();
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < n; j++) {

                if (topMinDistances.containsKey(irisDistances.get(i).getArrayDistances().get(j).getIrisName())) {
                    topMinDistances.put(irisDistances.get(i).getArrayDistances().get(j).getIrisName(),
                            topMinDistances.get(irisDistances.get(i).getArrayDistances().get(j).getIrisName()) + 1);
                } else {
                    topMinDistances.put(irisDistances.get(i).getArrayDistances().get(j).getIrisName(), 1);
                }
            }
            list.addAll(topMinDistances.values());
            Collections.sort(list, Collections.reverseOrder());
            boolean correct;

            if (topMinDistances.get(IrisClassify.get(0)) == list.get(0)) {
                correct = (irisDistances.get(i).getIrisName().equals(IrisClassify.get(0)));
                System.out.println("Correct " + irisDistances.get(i).getIrisName() + ", provided " + IrisClassify.get(0)
                        + " status: " + correct);
                counter++;
            }
            if (topMinDistances.get(IrisClassify.get(1)) == list.get(0)) {
                correct = (irisDistances.get(i).getIrisName().equals(IrisClassify.get(1)));
                System.out.println("Correct " + irisDistances.get(i).getIrisName() + ", provided " + IrisClassify.get(1)
                        + " status: " + correct);
                counter++;
            }
            if (topMinDistances.get(IrisClassify.get(2)) == list.get(0)) {
                correct = (irisDistances.get(i).getIrisName().equals(IrisClassify.get(2)));
                System.out.println("Correct " + irisDistances.get(i).getIrisName() + ", provided " + IrisClassify.get(2)
                        + " status: " + correct);
                counter++;
            }

        }
        System.out.println(counter + "/" + test.size() + " correct");
    }


    public static double distanceCalculator(Iris iris1, Iris iris2){

            double distance = 0;
            if (iris1.getParameters().size() == iris2.getParameters().size()) {
                int size = iris1.getParameters().size();
                for (int i = 0; i < size; i++) {
                    double distanceDif = iris2.getParameters().get(i) - iris1.getParameters().get(i);
                    double distance1 = Math.pow(distanceDif, 2);
                    distance += distance1;
                }
            }
            return Math.sqrt(distance);
    }

}
