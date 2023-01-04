import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {

    public static ArrayList<Object> path(String _directory) {
        ArrayList<Object> objects = new ArrayList<>();
        String language = null;
        DirectoryStream<Path> directories = null;
        try {
            directories = Files.newDirectoryStream(Paths.get(_directory));

            for (Path directory : directories) {
                ArrayList<Vector> vectors = new ArrayList<>();

                if (Files.isDirectory(directory)) {
                    language = directory.getFileName().toString();

                    Stream<Path> files = Files.walk(directory);
                    for (Path file : files.filter(Files::isRegularFile).collect(Collectors.toList())) {
                        StringBuilder stringBuilder = new StringBuilder();
                        BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
                        String line;
                        while (null != (line = br.readLine()))
                            stringBuilder.append(line.toLowerCase().replaceAll("[^a-zA-Z]", ""));
                        vectors.add(new Vector(stringBuilder.toString()));
                    }
                }
                objects.add(new Object(language, vectors));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }
}
