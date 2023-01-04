public class Vector {
    private double[] asciLetters;


    public Vector(String asciLetters){
        this.asciLetters = countLetters(asciLetters);

    }
    private double calculateLength(double[] vector) {
        double length = 0;
        for (double number : vector) {
            length += Math.pow(number, 2);
        }
        return Math.sqrt(length);
    }

    private double[] countLetters(String text) {
        double[] letters = new double[26];
        if (text.isEmpty()){
            for (int a = 0 ; a<letters.length; a++){
                letters[a] = 0;
            }
        }
        else {
            int a = 0;
            for (int i = 'a'; i <= 'z'; i++) {
                char letter = (char) i;
                double counter = (double) text.chars().filter(character -> character == letter).count();
                letters[a++] = counter;
            }

            double distance = calculateLength(letters);

            //normalization of vector
            for (int i = 0; i < letters.length; i++) {
                letters[i] = letters[i] / distance;
            }
        }
        return letters;
    }

    public double[] getVector() {
        return asciLetters;
    }


}
