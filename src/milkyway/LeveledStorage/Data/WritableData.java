package milkyway.LeveledStorage.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface WritableData {


    int getDataID();

    void writeData(ObjectOutputStream stream) throws IOException;

    void readData(ObjectInputStream stream) throws IOException;

    WritableData getNewInstance();
}
