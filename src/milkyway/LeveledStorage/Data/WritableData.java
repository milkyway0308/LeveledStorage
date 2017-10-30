package milkyway.LeveledStorage.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class WritableData {


    public abstract int getDataID();

    public abstract void writeData(ObjectOutputStream stream) throws IOException;

    public abstract void readData(ObjectInputStream stream) throws IOException;

    public abstract WritableData getNewInstance();
}
