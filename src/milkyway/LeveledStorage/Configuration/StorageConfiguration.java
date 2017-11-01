package milkyway.LeveledStorage.Configuration;

public class StorageConfiguration {


    /*
        비동기 데이터 이동을 사용할지의 여부입니다.
        비동기 데이터 사용시, 외부 스레드에서 데이터 이동이 처리되지만
        이동중에는 데이터 접근이 불가능합니다.
     */
    private boolean isDataMoveAsync = false;

    /*
        비동기 저장을 사용할지의 여부입니다.
        비동기 저장 사용시, 저장이 외부 스레드에서 실행됩니다.
     */
    private boolean isSaveAsync     = false;

    /*
        파일의 저장 주기를 의미합니다.
        해당 주기마다 모든 스토리지의 저장 카운터가 1 증가하며,
        현재 스토리지 레벨 > 저장 카운터가 되면 해당 스토리지의 저장이 시도됩니다.
     */
    private int     saveCycle       = 0;

    public boolean isSaveAsync() {
        return isSaveAsync;
    }

    public void setSaveAsync(boolean saveAsync) {
        isSaveAsync = saveAsync;
    }

    public boolean isDataMoveAsync() {
        return isDataMoveAsync;
    }

    public void setDataMoveAsync(boolean dataMoveAsync) {
        isDataMoveAsync = dataMoveAsync;
    }

    public int getSaveCycle() {
        return saveCycle;
    }

    public void setSaveCycle(int saveCycle) {
        this.saveCycle = saveCycle;
    }
}
