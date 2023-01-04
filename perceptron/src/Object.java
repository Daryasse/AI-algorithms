import java.util.ArrayList;

public class Object {
    private ArrayList<Double> inputs = new ArrayList<>();;
    private String objectName;

    public Object(ArrayList<Double> par, String objectName){
        inputs.addAll(par);
        this.objectName = objectName;
    }

    public ArrayList<Double> getInputs(){
        return inputs;
    }


    public String getObjectName() {
        return objectName;
    }
}
