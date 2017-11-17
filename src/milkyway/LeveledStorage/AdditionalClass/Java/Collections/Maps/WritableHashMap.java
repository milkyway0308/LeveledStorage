package milkyway.LeveledStorage.AdditionalClass.Java.Collections.Maps;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.WritableMap;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;

import java.util.HashMap;

public class WritableHashMap<K,V> extends WritableMap<K,V>{
    private static int hash = "WritableHashMap".hashCode();

    public WritableHashMap(HashMap<K, V> mapToSave) throws TypeNotSupportedException {
        super(mapToSave);
    }

    public WritableHashMap() throws TypeNotSupportedException {
        super(new HashMap<>());
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public WritableData getNewInstance() {
        try {
            return new WritableHashMap<>();
        } catch (TypeNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
