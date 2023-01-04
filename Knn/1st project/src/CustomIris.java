import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomIris {
    private ArrayList<Double> parameters;

    public CustomIris(List<Double> par){
        parameters = new ArrayList(Arrays.asList(par));
    }

    public ArrayList<Double> getParameters(){
        return parameters;
    }


}
