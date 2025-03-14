package itmo.st;

import itmo.st.math.SystemFunction;

public class Main {

    public static void main(String[] args) {
        SystemFunction systemFunction = new SystemFunction();
        
        // Using default delimiter (semicolon)
        systemFunction.generateCSVForFunction("sin", 0, 2 * Math.PI, Math.PI / 20, "output/sin_results.csv");
        
        // Using custom delimiters
        systemFunction.generateCSVForFunction("cos", 0, 2 * Math.PI, Math.PI / 20, "output/cos_results.csv", ",");
        systemFunction.generateCSVForFunction("ln", 0.01, 10, 0.1, "output/ln_results.csv", "\t");
        systemFunction.generateCSVForFunction("system", -7, 7, 0.1, "output/system_results.csv", "|");
    }
}
