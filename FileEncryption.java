import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FileEncryption {

    private static final int ENCRYPTION_KEY = 10;

    public static void main(String[] args) {
        try {
            // Convert local time to IST
            LocalDateTime dateTime = LocalDateTime.now();
            ZoneId timeZone = ZoneId.of("Asia/Kolkata");
            LocalDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(timeZone).toLocalDateTime();

            // Format the date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy   HH:mm:ss a");
            String formattedDateTime = zonedDateTime.format(formatter);

            // Welcome message
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + " " + "WELCOME TO FILE ENCRYPTION TOOL" + " " + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println("                                  \n" + "ENCRYPT and DECRYPT your files!!!!");
            System.out.println("\n" + "   WARNING : SimpleFileEncryption is a basic file encryption tool provided for educational purposes only.");
            System.out.println("                " + "It uses a simple XOR encryption method and should not be used for secure data encryption.");
            System.out.println("                " + "Use at your own risk, and always back up your files before encryption. The developers are not responsible for any data loss or damages caused by the tool's usage.");
            System.out.println("\n" + "    Note : Use Command Prompt or any other terminal to run commands.");

            // Check command-line arguments
            if (args.length != 3 || (!args[0].equals("encrypt") && !args[0].equals("decrypt"))) {
                System.out.println("Usage: java FileEncryption <encrypt/decrypt> <inputFileName> <outputFileName>");
                return;
            }

            // Get user input from command line
            String option = args[0];
            String inputFileName = args[1];
            String outputFileName = args[2];

            // Perform encryption or decryption based on the user's choice
            if (option.equals("encrypt")) {
                encryptFile(inputFileName, outputFileName);
                System.out.println("Your file is successfully encrypted. Encrypted file name: " + outputFileName + "          " + formattedDateTime);
            } else if (option.equals("decrypt")) {
                decryptFile(inputFileName, outputFileName);
                System.out.println("Your file is successfully decrypted.                      " + formattedDateTime);
            } else {
                System.out.println("Invalid option. Use 'encrypt' or 'decrypt'.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void encryptFile(String inputFileName, String outputFileName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inputFileName);
             FileOutputStream outputStream = new FileOutputStream(outputFileName)) {

            int data;
            while ((data = inputStream.read()) != -1) {
                data = data + ENCRYPTION_KEY; // Simple XOR encryption using the key
                outputStream.write(data);
            }
        }
    }

    private static void decryptFile(String inputFileName, String outputFileName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inputFileName);
             FileOutputStream outputStream = new FileOutputStream(outputFileName)) {

            int data;
            while ((data = inputStream.read()) != -1) {
                data = data - ENCRYPTION_KEY; // Simple XOR decryption using the key
                outputStream.write(data);
            }
        }
    }
}
