import java.util.ArrayList;

public class testIrisDistanse{
    private String irisName;
    private ArrayList<distancesToTrainingData> arrayDistances;

    public testIrisDistanse(String irisName, ArrayList<distancesToTrainingData> arrayDistances){
        this.arrayDistances = arrayDistances;
        this.irisName = irisName;
    }

    public String getIrisName() {
        return irisName;
    }

    public ArrayList<distancesToTrainingData> getArrayDistances() {
        return arrayDistances;
    }


}
