package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl;

import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.AdditionalData;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableSpawnedEggMeta extends AdditionalData {
    private static int hash = "Minecraft - WritableSpawnedEggMeta".hashCode();

    private String type;

    public WritableSpawnedEggMeta(){

    }

    public WritableSpawnedEggMeta(ItemMeta meta){
        if(meta instanceof SpawnEggMeta){
            type = ((SpawnEggMeta) meta).getSpawnedType().name();
        }
    }
    @Override
    public void setToMeta(ItemMeta meta) {
        if(meta instanceof SpawnEggMeta){
            ((SpawnEggMeta) meta).setSpawnedType(EntityType.valueOf(type));
        }
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeUTF(type);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        type = stream.readUTF();
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableSpawnedEggMeta();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL)
            return type;
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        return getData(flavor);
    }
}
