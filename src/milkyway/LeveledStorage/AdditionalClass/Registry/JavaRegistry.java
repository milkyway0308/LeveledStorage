package milkyway.LeveledStorage.AdditionalClass.Registry;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.Lists.WritableArrayList;
import milkyway.LeveledStorage.AdditionalClass.Java.Collections.Lists.WritableLinkedList;
import milkyway.LeveledStorage.Data.DataWritableRegistry;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;

import java.util.ArrayList;
import java.util.LinkedList;

public class JavaRegistry {

    public static void registerWritable(){
        registerCollection();
    }

    private static void registerCollection(){
        registerList();
    }

    private static void registerList(){
        try {
            DataWritableRegistry.registerWritable(new WritableArrayList<>(new ArrayList<>()));
            DataWritableRegistry.registerWritable(new WritableLinkedList<>(new LinkedList<>()));
        } catch (TypeNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
