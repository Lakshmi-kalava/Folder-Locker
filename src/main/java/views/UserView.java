
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
            System.out.println("HEYY " + this.email);
            System.out.println("Press 1 to SHOW LOCKED FILES");
            System.out.println("Press 2 to LOCK A NEW FILE");
            System.out.println("Press 3 to UNLOCK A NEW FILE");
            System.out.println("Press 0 to EXIT");
            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("ID - FILE NAME");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("ENTER THE FILE PATH");
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
                    System.out.println("FILE LOCKED SUCCESSFULLY!");
                }
                case 3 -> {
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFiles(this.email);

                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                        System.out.println("ENTER THE ID OF THE FILE TO BE UNLOCKED.");
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
                            System.out.println("ID DOES NOT EXIST.TRY AGAIN!");
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

