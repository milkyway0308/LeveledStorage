package milkyway.LeveledStorage.Util.GenericsResolver;


import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.CollectionsNullException;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Developer_Unlocated on 2017-05-10.
 */
public class GenericsResolver {
    public enum ItemType {
        String(new TypeResolver.StringResolver(), "SaveableNxNullPointerItemObject"), Integer(new TypeResolver.IntegerResolver(), 0), Double(new TypeResolver.DoubleResolver(), 0d), Float(new TypeResolver.FloatResolver(), 0f), Byte(new TypeResolver.ByteResolver(), (byte) 0), Long(new TypeResolver.LongResolver(), 0L), Empty(new TypeResolver.NullResolver(), null), Writable(new TypeResolver.WritableResolver(), null);

       ItemType(TypeResolver resolver, Object nullDefault) {
            solver = resolver;
            this.nullDefault = nullDefault;
            ResolverStorage.registerResolver(this);
        }

        public TypeResolver getResolver() {
            return solver;
        }

        private final Object nullDefault;

        public Object getNullDefaultObject() {
            return nullDefault;
        }

        private final TypeResolver solver;
    }

    public static ItemType resolveGenerics(List list) throws TypeNotSupportedException, CollectionsNullException {
        if (list.size() <= 0)
            return ItemType.Empty;
        for (Object entrys : list) {
            if (entrys != null)
                return resolve(entrys);
        }
        throw new CollectionsNullException();

    }

    public static ItemType[] resolveGenerics(Map map) throws TypeNotSupportedException, CollectionsNullException {
        if (map.size() <= 0)
            return new ItemType[]{ItemType.Empty, ItemType.Empty};
        Map.Entry entry = new ArrayList<Map.Entry>(map.entrySet()).get(0);
        for (Object entry_ : map.entrySet()) {
            Map.Entry ent = (Map.Entry) entry_;
            if (ent.getKey() != null && ent.getValue() != null) {
                entry = ent;
                break;
            }
        }
        if (entry == null)
            throw new CollectionsNullException();
        return new ItemType[]{resolve(entry.getKey()), resolve(entry.getValue())};

    }

    public static ItemType resolve(Object obj) throws TypeNotSupportedException {
        if (obj instanceof String)
            return ItemType.String;
        if (obj instanceof Integer)
            return ItemType.Integer;
        if (obj instanceof Double)
            return ItemType.Double;
        if (obj instanceof Float)
            return ItemType.Float;
        if (obj instanceof Long)
            return ItemType.Long;
        if (obj instanceof Byte)
            return ItemType.Byte;
        if (obj instanceof WritableData)
            return ItemType.Writable;
        throw new TypeNotSupportedException();
    }

}

