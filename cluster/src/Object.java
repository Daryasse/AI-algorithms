import java.util.ArrayList;

public class Object {
    private ArrayList<Double> parameters = new ArrayList<>();;
    private String objectName;

    public Object(ArrayList<Double> parameters, String objectName){
        this.parameters.addAll(parameters);
        this.objectName = objectName;
    }

    public ArrayList<Double> getParameters(){
        return parameters;
    }

    public String getObjectName() {
        return objectName;
    }
}
