package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.Lists.WritableArrayList;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Enchants.WritableEnchant;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.AdditionalData;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WritableEnchantBookMeta extends AdditionalData {
    private static int hash = "Minecraft - WritableEnchantBookMeta".hashCode();

    private WritableArrayList<WritableEnchant> data;

    public WritableEnchantBookMeta(){
        try {
            data = new WritableArrayList<>(new ArrayList<>());
        } catch (TypeNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public WritableEnchantBookMeta(ItemMeta meta){
        this();
        if(meta instanceof EnchantmentStorageMeta){
            try {
                List<WritableEnchant> list = (List<WritableEnchant>) data.getData(ObjectType.ORIGINAL);
                for(Map.Entry<Enchantment,Integer> ench : ((EnchantmentStorageMeta) meta).getStoredEnchants().entrySet()){
                    list.add(new WritableEnchant(ench.getKey(),ench.getValue()));
                }
            } catch (ObjectNotSupportedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void setToMeta(ItemMeta meta) {
        if (meta instanceof EnchantmentStorageMeta){
            EnchantmentStorageMeta em = (EnchantmentStorageMeta) meta;
            try {
                for(WritableEnchant e : ((List<WritableEnchant>)data.getData(ObjectType.ORIGINAL)))
                    em.addStoredEnchant((Enchantment) e.getData(ObjectType.ORIGINAL),(int)e.getData(ObjectType.INTEGER),true);
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
        data.writeData(stream);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        data.readData(stream);
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableEnchantBookMeta();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL)
            return data;
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        return getData(flavor);
    }
}
