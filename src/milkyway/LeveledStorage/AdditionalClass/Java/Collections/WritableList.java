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
import java.util.List;

public abstract class WritableList<T> implements WritableData {
    private List<T> inList;
    private TypeResolver type;

    public WritableList(List<T> list) throws TypeNotSupportedException {
        inList = list;
        if (list.size() <= 0)
            return;
        try {
            type = GenericsResolver.resolveGenerics(inList).getResolver();
        } catch (CollectionsNullException e) {
            e.printStackTrace();
        }
    }

    protected List<T> getList() {
        return inList;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        if (type != null)
            stream.writeInt(type.getResolver().name().hashCode());
        else
            stream.writeInt(-1);
        stream.writeInt(inList.size());
        for (T t : inList)
            type.writeObject(stream, t);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        int type = stream.readInt();
        if (type != -1) {
            this.type = ResolverStorage.getResolver(type);
            int size = stream.readInt();
            //  System.out.println(size);
            for (int i = 0; i < size; i++) {
                T t = (T) this.type.readObject(stream);
                // System.out.println(t);
                inList.add(t);
            }
        }else
            stream.readInt();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if (flavor == ObjectType.ORIGINAL)
            return inList;
        throw new ObjectNotSupportedException(flavor, this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        if (flavor == ObjectType.ORIGINAL)
            return inList;
        throw new ObjectNotSupportedException(flavor, this.getClass());
    }
}
