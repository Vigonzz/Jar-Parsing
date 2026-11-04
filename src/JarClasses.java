// Violet Gonzalez
// Nov 2, 2026
/* SWE 262P Task:
* takes a jar file as command line argument and prints out a listing of all
* the classes inside, as well as the number of declared public, private, protected,
* and static methods for each, and the number of declared fields for each.
*/


import java.io.IOException;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;
import java.util.Enumeration;
import java.util.jar.JarEntry;

public class JarClasses {
    public static void main(String[] args) throws IOException {

        // Exception handling, incase nothing was passed in the args
        if (args.length < 1) {

            System.err.println("Need a a jar file");
            System.err.println("Enter file as: java filename.java ");
            System.exit(1);
        }
        // Create a String path to open a file from the args
        String file = args[0];

        // Create a list to hold the classnames
        List<String> classNames = new ArrayList<>();

        // Create a jar file
        try (JarFile jar = new JarFile(file)) {
            // Shift through the jar file and print the entries
            Enumeration<JarEntry> shiftThrough = jar.entries();
            while (shiftThrough.hasMoreElements()) {
                JarEntry entry = shiftThrough.nextElement();
                // get the name
                String name = entry.getName();
                // Filter
                if (name.endsWith(".class") && !name.startsWith("META-INF/")) {
                    String cleanName = name.substring(0, name.length() - 6);
                    String conversion = cleanName.replace("/", ".");
                    classNames.add(conversion);
                }
            }
        }

        // Sort the list
        Collections.sort(classNames);

        // Loading each class
        // points to the jar
        File jarFile = new File(file);
        URL url = jarFile.toURI().toURL();
        // Place the url into an array
        URL[] url1 = new URL[]{ url };

        // Handle classes that fail:
        try (URLClassLoader loader = new URLClassLoader(url1)) {
            // In the sorted lines, add the header print style
            for (String sortNames : classNames) {
                try {
                    Class<?> load = loader.loadClass(sortNames);
                    System.out.println("----------" + load.getName() + "----------");
                    // Get the methods
                    Method[] methods = load.getDeclaredMethods();
                    // Counter for public methods
                    int publicCount = 0;
                    // organize public methods used
                    for (Method method : methods) {
                        int modifiers = method.getModifiers();
                        if (Modifier.isPublic(modifiers)) {
                            publicCount += 1;
                        }
                    }
                    // Counter for private
                    int privateCount = 0;
                    // Organize and collect private methods to display
                    for (Method method : methods) {
                        int modifiers = method.getModifiers();
                        if (Modifier.isPrivate(modifiers)) {
                            privateCount += 1;
                        }
                    }
                    // Counter for protected
                    int protectedCount = 0;
                    // Organize and collect protected methods to display
                    for (Method method : methods) {
                        int modifiers = method.getModifiers();
                        if (Modifier.isProtected(modifiers)) {
                            protectedCount += 1;
                        }
                    }
                    // Counter for static
                    int staticCount = 0;
                    // Organize and collect static methods to display
                    for (Method method : methods) {
                        int modifiers = method.getModifiers();
                        if (Modifier.isStatic(modifiers)) {
                            staticCount += 1;
                        }
                    }

                    // Output expected format
                    System.out.println("  Public methods: " + publicCount);
                    System.out.println("  Private methods: " + privateCount);
                    System.out.println("  Protected methods: " + protectedCount);
                    System.out.println("  Static methods: " + staticCount);

                    int length = load.getDeclaredFields().length;
                    System.out.println("  Fields: " + length);
                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                    System.out.println("Could not examine: " + sortNames);
                }
            }
        }
    }
}
