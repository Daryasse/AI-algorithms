import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class main {
    public static ArrayList<Object> test, training;
    public static HashMap<String, Perseptron> hashPerseptron;

    public static void main(String[] args) {

        test = FileHandler.path("data/test");
        training = FileHandler.path("data/train");
        hashPerseptron = PerceptronWork(training);
        System.out.println(hashPerseptron.keySet());
        System.out.println(hashPerseptron.values());
        hashPerseptron.get(training.get(0).getLanguage()).trainOnExtraData(training.get(1));
        hashPerseptron.get(training.get(0).getLanguage()).trainOnExtraData(training.get(2));
        hashPerseptron.get(training.get(1).getLanguage()).trainOnExtraData(training.get(0));
        hashPerseptron.get(training.get(1).getLanguage()).trainOnExtraData(training.get(2));
        hashPerseptron.get(training.get(2).getLanguage()).trainOnExtraData(training.get(0));
        hashPerseptron.get(training.get(2).getLanguage()).trainOnExtraData(training.get(1));
        hashPerseptron.get(training.get(0).getLanguage()).train(training.get(0));
        hashPerseptron.get(training.get(1).getLanguage()).train(training.get(1));
        hashPerseptron.get(training.get(2).getLanguage()).train(training.get(2));
        testData(test);
//        hashPerseptron.get(training.get(0).getLanguage()).trainOnExtraData(training.get(1));
//        hashPerseptron.get(training.get(0).getLanguage()).trainOnExtraData(training.get(2));
//        hashPerseptron.get(training.get(1).getLanguage()).trainOnExtraData(training.get(0));
//        hashPerseptron.get(training.get(1).getLanguage()).trainOnExtraData(training.get(2));
//        hashPerseptron.get(training.get(2).getLanguage()).trainOnExtraData(training.get(0));
//        hashPerseptron.get(training.get(2).getLanguage()).trainOnExtraData(training.get(1));
//        hashPerseptron.get(training.get(0).getLanguage()).train(training.get(0));
//        hashPerseptron.get(training.get(1).getLanguage()).train(training.get(1));
//        hashPerseptron.get(training.get(2).getLanguage()).train(training.get(2));
        System.out.println("Do you want any custom text: yes or no?");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        if (answer.equals("yes")){
            System.out.println("Enter the text");
            String text = scanner.next();
            ArrayList<Vector> vector = new ArrayList<>();
            vector.add(new Vector(text));
            Object object = new Object("", vector);
            testCustom(object);
        }
        if (answer.equals("no")){
            System.out.println("Thanks for using me");
        }

    }
    public static void testCustom(Object object) throws NullPointerException {
        HashMap<Double, String> max = new HashMap<>();
        ArrayList<Double> maxVal = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        names.addAll(hashPerseptron.keySet());
        for (int a = 0; a < names.size(); a++) {
            hashPerseptron.get(names.get(a)).defineMax(object);
            max.put(hashPerseptron.get(names.get(a)).defineMax(object).get(0), names.get(a));
            maxVal.add(hashPerseptron.get(names.get(a)).defineMax(object).get(0));

        }
            Collections.sort(maxVal, Collections.reverseOrder());
        for (int i = 0; i < maxVal.size(); i++){
            System.out.println(maxVal.get(i));
        }
            System.out.println("Defined: " + max.get(maxVal.get(0)));

    }

    public static void testData(ArrayList<Object> objects) throws NullPointerException {
        HashMap<Double, String> max = new HashMap<>();
        ArrayList<Double> maxVal = new ArrayList<>();
        for (int a = 0; a < hashPerseptron.values().size(); a++) {
        for (int i = 0; i< objects.size(); i++) {
                hashPerseptron.get(objects.get(a).getLanguage()).defineMax(test.get(i));
                max.put(hashPerseptron.get(objects.get(a).getLanguage()).defineMax(objects.get(i)).get(0), objects.get(a).language);
                maxVal.add(hashPerseptron.get(objects.get(a).getLanguage()).defineMax(objects.get(i)).get(0));
            }

            Collections.sort(maxVal, Collections.reverseOrder());
            boolean correct = (objects.get(a).getLanguage().equals(max.get(maxVal.get(0))));
            if (!correct){
                while (correct){
                    hashPerseptron.get(training.get(0).getLanguage()).trainOnExtraData(training.get(1));
                    hashPerseptron.get(training.get(0).getLanguage()).trainOnExtraData(training.get(2));
                    hashPerseptron.get(training.get(1).getLanguage()).trainOnExtraData(training.get(0));
                    hashPerseptron.get(training.get(1).getLanguage()).trainOnExtraData(training.get(2));
                    hashPerseptron.get(training.get(2).getLanguage()).trainOnExtraData(training.get(0));
                    hashPerseptron.get(training.get(2).getLanguage()).trainOnExtraData(training.get(1));
                }
            }
//            System.out.println("Correct: " + objects.get(a).getLanguage() + "; Defined: " + max.get(maxVal.get(0))+ "---" + correct);
            System.out.println("Correct: " + objects.get(a).getLanguage() + "; Defined: " + max.get(maxVal.get(0)));
        }
    }


    public static HashMap<String, Perseptron> PerceptronWork(ArrayList<Object> objects) {
        HashMap<String, Perseptron> hash = new HashMap<>();
        for (int a = 0; a < objects.size(); a++) {
            Perseptron perseptron = new Perseptron();
            perseptron.train(objects.get(a));
            hash.put(objects.get(a).getLanguage(), perseptron);
        }
        return hash;
    }
}
