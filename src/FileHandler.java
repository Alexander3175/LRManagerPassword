import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String PASSWORD_FILE = "passwords.txt";

    public List<String> loadPasswords() {
        List<String> passwords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PASSWORD_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                passwords.add(line);
            }
        } catch (IOException e) {
            System.out.println("Файл паролів не знайдено, створюється новий файл.");
        }
        return passwords;
    }

    public void savePasswords(List<String> passwords) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PASSWORD_FILE))) {
            for (String password : passwords) {
                bw.write(password);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Помилка збереження паролів.");
        }
    }
}
