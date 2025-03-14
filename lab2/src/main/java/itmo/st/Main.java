package itmo.st;

import itmo.st.math.SystemFunction;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SystemFunction systemFunction = new SystemFunction();
        
        if (args.length > 0) {
            processCommandLineArgs(args, systemFunction);
        } else {
            processInteractiveMode(systemFunction);
        }
    }
    
    private static void processCommandLineArgs(String[] args, SystemFunction systemFunction) {
        if (args.length < 5) {
            printUsage();
            return;
        }
        
        try {
            String functionName = args[0];
            double start = Double.parseDouble(args[1]);
            double end = Double.parseDouble(args[2]);
            double step = Double.parseDouble(args[3]);
            String filePath = args[4];
            
            String delimiter = args.length > 5 ? args[5] : ";";
            
            systemFunction.generateCSVForFunction(functionName, start, end, step, filePath, delimiter);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Please check your input.");
            printUsage();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private static void processInteractiveMode(SystemFunction systemFunction) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Enter function name (sin, cos, tan, cot, sec, csc, ln, log2, log3, log5, log10, system):");
            String functionName = scanner.nextLine();
            
            System.out.println("Enter start value:");
            double start = Double.parseDouble(scanner.nextLine());
            
            System.out.println("Enter end value:");
            double end = Double.parseDouble(scanner.nextLine());
            
            System.out.println("Enter step value:");
            double step = Double.parseDouble(scanner.nextLine());
            
            System.out.println("Enter output file path:");
            String filePath = scanner.nextLine();
            
            System.out.println("Enter delimiter (press Enter for default ';'):");
            String delimiter = scanner.nextLine();
            if (delimiter.isEmpty()) {
                delimiter = ";";
            }
            
            systemFunction.generateCSVForFunction(functionName, start, end, step, filePath, delimiter);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Please check your input.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    private static void printUsage() {
        System.out.println("Usage: java -jar program.jar <function> <start> <end> <step> <filepath> [delimiter]");
        System.out.println("  function: sin, cos, tan, cot, sec, csc, ln, log2, log3, log5, log10, system");
        System.out.println("  start: starting value for the function domain");
        System.out.println("  end: ending value for the function domain");
        System.out.println("  step: step size between values");
        System.out.println("  filepath: path to save the CSV file");
        System.out.println("  delimiter: (optional) character to use as CSV delimiter, default is ';'");
        System.out.println("\nExample: java -jar program.jar sin 0 6.28 0.1 output/sin_results.csv ,");
    }
}
