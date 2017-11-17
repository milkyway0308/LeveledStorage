package milkyway.LeveledStorage.AdditionalClass.Java.Collections;

import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.CollectionsNullException;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;
import milkyway.LeveledStorage.Util.GenericsResolver.GenericsResolver;
import milkyway.LeveledStorage.Util.GenericsResolver.ResolverStorage;
import milkyway.LeveledStorage.Util.GenericsResolver.TypeResolver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class WritableMap<K,V> implements WritableData{
    private Map<K,V> inClassMap;
    private TypeResolver key;
    private TypeResolver value;

    public WritableMap(Map<K,V> mapToSave) throws TypeNotSupportedException{
        inClassMap = mapToSave;
        if(inClassMap.size() <= 0)
            return;

        try {
            GenericsResolver.ItemType[] types = GenericsResolver.resolveGenerics(inClassMap);
            key = types[0].getResolver();
            value = types[1].getResolver();
        } catch (CollectionsNullException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        if((key == null || value == null) && inClassMap.size() >= 1){
            try {
                GenericsResolver.ItemType[] types = GenericsResolver.resolveGenerics(inClassMap);
                key = types[0].getResolver();
                value = types[1].getResolver();
            } catch (TypeNotSupportedException | CollectionsNullException ignored) {

            }
        }
        if(key == null || value == null){
            stream.writeInt(-1);
            stream.writeInt(-1);
            stream.writeInt(0);
        }else{
            stream.writeInt(key.getResolver().name().hashCode());
            stream.writeInt(value.getResolver().name().hashCode());
            stream.writeInt(inClassMap.size());
            for(Map.Entry<K,V> kv : inClassMap.entrySet())
            {
                key.writeObject(stream,kv.getKey());
                value.writeObject(stream,kv.getValue());
            }
        }
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        int key = stream.readInt();
        int value = stream.readInt();
        int size = stream.readInt();
        if(key == -1 || value == -1)
            return;
        TypeResolver keyResolver = ResolverStorage.getResolver(key);
        TypeResolver valueResolver = ResolverStorage.getResolver(value);
        for(int i = 0;i < size;i++){
            inClassMap.put((K)keyResolver.resolve(stream),(V)valueResolver.resolve(stream));
        }
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        return null;
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        return null;
    }

    public V put(K key,V value){
        return inClassMap.put(key, value);
    }

    public V remove(K Key){
        return inClassMap.remove(key);
    }

    public Set<Map.Entry<K,V>> entrySet(){
        return inClassMap.entrySet();
    }

    public V get(K key){
        return inClassMap.get(key);
    }

    public boolean containsKey(K key){
        return inClassMap.containsKey(key);
    }

    public Collection<V> values(){
        return inClassMap.values();
    }

    public Set<K> keySet(){
        return inClassMap.keySet();
    }



    public void clear(){
        inClassMap.clear();
    }
}
