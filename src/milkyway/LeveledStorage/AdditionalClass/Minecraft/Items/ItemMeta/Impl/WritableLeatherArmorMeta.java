package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl;

import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Colors.WritableBukkitColor;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.AdditionalData;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableLeatherArmorMeta extends AdditionalData{
    private static int hash = "Minecraft - WritableLeatherArmorMeta".hashCode();

    private WritableBukkitColor color;

    public WritableLeatherArmorMeta(){

    }

    public WritableLeatherArmorMeta(ItemMeta meta) {
        if(meta instanceof LeatherArmorMeta){
            LeatherArmorMeta lm = (LeatherArmorMeta) meta;
            color = new WritableBukkitColor(lm.getColor());
        }
    }

    @Override
    public void setToMeta(ItemMeta meta) {
        if(meta instanceof LeatherArmorMeta) {
            LeatherArmorMeta lm = (LeatherArmorMeta) meta;
            try {
                lm.setColor((Color) color.getData(ObjectType.ORIGINAL));
            } catch (ObjectNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        color.writeData(stream);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        color.readData(stream);
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableLeatherArmorMeta();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL)
            return color;
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        return getData(flavor);
    }
}
