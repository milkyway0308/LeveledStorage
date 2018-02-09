package milkyway.LeveledStorage.Data;

import java.util.HashMap;

public class DataWritableRegistry {
    private static HashMap<Integer, WritableData> registered = new HashMap<>();

    public static void registerWritable(WritableData dat) {
        registered.put(dat.getDataID(), dat);
    }

    public static WritableData getRegisteredWritable(Integer id) {
        return registered.get(id).getNewInstance();
    }
}
