package milkyway.LeveledStorage.AdditionalClass.Java.Collections.Lists;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.WritableList;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;

import java.util.ArrayList;
import java.util.List;

public class WritableArrayList<T> extends WritableList<T>{
    private static int hash = "WritableArrayList".hashCode();

    public WritableArrayList(ArrayList<T> list) throws TypeNotSupportedException {
        super(list);
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public WritableData getNewInstance() {
        try {
            return new WritableArrayList<>(new ArrayList<>());
        } catch (TypeNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
