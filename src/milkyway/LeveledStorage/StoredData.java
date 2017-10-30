package milkyway.LeveledStorage;

import milkyway.LeveledStorage.Exception.SeparatorIncorrectException;
import milkyway.LeveledStorage.Util.GenericsResolver.GenericsResolver;
import milkyway.LeveledStorage.Util.GenericsResolver.ResolverStorage;
import milkyway.LeveledStorage.Util.GenericsResolver.TypeResolver;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StoredData<Key, Data> implements Comparable<StoredData<Key, Data>> {

    private static int IntegerSeparator = 282167487;

    private static double DoubleSeparator = -249181242276632189412d;

    private HashMap<Key, Data> map = new HashMap<>();

    private int priority = -1;

    private int saveTime = 0;

    private String fileType;

    private String folder;

    StoredData(String folder, String fileType) {
        this.fileType = fileType;
        this.folder = folder;
    }

    public Data accessData(Key key, StoredData<Key, Data> upperStorage) {
        Data value = map.get(key);
        if (value == null)
            return null;
        if (priority != 0 && upperStorage != null && upperStorage != this) {
            map.remove(key);
            upperStorage.storeData(key, value);
        }
        return value;
    }

    public Data storeData(Key key, Data value) {
        return map.put(key, value);
    }


    void moveData(StoredData<Key, Data> next) {
        Iterator<Map.Entry<Key, Data>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Key, Data> kd = iterator.next();
            next.storeData(kd.getKey(), kd.getValue());
        }
        map.clear();
    }


    StoredData<Key, Data> loadData(File l) {
        if (l.exists()) {
            try {
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream(l));
                int firstInteger = stream.readInt();
                double firstDouble = stream.readDouble();
                if (firstInteger != IntegerSeparator || firstDouble != DoubleSeparator)
                    throw new SeparatorIncorrectException();
                TypeResolver keyResolver = ResolverStorage.getResolver(stream.readInt());
                TypeResolver valueResolver = ResolverStorage.getResolver(stream.readInt());
                if (keyResolver == null || valueResolver == null) {
                    // System.out.println("Resolver null");
                    return this;
                }
                while (stream.available() > 0) {
                    Key k = (Key) keyResolver.readObject(stream);
                    Data v = (Data) valueResolver.readObject(stream);

                    storeData(k, v);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //System.out.println(map);
        return this;
    }

    void saveData() {
        File locations = new File(folder + "/" + fileType.replace("<Number>", String.valueOf(priority)));
        if (locations.exists())
            locations.delete();
        locations.getParentFile().mkdirs();
        try {
            locations.createNewFile();
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(locations));
            stream.writeInt(IntegerSeparator);
            stream.writeDouble(DoubleSeparator);
            GenericsResolver.ItemType resolve = null;
            GenericsResolver.ItemType resolveVAl = null;
            boolean isResolved = false;
            if (map.size() <= 0) {
                stream.writeInt(0);
                stream.writeInt(0);
            } else
                for (Map.Entry<Key, Data> kd : map.entrySet()) {
                    if (!isResolved) {
                        isResolved = true;
                        resolve = GenericsResolver.resolve(kd.getKey());
                        resolveVAl = GenericsResolver.resolve(kd.getValue());
                        stream.writeInt(resolve.name().hashCode());
                        stream.writeInt(resolveVAl.name().hashCode());
                        resolve.getResolver().writeObject(stream, kd.getKey());
                        resolveVAl.getResolver().writeObject(stream, kd.getValue());
                    } else {
                        resolve.getResolver().writeObject(stream, kd.getKey());
                        resolveVAl.getResolver().writeObject(stream, kd.getValue());
                    }

                }
            stream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            locations.delete();
        }
    }

    boolean doSave() {
        boolean ret = saveTime >= priority;
        saveTime = 0;
        return ret;
    }

    boolean addTime() {
        saveTime++;
        return saveTime >= priority;
    }

    StoredData<Key, Data> setStoragePriority(int pr) {
        priority = pr;
        return this;
    }


    @Override
    public int compareTo(StoredData<Key, Data> o) {
        return Integer.compare(o.priority, priority);
    }

    @Override
    public String toString() {
        return "{Priority: " + priority + ",Items: " + map.toString() + "}";
    }


    void printMap() {
        System.out.println(map);
    }
}
