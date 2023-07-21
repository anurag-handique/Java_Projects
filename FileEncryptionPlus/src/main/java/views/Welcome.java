package views;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    public void welcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                               Welcome to FileEncryptionPlus                                  ║");
        System.out.println("║                              Keep your files safe and secure                                 ║");
        System.out.println("║ Disclaimer: Use FileEncryptionPlus app at your own risk. Hide files with encryption,         ║");
        System.out.println("║            but there is no absolute guarantee of protection. Comply with laws.               ║");
        System.out.println("║            No warranties, no liability for damages. Do not modify without permission.        ║");
        System.out.println("║                                                                                              ║");
        System.out.println("╟──────────────────────────────────────────────────────────────────────────────────────────────╢");
        System.out.println("║                                      Main Menu                                               ║");
        System.out.println("║                                                                                              ║");
        System.out.println("║ Press 1 -> Login as an existing user                                                         ║");
        System.out.println("║ Press 2 -> Sign-up as a new user                                                             ║");
        System.out.println("║ Press 0 -> Exit                                                                              ║");
        System.out.println("║                                                                                              ║");
        System.out.println("║                                                                                              ║");
        System.out.println("║                                                                                              ║");
        System.out.println("║                                                                                              ║");
        System.out.println("║                                                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════╝");

        int choice = 0;
        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        switch (choice) {
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> {
                System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                System.out.println("Thank you for using FileEncryptionPlus. Goodbye!");
                System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid choice. Please try again.");
                welcomeScreen();
            }
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                                        Login                                              ");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Enter your email: ");
        String email = sc.nextLine();
        try {
            if (UserDAO.isExists(email)) {
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                System.out.println("An OTP has been sent to your email. Please enter the OTP: ");
                System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                String otp = sc.nextLine();
                if (otp.equals(genOTP)) {
                    System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                    System.out.println("Login successful. Welcome back, " + email + "!");
                    System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                    new UserView(email).home();
                } else {
                    System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                    System.out.println("Wrong OTP... Try again!");
                    System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                }
            } else {
                System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                System.out.println("User not found. Please sign up now!");
                System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("                                    Sign Up                                              ");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        System.out.println("Enter your email: ");
        String email = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("An OTP has been sent to your email. Please enter the OTP: ");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
        String otp = sc.nextLine();
        if (otp.equals(genOTP)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);
            switch (response) {
                case 0 -> {
                    System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                    System.out.println("User registered successfully! Welcome to FileEncryptionPlus, " + name + "!");
                    System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                    new UserView(email).home();
                }
                case 1 -> {
                    System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                    System.out.println("User already exists. Please sign in!");
                    System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                }

            }
        } else {
            System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
            System.out.println("Wrong OTP... Please try again!");
            System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
        }
    }
}
