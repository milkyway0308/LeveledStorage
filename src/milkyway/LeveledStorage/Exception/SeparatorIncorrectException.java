package milkyway.LeveledStorage.Exception;

public class SeparatorIncorrectException extends Exception {
    public SeparatorIncorrectException() {
        super("해당 파일의 구별 인식자가 LevelStorage 인식자가 아닙니다.");
    }
}
