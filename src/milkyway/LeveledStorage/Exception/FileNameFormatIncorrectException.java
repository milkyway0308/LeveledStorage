package milkyway.LeveledStorage.Exception;

public class FileNameFormatIncorrectException extends LeveledStorageException {
    public FileNameFormatIncorrectException() {
        super("LeveledStorage file name have to contains <Number> one or more than one.");
    }
}
