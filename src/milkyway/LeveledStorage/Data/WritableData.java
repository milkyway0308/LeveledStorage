package milkyway.LeveledStorage.Data;

import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface WritableData {


    int getDataID();

    void writeData(ObjectOutputStream stream) throws IOException;

    void readData(ObjectInputStream stream) throws IOException;

    WritableData getNewInstance();

    Object getData(ObjectType flavor) throws ObjectNotSupportedException;

    Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException;
}
