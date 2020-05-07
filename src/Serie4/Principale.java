package Serie4;

import java.sql.SQLException;

public class Principale {
    public static void main(String[] args) {
        try {
            MainWindow f = new MainWindow();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

