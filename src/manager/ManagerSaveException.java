package manager;


/**
 * Класс создает собственное непроверяемое исключение
 */
public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException() {
    }

    public ManagerSaveException(final String message) {
        super(message);
    }
}
