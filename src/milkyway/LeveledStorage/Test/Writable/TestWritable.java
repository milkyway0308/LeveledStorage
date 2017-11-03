package milkyway.LeveledStorage.Test.Writable;

import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestWritable implements WritableData {
    private int dat;

    public TestWritable(int dat) {
        this.dat = dat;
    }

    @Override
    public int getDataID() {
        return "TestItem".hashCode();
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeInt(dat);

    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        dat = stream.readInt();
    }

    @Override
    public WritableData getNewInstance() {
        return new TestWritable(0);
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL)
            return dat;
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL)
            return dat;
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public String toString() {
        return String.valueOf(dat);
    }
}
