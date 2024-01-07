package clients.cashier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ReceiptWriter {

    public static void writeBasketDetails(String filePath, String basketDetails) {
        try {
            // Ensure parent directories exist
            Files.createDirectories(Paths.get(filePath).getParent());

            // Write basket details to file
            Files.write(Paths.get(filePath), basketDetails.getBytes(), StandardOpenOption.CREATE);

            System.out.println("Data written to file: " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String filePath = "OrderInfo/Orders/Order.txt";
        String basketDetails = "Your basket details here.";
        writeBasketDetails(filePath, basketDetails);
    }
}
