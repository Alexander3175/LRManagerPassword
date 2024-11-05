import java.util.List;
import java.util.Scanner;

public class PasswordManager {
    private List<String> passwords;
    private SQLHandler sqlHandler;
    private PasswordEncryptor encryptor;
    private PasswordGenerator generator;

    public PasswordManager() throws Exception {
        this.sqlHandler = new SQLHandler();
        this.encryptor = new PasswordEncryptor();
        this.generator = new PasswordGenerator();
        this.passwords = sqlHandler.loadPasswords();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть майстер-пароль для доступу: ");
        String masterPassword = scanner.nextLine();
        if (!authenticate(masterPassword)) {
            System.out.println("Невірний майстер-пароль!");
            return;
        }

        while (true) {
            System.out.println("\nМенеджер паролів:");
            System.out.println("1. Додати пароль");
            System.out.println("2. Переглянути всі паролі");
            System.out.println("3. Видалити пароль");
            System.out.println("4. Згенерувати безпечний пароль");
            System.out.println("5. Вийти");
            System.out.print("Виберіть дію: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> addPassword(scanner);
                    case 2 -> viewPasswords();
                    case 3 -> deletePassword(scanner);
                    case 4 -> System.out.println("Згенерований пароль: " + generator.generatePassword(12));
                    case 5 -> {
                        sqlHandler.close();
                        System.out.println("Програма завершена.");
                        return;
                    }
                    default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
                }
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private void addPassword(Scanner scanner) throws Exception {
        System.out.print("Введіть назву сайту або сервісу: ");
        String site = scanner.nextLine();
        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();
        String encryptedPassword = encryptor.encrypt(site + " : " + password);
        sqlHandler.savePassword(encryptedPassword);
        passwords.add(encryptedPassword);
        System.out.println("Пароль додано!");
    }

    private void viewPasswords() throws Exception {
        if (passwords.isEmpty()) {
            System.out.println("Список паролів порожній.");
        } else {
            System.out.println("Збережені паролі:");
            for (int i = 0; i < passwords.size(); i++) {
                String decryptedPassword = encryptor.decrypt(passwords.get(i));
                System.out.println((i + 1) + ". " + decryptedPassword);
            }
        }
    }

    private void deletePassword(Scanner scanner) throws Exception {
        viewPasswords();
        if (passwords.isEmpty()) return;

        System.out.print("Введіть номер пароля для видалення: ");
        int index = scanner.nextInt();
        if (index > 0 && index <= passwords.size()) {
            sqlHandler.deletePassword(index - 1);
            passwords.remove(index - 1);
            System.out.println("Пароль видалено.");
        } else {
            System.out.println("Невірний номер.");
        }
    }

    private boolean authenticate(String masterPassword) {
        return masterPassword.equals("myMasterPassword");
    }
}
