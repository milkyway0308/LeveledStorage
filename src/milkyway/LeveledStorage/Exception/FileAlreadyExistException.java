package milkyway.LeveledStorage.Exception;

public class FileAlreadyExistException extends LeveledStorageException {
    public FileAlreadyExistException(String location) {
        super("File " + location + " is not folder.");
    }
}
