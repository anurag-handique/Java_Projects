package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;

    UserView(String email) {
        this.email = email;
    }

    public void home() {
        do {
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                 Welcome Back!!                                            ║");
            System.out.println("║                                                                                           ║");
            System.out.println("║ Press 1 to show hidden files                                                              ║");
            System.out.println("║ Press 2 to hide a new file                                                                ║");
            System.out.println("║ Press 3 to un-hide a file                                                                 ║");
            System.out.println("║ Press 0 to exit                                                                           ║");
            System.out.println("║                                                                                           ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════╝");

            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("╔════════════════════════════════════════╗");
                        System.out.println("║        ID         |     File Name      ║");
                        System.out.println("╟───────────────────┼────────────────────╢");
                        for (Data file : files) {
                            System.out.printf("║  %-15d | %-20s ║%n", file.getId(), file.getFileName());
                        }
                        System.out.println("╚════════════════════════════════════════╝");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                    System.out.println("Enter the file path:");
                    System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 3 -> {
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFiles(this.email);
                        System.out.println("╔════════════════════════════════════════╗");
                        System.out.println("║        ID         |      File Name     ║");
                        System.out.println("╟───────────────────┼────────────────────╢");
                        for (Data file : files) {
                            System.out.printf("║  %-15d | %-20s ║%n", file.getId(), file.getFileName());
                        }
                        System.out.println("╚════════════════════════════════════════╝");
                        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                        System.out.println("Enter the id of the file to un-hide:");
                        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean isValidID = false;
                        for (Data file : files) {
                            if (file.getId() == id) {
                                isValidID = true;
                                break;
                            }
                        }
                        if (isValidID) {
                            DataDAO.unhide(id);
                        } else {
                            System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                            System.out.println("Wrong ID....Please enter a valid ID");
                            System.out.println("════════════════════════════════════════════════════════════════════════════════════════════");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 0 -> {
                    System.exit(0);
                }
            }
        } while (true);
    }
}
