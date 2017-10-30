package milkyway.LeveledStorage.Util.GenericsResolver;


import milkyway.LeveledStorage.Data.DataWritableRegistry;
import milkyway.LeveledStorage.Data.WritableData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Developer_Unlocated on 2017-05-11.
 */
public interface TypeResolver {
    boolean canResolve(Object obj);

    Object resolve(Object obj);

    Object readObject(ObjectInputStream stream) throws IOException;

    void writeObject(ObjectOutputStream stream, Object obj) throws IOException;

    class StringResolver implements TypeResolver {

        @Override
        public boolean canResolve(Object obj) {
            return obj == null || obj instanceof String;
        }

        public Object resolve(Object obj) {
            if (obj != null)
                return obj.toString();

            return null;
        }

        @Override
        public Object readObject(ObjectInputStream stream) throws IOException {
            return stream.readUTF();
        }

        @Override
        public void writeObject(ObjectOutputStream stream, Object obj) throws IOException {

            stream.writeUTF((String) obj);

        }
    }

    class WritableResolver implements TypeResolver {

        @Override
        public boolean canResolve(Object obj) {
            return obj == null || obj instanceof WritableData;
        }

        public Object resolve(Object obj) {
            return  obj;
        }

        @Override
        public Object readObject(ObjectInputStream stream) throws IOException {
            WritableData writeable = DataWritableRegistry.getRegisteredWritable(stream.readInt());
            if (writeable != null)
                writeable.readData(stream);
            return writeable;
        }

        @Override
        public void writeObject(ObjectOutputStream stream, Object obj) throws IOException {
            WritableData data = (WritableData) obj;
            stream.writeInt(data.getDataID());
            ((WritableData) obj).writeData(stream);
        }
    }

    class NullResolver implements TypeResolver {

        @Override
        public boolean canResolve(Object obj) {
            return obj == null;
        }

        public Object resolve(Object obj) {
            return null;
        }

        @Override
        public Object readObject(ObjectInputStream stream) throws IOException {
            try {
                return stream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void writeObject(ObjectOutputStream stream, Object obj) throws IOException {
            stream.writeObject(null);
        }
    }

    class LongResolver implements TypeResolver {

        @Override
        public boolean canResolve(Object obj) {
            return obj == null || obj instanceof Long;
        }

        public Object resolve(Object obj) {
            if (obj != null)
                return Long.parseLong(obj.toString());

            return null;
        }

        @Override
        public Object readObject(ObjectInputStream stream) throws IOException {
            return stream.readLong();
        }

        @Override
        public void writeObject(ObjectOutputStream stream, Object obj) throws IOException {
            stream.writeLong((Long) obj);
        }
    }

    class IntegerResolver implements TypeResolver {

        @Override
        public boolean canResolve(Object obj) {
            return obj == null || obj instanceof Integer;
        }

        public Object resolve(Object obj) {
            if (obj != null && obj instanceof String)
                try {
                    return Integer.parseInt((String) obj);
                } catch (Exception ignored) {
                }
            return obj;
        }

        @Override
        public Object readObject(ObjectInputStream stream) throws IOException {
            return stream.readInt();
        }

        @Override
        public void writeObject(ObjectOutputStream stream, Object obj) throws IOException {
            stream.writeInt((Integer) obj);
        }
    }

    class ByteResolver implements TypeResolver {

        @Override
        public boolean canResolve(Object obj) {
            return obj == null || obj instanceof Byte;
        }

        public Object resolve(Object obj) {
            if (obj != null && obj instanceof String)
                try {
                    return Byte.parseByte((String) obj);
                } catch (Exception ignored) {
                }
            return obj;
        }

        @Override
        public Object readObject(ObjectInputStream stream) throws IOException {
            return stream.readByte();
        }

        @Override
        public void writeObject(ObjectOutputStream stream, Object obj) throws IOException {
            stream.writeByte((byte) obj);
        }
    }

    class DoubleResolver implements TypeResolver {

        @Override
        public boolean canResolve(Object obj) {
            return obj == null || obj instanceof Double;
        }

        public Object resolve(Object obj) {
            if (obj != null && obj instanceof String)
                try {
                    return Double.parseDouble((String) obj);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            return obj;
        }

        @Override
        public Object readObject(ObjectInputStream stream) throws IOException {
            return stream.readDouble();
        }

        @Override
        public void writeObject(ObjectOutputStream stream, Object obj) throws IOException {
            stream.writeDouble((Double) obj);
        }
    }

    class FloatResolver implements TypeResolver {

        @Override
        public boolean canResolve(Object obj) {
            return obj == null || obj instanceof Float;
        }

        public Object resolve(Object obj) {
            if (obj != null && obj instanceof String)
                try {
                    return Float.parseFloat((String) obj);
                } catch (Exception ignored) {
                }
            return obj;
        }

        @Override
        public Object readObject(ObjectInputStream stream) throws IOException {
            return stream.readFloat();
        }

        @Override
        public void writeObject(ObjectOutputStream stream, Object obj) throws IOException {
            stream.writeFloat((Float) obj);
        }
    }

    class EmprtyResolver implements TypeResolver {

        @Override
        public boolean canResolve(Object obj) {
            return true;
        }

        public Object resolve(Object obj) {
            return obj;
        }

        @Override
        public Object readObject(ObjectInputStream stream) throws IOException {
            return null;
        }

        @Override
        public void writeObject(ObjectOutputStream stream, Object obj) throws IOException {

        }
    }

}
