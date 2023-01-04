import java.util.ArrayList;

public class Object {
    String language;
    ArrayList<Vector> asciLetters;

    public Object(String language, ArrayList<Vector> asciLetters){
        this.language = language;
        this.asciLetters = asciLetters;
    }

    public String getLanguage() {
        return language;
    }

    public ArrayList<Vector> getAsciLetters() {
        return asciLetters;
    }
}
