import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Perseptron {
    HashMap<String, Integer> map= new HashMap<>();
    private double alpha = 0.8;
    private double theta = Math.random()*1;
    private double[] weights;

    public Perseptron() {
        weights = fillRandom();
    }

    private double[] fillRandom() {
        double[] array = new double[26];
        Arrays.fill(array, Math.random()*2);
        return array;
    }
    private static HashMap<String, Integer> fullHashMap(Object object) {
        HashMap<String, Integer> names = new HashMap<>();
        names.put(object.getLanguage(), 1);
        String notThisLanguage = "Not " + object.getLanguage();
        names.put(notThisLanguage, 0);
        return names;
    }

    public int defined(Vector vector) {
        double sum = theta;
        for (int i = 0; i < vector.getVector().length; i++)
            sum += vector.getVector()[i] * weights[i];
        return (sum> 0? 1:0);
    }
    public double definedAccuracy(Vector vector) {
        double sum = theta;
        for (int i = 0; i < vector.getVector().length; i++)
            sum += vector.getVector()[i] * weights[i];
        return sum;
    }

    public void train (Object object){
        int correct = 0;
        int total = 0;
        HashMap<String, Integer> hash = fullHashMap(object);

        for (int i = 0; i<object.asciLetters.size(); i++){
            total++;
            int d = defined(object.asciLetters.get(i));
            int y = hash.get(object.getLanguage());
            if (d==y){
                correct++;
            }
            else{
                int err = d - y;
                for (int j = 0; j < weights.length; j++) {
                    weights[j] += err * theta * object.getAsciLetters().get(i).getVector()[j];
                }
                theta -= err * alpha;
                i = 0;
            }
        }
        if (correct != total){
            System.out.println("i am not 100% accurate");
        }
        for (int j = 0; j < weights.length; j++) {
            System.out.println(weights[j]);
        }
        System.out.println(theta);
        System.out.println("Correct " + correct + "/" + object.getAsciLetters().size());
        if (correct == object.getAsciLetters().size()){
            System.out.println("Algorithm is well-trained");
        }
    }
    public void trainOnExtraData (Object object){
        int correct = 0;
        HashMap<String, Integer> hash = new HashMap<>();
        hash.put(object.getLanguage(), 1);
        hash.put("Not", 0);
        int a=0;
        for (int i = 0; i<object.asciLetters.size(); i++){
            while (a!=1000000) {
                a++;
                int d = defined(object.asciLetters.get(i));
                int y = hash.get(object.getLanguage());
                if (d == y) {
                    int err = d - y;
                    for (int j = 0; j < weights.length; j++) {
                        weights[j] += err * theta * object.getAsciLetters().get(i).getVector()[j];
                    }
                    theta += err * alpha;
                    i = 0;
                } else {
                    correct++;
                }
            }
        }
    }
    public ArrayList<Double> defineMax(Object object){
        int correct = 0;
        ArrayList <Double> definedAccurate = new ArrayList<>();
        HashMap<String, Integer> hash = fullHashMap(object);
        for (int i = 0; i<object.asciLetters.size(); i++) {
            int d = defined(object.asciLetters.get(i));
            int y = hash.get(object.getLanguage());
            double dA = definedAccuracy(object.asciLetters.get(i));
            boolean correctness = (d==y);
            definedAccurate.add(dA);
            if(correctness){
                correct++;
            }
        }
        Collections.sort(definedAccurate, Collections.reverseOrder());
        return definedAccurate;
    }

    public double getAlpha() { return alpha; }

    public void setAlpha(double alpha) { this.alpha = alpha; }

    public double getTheta() {return theta; }

    public void setTheta(double theta) { this.theta = theta; }

    public double[] getWeights() { return weights; }

    public void setWeights(double[] weights) { this.weights = weights; }
}
