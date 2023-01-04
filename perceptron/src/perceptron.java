import javax.lang.model.element.Name;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class perceptron {
    double[] weights;
    double theta;
    double trainingRate;
    private static final String double_regex = "([0-9]+([.][0-9]*)?|[.][0-9]+)";
    private static final String STRING_regex = "[A-Z][a-z]+[-][A-Z]?[a-z]+";
    private static final String BOOLEAN_regex = "[0|1]";
//    private static String fileTrain = "data for perceptron/Eexample2/train.txt";
//    private static String fileTest = "data for perceptron/Eexample2/test.txt";
    private static String fileTrain = "data for perceptron/example2/train.txt";
    private static String fileTest = "data for perceptron/example2/test.txt";
//    private static String fileTrain = "data for perceptron/example1/train.txt";
//    private static String fileTest = "data for perceptron/example1/test.txt";
//    private static String fileTrain = "data for perceptron/iris_perceptron/train1.txt";
//    private static String fileTest = "data for perceptron/iris_perceptron/test1.txt";
//    private static String fileTrain = "data for perceptron/iris_perceptron/training.txt";
//    private static String fileTest = "data for perceptron/iris_perceptron/test.txt";
    private static ArrayList<Object> train = loadTrainingIrises();
    private static ArrayList<Object> test = loadTestingIrises();
    private static HashMap<String, Integer> NameClassify = fullHashMap();
    static int limit;


    perceptron(double trainingRate) {
        this.trainingRate = trainingRate;
        weights = randomWeights();
        theta = 0.5;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter training rate ");
        double train_rate = sc.nextDouble();
        if (train_rate > 1 || train_rate <= 0) {
            System.out.println("train rate should be between 0 and 1");
        } else {
            perceptron perceptron = new perceptron(train_rate);
            controlTraining(perceptron, train);
            testDataSet(perceptron, test);
            System.out.println("Want to enter any sample: yes or no?");
            String answer = sc.next();
            if (answer.equals("no")) {
                System.out.println("Thank you for trying me.");
            }
            if (answer.equals("yes")) {
                System.out.println("How many numbers you want to enter");
                int number = sc.nextInt();
                if (number != train.get(0).getInputs().size()) {
                    System.out.println("Algorithm wasn't trained for such data");
                } else {
                    ArrayList<Double> parameters = new ArrayList<>();
                    for (int i = 0; i < number; i++) {
                        System.out.println("Enter parameter");
                        parameters.add(sc.nextDouble());
                    }
                    testSampleCustom(perceptron, new Object(parameters, ""));
                }
            }
        }
    }

    public static ArrayList<Object> loadTestingIrises() {
        ArrayList<Object> testObjects = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileTest));
            for (String line : lines) {
                String parts[] = line.split(",");
                ArrayList<Double> inputs = new ArrayList<>();
                String decisionAttribute = new String();
                if (!line.isEmpty()) {
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].matches(double_regex) && i < parts.length - 1) {
                            inputs.add(Double.valueOf(parts[i]));
                        }
                        if (i == parts.length - 1) {
                            if (parts[i].matches(STRING_regex) || parts[i].matches(BOOLEAN_regex)) {
                                decisionAttribute = parts[i];
                            }
                        }
                    }
                    testObjects.add(new Object(inputs, decisionAttribute));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testObjects;
    }

    public static ArrayList<Object> loadTrainingIrises() {
        ArrayList<Object> trainObjects = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileTrain));
            for (String line : lines) {
                String parts[] = line.split(",");
                ArrayList<Double> inputs = new ArrayList<>();
                String decisionAttribute = new String();
                if (!line.isEmpty()) {
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].matches(double_regex) && i < parts.length - 1) {
                            inputs.add(Double.valueOf(parts[i]));
                        }
                        if (i == parts.length - 1) {
                            if (parts[i].matches(STRING_regex) || parts[i].matches(BOOLEAN_regex)) {
                                decisionAttribute = parts[i];
                            }
                        }
                    }
                    trainObjects.add(new Object(inputs, decisionAttribute));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trainObjects;
    }

    private static HashMap<String, Integer> fullHashMap() {
        HashMap<String, Integer> names = new HashMap<>();
        for (int i = 0; i < train.size(); i++) {
            if (names.isEmpty()) {
                names.put(train.get(i).getObjectName(), 0);
            }
            if (!names.containsKey(train.get(i).getObjectName())) {
                names.put(train.get(i).getObjectName(), 1);
            }
        }
        return names;
    }

    private double[] randomWeights() {
        double[] weightsRandom = new double[train.get(0).getInputs().size()];
        for (int i = 0; i < train.get(0).getInputs().size(); i++) {
            weightsRandom[i] = 0;
        }
        return weightsRandom;
    }

    private double randomTheta() {
        double random = Math.random() * 3;
        return random;
    }

    public static int provideOutput(perceptron perceptron, Object data) {
        double sum = perceptron.theta;
        if (perceptron.weights.length == data.getInputs().size()) {
            for (int i = 0; i < data.getInputs().size(); i++) {
                sum += perceptron.weights[i] * data.getInputs().get(i);
            }
        } else {
            System.out.println("Data doesn't match training set");
        }
        return (sum > 0 ? 1 : 0);
    }

    public static int TrainAlgorithmFirst(perceptron perceptron, ArrayList<Object> data) {
        int count = 0;
        int countTries = 0;
        ArrayList<Integer> listOfAccurancies = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {

            int d = provideOutput(perceptron, data.get(i));
            if (d != NameClassify.get(data.get(i).getObjectName())) {
                int y = NameClassify.get(data.get(i).getObjectName());
                int err = d - y;
                System.out.println(i);
                for (int j = 0; j < perceptron.weights.length; j++) {
                    perceptron.weights[j] += err * perceptron.theta * data.get(i).getInputs().get(j);
                    System.out.print(perceptron.weights[j] + " ");
                }
                perceptron.theta -= err * perceptron.trainingRate;
                System.out.println();
                i = 0;
                countTries++;
            }
            if (d == NameClassify.get(data.get(i).getObjectName())) {
                count += 1;
                listOfAccurancies.add(i+1);
            }
            if (countTries == 150){
                break;
            }
        }
        Collections.sort(listOfAccurancies, Collections.reverseOrder());
        limit = listOfAccurancies.get(0);
        return countTries;
    }

    public static void controlTraining(perceptron perceptron, ArrayList<Object> data) {
        int countAccurancy = 0;
        TrainAlgorithmFirst(perceptron, data);
        System.out.println(limit);
        if (limit!= data.size()) {
            while (countAccurancy != limit) {
                countAccurancy = TrainAlgorithmSecond(perceptron, data);
            }
        }
        else {
            while (countAccurancy != data.size()) {
                countAccurancy = TrainAlgorithmSecond(perceptron, data);
            }
        }
            //14 - for example1
            //74 - for virginica or versicolor
        if (countAccurancy == limit) {
            System.out.println("algorithm well-trained");
        }
    }
    public static int TrainAlgorithmSecond(perceptron perceptron, ArrayList<Object> data) {
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            int d = provideOutput(perceptron, data.get(i));
            if (count == limit || count == data.size()){
                System.out.println(limit);
                break;

            }
            if (d != NameClassify.get(data.get(i).getObjectName())) {
                int y = NameClassify.get(data.get(i).getObjectName());
                int err = d - y;
                System.out.println(i);
                for (int j = 0; j < perceptron.weights.length; j++) {
                    perceptron.weights[j] += err * perceptron.theta * data.get(i).getInputs().get(j);
                    System.out.print(perceptron.weights[j] + " ");
                }
                perceptron.theta -= err * perceptron.trainingRate;
                System.out.println();
                i = 0;
            }
            if (d == NameClassify.get(data.get(i).getObjectName())) {
                count += 1;
            }

        }
        System.out.println("Correctness " + count + "/" + data.size());
        return count;
    }

    public static void testDataSet(perceptron perceptron, ArrayList<Object> data) {
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            boolean correctness = testSample(perceptron, data.get(i));
            if (correctness) {
                count += 1;
            }
        }
        System.out.println("Correct " + count + "/" + data.size());
    }

    public static boolean testSample(perceptron perceptron, Object sample) {
        int d = provideOutput(perceptron, sample);
        String provided = "";
        String essential = "";

        if (NameClassify.get(sample.getObjectName()) != d) {
            for (String name : NameClassify.keySet()) {
                if (NameClassify.get(name) == d) {
                    provided = name;
                }
            }
            essential = sample.getObjectName();
        }
        if (d == NameClassify.get(sample.getObjectName())) {
            provided = sample.getObjectName();
            essential = sample.getObjectName();
        }
        boolean correctness = provided.equals(sample.getObjectName());
        System.out.println("Correct: " + provided + "; Defined: " + essential + " --- " + correctness);
        return correctness;
    }

    public static void testSampleCustom(perceptron perceptron, Object sample) {
        int d = provideOutput(perceptron, sample);
        String provided = "";

            for (String name : NameClassify.keySet()) {
                if (NameClassify.get(name) == d) {
                    provided = name;
                }
            }

        System.out.println("Defined: " + provided);
    }
}
