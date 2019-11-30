import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
         // Input file reader
        BufferedReader reader;
        // Read the file line by line
        try {
            reader = new BufferedReader(new FileReader("compiler_input.txt"));
            String line = reader.readLine();
            while (line != null) {
                // Print and read lines
                //System.out.println(line);

                // Break up the line into words
                line = line.trim();
                line = line.replaceAll(",", " , ");
                String[] tokens = line.split("\\s");

                // the first keyword
                String token = tokens[0];
                if (token != null) {
                    if (token.matches("decl|lab|subr|printi|jmp|cmpe|cmplt|cmpgt|pushi|popm|popv|peek|poke|swp|add|sub|mul|div")) {
                            System.out.println(token);
                    }
                }

                // read the next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
