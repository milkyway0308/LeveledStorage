package milkyway.LeveledStorage.Configuration;

public class StorageConfiguration {


    /*
        �񵿱� ������ �̵��� ��������� �����Դϴ�.
        �񵿱� ������ ����, �ܺ� �����忡�� ������ �̵��� ó��������
        �̵��߿��� ������ ������ �Ұ����մϴ�.
     */
    private boolean isDataMoveAsync = false;

    /*
        �񵿱� ������ ��������� �����Դϴ�.
        �񵿱� ���� ����, ������ �ܺ� �����忡�� ����˴ϴ�.
     */
    private boolean isSaveAsync     = false;

    /*
        ������ ���� �ֱ⸦ �ǹ��մϴ�.
        �ش� �ֱ⸶�� ��� ���丮���� ���� ī���Ͱ� 1 �����ϸ�,
        ���� ���丮�� ���� > ���� ī���Ͱ� �Ǹ� �ش� ���丮���� ������ �õ��˴ϴ�.
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
