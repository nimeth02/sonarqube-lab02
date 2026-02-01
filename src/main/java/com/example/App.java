package main.java.com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    // Create a logger instance for the App class ---
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) throws Exception {
        Calculator calc = new Calculator();
        
        // Using logger instead of System.out
        int result = calc.calculate(10, 5, "add");
        logger.info("Calculator result: {}", result);
        
        UserService service = new UserService();
        service.findUser("admin");
    }
}