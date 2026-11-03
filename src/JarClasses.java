// Violet Gonzalez
// Nov 2, 2026
/* SWE 262P Task:
* takes a jar file as command line argument and prints out a listing of all
* the classes inside, as well as the number of declared public, private, protected,
* and static methods for each, and the number of declared fields for each.
*/


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Path;

public class JarClasses {
    public static void main(String[] args) throws IOException {

        // Create a String path to open a file from the args
        String file = args[0];

        // Converting the string to a Path object
        Path path = Path.of(file);

        // Output the Path
        System.out.println(path);
    }

}
