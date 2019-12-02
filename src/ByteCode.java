import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ByteCode {
    // OP CODE
    public static final int HALT = 0;
    public static final int JMP = 36;
    public static final int JMPC = 40;
    public static final int CALL = 44;
    public static final int RET = 48;
    public static final int PUSHI = 70;
    public static final int PUSHVI = 74;
    public static final int POPM = 76;
    public static final int POPA = 77;
    public static final int POPV = 80;
    public static final int PEEKI = 86;
    public static final int POKEI = 90;
    public static final int SWP = 94;
    public static final int ADD = 100;
    public static final int SUB = 104;
    public static final int MUL = 108;
    public static final int DIV = 112;
    public static final int CMPE = 132;
    public static final int CMPLT = 136;
    public static final int CMPGT = 140;
    public static final int PRINTI = 146;
    public static final int LABEL = -1;
    public static final int SHORT = 0;
    public static final int INT = 1;
    public static final int FLOAT = 2;

    // Program variables
    private int lineNum = 0;        // current line number
    private int pc = -1;            // Program Counter
    private int varNum = -1;        // Number of Local Variables
    private String flabel = "main";

    // Compiler Data Structures
    private ArrayList<String> inputStrings;                     // input lines
    private ArrayList<Integer> mem;   // Converted Bytecode
    //public Pair<Integer, Integer> symbolValue = new Pair<Integer, Integer>(0, 0);    //
    private Map<String, int[]> symbolTable;
    //public ArrayList<ArrayList<Integer>> labelValue = new ArrayList<ArrayList<Integer>>();
    //public Map<String, ArrayList<ArrayList<Integer>>>

    public ByteCode () {
        inputStrings = null;
        symbolTable = null;
        mem = null;
    }

    public ByteCode (ArrayList<String> inputSource) {
        inputStrings = inputSource;
        symbolTable = new HashMap<String, int[]>();
        mem = new ArrayList<Integer>();
    }

    // Methods for all instructions
    private void decl(String varName, String type) {
        String key = flabel + "_" + varName;
        int typeVal = 0;
        switch (type) {
            case "int":
                typeVal = INT;
                break;
            case "short":
                typeVal = SHORT;
                break;
            case "float":
                typeVal = FLOAT;
                break;
        }

        int[] value = {++varNum, typeVal};
        symbolTable.put(key, value);
        pushi(0);
    }

    private void lab(String labName) {

    }

    private void subr(int count, String flabel) {
        // temporary
        pushi(16);
        pushi(17);
        pushi(1);
        call();
    }

    private void pushi(int value) {
        mem.add(PUSHI);

        // parse into 4 bytes
        byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
        for (int i = bytes.length - 1; i >= 0; i--) {
            mem.add(Byte.toUnsignedInt(bytes[i]));
        }
        pc += 5;
    }

    private void call() {
        mem.add(CALL);
        mem.add(0);
        pc++;
    }

    private void printi(int value) {
        pushi(value);
        mem.add(PRINTI);
        pc++;
    }

    private void ret() {
        pushi(0);
        mem.add(POPA);
        mem.add(RET);
        pc += 2;
    }

    private void popv(String varName) {
        String key = flabel + "_" + varName;
        int value = (symbolTable.get(key))[0];
        pushi(value);
        mem.add(POPV);
        pc++;
    }

    private void printv(String varName) {
        String key = flabel + "_" + varName;
        int value = symbolTable.get(key)[0];
        pushi(value);
        mem.add(PUSHVI);
        mem.add(PRINTI);
        pc += 2;
    }

    /*

    void jmp(String);
    void jmpc(String);
    void cmpe();
    void cmplt();
    void cmpgt();
    void pushi(int);
    void popm(int);
    void popa(int);
    void peek(String, int);
    void poke(int, String);
    void swp();
    void add();
    void sub();
    void mul();
    void div();
    */

    public ArrayList<Integer> getMem() {
        return mem;
    }

    public int getpc() {
        return pc;
    }

    public void compile() {
        // for each line
        for (String line : inputStrings) {
            // ignore comments
            if (line.matches("\\/\\/(.)*")) {
                continue;
            }

            lineNum++;

            line = line.trim();
            line = line.replaceAll(",", " , ");
            String[] tokens = line.split("\\s");

            // the first keyword
            String token = tokens[0];

            switch (token) {
                case "decl":
                    decl(tokens[1], tokens[2]);
                    break;
                case "lab":
                    lab(tokens[1]);
                    break;
                case "subr":
                    subr(Integer.parseInt(tokens[1]), tokens[2]);
                    break;
                case "printi":
                    printi(Integer.parseInt(tokens[1]));
                    break;
                case "ret":
                    ret();
                    break;
                case "popv":
                    popv(tokens[1]);
                    break;
                case "printv":
                    printv(tokens[1]);
                    break;
            }

        }
    }

}
