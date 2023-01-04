import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomIris {
    private ArrayList<Double> parameters = new ArrayList<>();;

    public CustomIris(ArrayList<Double> par){
        parameters.addAll(par);
    }

    public ArrayList<Double> getParameters(){
        return parameters;
    }


}
