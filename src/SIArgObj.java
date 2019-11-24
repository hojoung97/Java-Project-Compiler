public class SIArgObj extends ArgObj {
    private String str;
    private int i;

    public SIArgObj(String newStr, int newInt) {
        str = newStr;
        i = newInt;
    }

    public String getString() {
        return str;
    }
    public int getInt() {
        return i;
    }
}
