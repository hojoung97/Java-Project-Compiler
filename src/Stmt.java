abstract public class Stmt {
    private StmtParser parser;
    private ArgObj args;

    abstract void genCode(ArgObj args);
}
