//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.WindowConstants;
//import java.awt.BorderLayout;
//import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * Выводит окно со временем запуска прораммы и дублирует время в файл.
 */
public class Cron {
    private static final File LOG_FILE = new File(System.getenv("HOME") + "/tmp/cron.log");
    private static final DateFormat format = DateFormat.getDateTimeInstance();
    public static void main(String[] args) throws IOException {
        String message = "<no message>";
        if (args.length > 0) {
            message = args[0];
        }
        String date = format.format(new Date());
        if (!LOG_FILE.exists()) {
            LOG_FILE.createNewFile();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            bw.append(date +" " + message + "\n");
        }
//        new UiNotify(date, message);

    }
}

//class UiNotify extends JFrame {
//    UiNotify(String date, String message) throws HeadlessException {
//        setTitle("Cron executed me!");
//        setSize(800, 600);
//        setVisible(true);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        add(new JLabel(date));
//        add(new JLabel(message), BorderLayout.SOUTH);
//    }
//}