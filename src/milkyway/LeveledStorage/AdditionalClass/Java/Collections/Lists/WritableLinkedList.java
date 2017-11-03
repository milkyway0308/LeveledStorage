package milkyway.LeveledStorage.AdditionalClass.Java.Collections.Lists;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.WritableList;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;

import java.util.LinkedList;

public class WritableLinkedList<T> extends WritableList<T>{
    private static int hash = "WritableLinkedList".hashCode();
    public WritableLinkedList(LinkedList<T> list) throws TypeNotSupportedException {
        super(list);
    }


    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public WritableData getNewInstance() {
        try {
            return new WritableLinkedList<>(new LinkedList<>());
        } catch (TypeNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
