public class Main {
    public static void main(String[] args) {
        try {
            PasswordManager manager = new PasswordManager();
            manager.start();
        } catch (Exception e) {
            System.out.println("Помилка запуску програми: " + e.getMessage());
        }
    }
}
