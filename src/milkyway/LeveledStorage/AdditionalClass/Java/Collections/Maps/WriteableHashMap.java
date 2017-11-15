package milkyway.LeveledStorage.AdditionalClass.Java.Collections.Maps;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.WritableMap;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;

import java.util.HashMap;

public class WriteableHashMap<K,V> extends WritableMap<K,V>{
    private static int hash = "WritableHashMap".hashCode();

    public WriteableHashMap(HashMap<K, V> mapToSave) throws TypeNotSupportedException {
        super(mapToSave);
    }

    @Override
    public int getDataID() {
        return hash;
    }
}
