// Violet Gonzalez
// Nov 2, 2026
/* SWE 262P Task:
* takes a jar file as command line argument and prints out a listing of all
* the classes inside, as well as the number of declared public, private, protected,
* and static methods for each, and the number of declared fields for each.
*/


import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;
import java.util.Enumeration;
import java.util.jar.JarEntry;

public class JarClasses {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // Exception handling, incase nothing was passed in the args
        if (args.length < 1) {

            System.err.println("Need a a jar file");
            System.err.println("Enter file as: java filename.java ");
            System.exit(1);
        }
        // Create a String path to open a file from the args
        String file = args[0];

        // Create a jar file
         JarFile jar = new JarFile(file);
         // Create a list to hold the classnames
        List<String> classNames = new ArrayList<>();
        // Shift through the jar file and print the entries
        Enumeration<JarEntry> shiftThrough = jar.entries();
        while (shiftThrough.hasMoreElements()) {
            JarEntry entry = shiftThrough.nextElement();
            // get the name
            String name = entry.getName();
            // Filter
            if (name.endsWith(".class") &&  ! name.startsWith("META-INF/")) {
                String cleanName = name.substring(0, name.length() - 6);
                String conversion = cleanName.replace("/", ".");
                classNames.add(conversion);
            }
        }
        // Loading each class
        // points to the jat
        File jarFile = new File(file);
        URL url = jarFile.toURI().toURL();
        // Place the url into an array
        URL[] url1 = new URL[]{ url };
        URLClassLoader loader = new URLClassLoader(url1);

        // Sort the list
        Collections.sort(classNames);

        for (String sortNames : classNames) {
            Class<?> load = loader.loadClass(sortNames);
            System.out.println("----------" + load.getName() + "----------");
        }



    }
}
