package itmo.st;

import itmo.st.math.SystemFunction;

public class Main {

    public static void main(String[] args) {
        SystemFunction systemFunction = new SystemFunction();
        systemFunction.generateCSVForFunction("sin", 0, 2 * Math.PI, Math.PI / 20, "output/sin_results.csv");
        systemFunction.generateCSVForFunction("cos", 0, 2 * Math.PI, Math.PI / 20, "output/cos_results.csv");
        systemFunction.generateCSVForFunction("ln", 0.01, 10, 0.1, "output/ln_results.csv");
        systemFunction.generateCSVForFunction("system", -7, 7, 0.1, "output/system_results.csv");
    }
}
