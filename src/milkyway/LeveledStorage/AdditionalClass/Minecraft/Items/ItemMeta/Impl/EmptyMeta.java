package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl;

import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.AdditionalData;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EmptyMeta extends AdditionalData {
    private static int hash = "Minecraft - EmptyMeta".hashCode();

    @Override
    public void setToMeta(ItemMeta meta) {

    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {

    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {

    }

    @Override
    public WritableData getNewInstance() {
        return new EmptyMeta();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        return null;
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        return null;
    }
}
