package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl;

import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.AdditionalData;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableSkullMeta extends AdditionalData {

    private static int hash = "Minecraft - WritableSkullMeta".hashCode();

    private String skullOwner = "Notch";

    public WritableSkullMeta(ItemMeta meta) {
        if (meta instanceof SkullMeta)
            skullOwner = ((SkullMeta) meta).getOwner();
    }

    public WritableSkullMeta() {

    }

    @Override
    public void setToMeta(ItemMeta meta) {
        if (meta instanceof SkullMeta)
            ((SkullMeta) meta).setOwner(skullOwner);
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeUTF(skullOwner);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        skullOwner = stream.readUTF();
    }

    @Override
    public WritableData getNewInstance() {
        return null;
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if (flavor == ObjectType.ORIGINAL)
            return skullOwner;

        throw new ObjectNotSupportedException(flavor, this.getClass());

    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        if (flavor == ObjectType.ORIGINAL)
            return skullOwner;
        throw new ObjectNotSupportedException(flavor, this.getClass());
    }
}
