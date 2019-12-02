import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // arguments
        String inputName = args[0];
        String outputName = args[1];

        // inputs as list of Strings
        ArrayList<String> inputs = new ArrayList<String>();

        // Read the file
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputs.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("File not found! Check again...");
        }//System.out.println("Error reading lines from the file!");

        ByteCode bc = new ByteCode(inputs);
        // Compile the inputs into byte code
        bc.compile();
        if (bc.labelFlag) {
            bc.setMem();
            bc.compile();
        }
        ArrayList<Integer> mem = bc.getMem();


        // write to an output file
        try {
            FileOutputStream outfile = new FileOutputStream(outputName);

            for (int bytecode : mem) {
                outfile.write(bytecode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        // Input file reader
        BufferedReader reader;
        // Read the file line by line
        try {
            reader = new BufferedReader(new FileReader("compiler_input.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (line.matches("\\/\\/(.)*")) {
                    line = reader.readLine();
                    continue;
                }
                // Print and read lines
                System.out.println(line);

                // Break up the line into words
                line = line.trim();
                line = line.replaceAll(",", " , ");
                String[] tokens = line.split("\\s");

                // the first keyword
                String token = tokens[0];
                if (token != null) {
                    if (token.matches("subr|ret|printi|decl|popv|pushi|printv|add|sub|mul|div|swp|cmpe|cmplt|cmpgt|popm|jmp|jmpc|label|peek|poke")) {
                            //System.out.println(token);
                    }
                }

                // read the next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }*/
    }
}
