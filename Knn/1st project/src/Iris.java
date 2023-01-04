import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Iris {
    private ArrayList<Double> parameters;
    private String irisName;

    public Iris(ArrayList<Double> par, String irisName){
        parameters = new ArrayList(Arrays.asList(par));
        this.irisName = irisName;
    }

    public ArrayList<Double> getParameters(){
        return parameters;
    }


    public String getIrisName() {
        return irisName;
    }
}
