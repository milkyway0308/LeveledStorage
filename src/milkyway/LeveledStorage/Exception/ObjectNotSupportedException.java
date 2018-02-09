package milkyway.LeveledStorage.Exception;

import milkyway.LeveledStorage.Enums.ObjectType;

public class ObjectNotSupportedException extends Exception{
    public ObjectNotSupportedException(ObjectType type,Class classItem){
        super("Object type " + (type == null ? "null" : type.name()) + " at " + classItem.getName() + "is not supported");
    }
}
