package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.Lists.WritableArrayList;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Fireworks.WritableFireworkEffect;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.AdditionalData;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WritableFireworkMeta extends AdditionalData{
    private static int hash = "Minecraft - WritableFireworkMeta".hashCode();

    private WritableArrayList<WritableFireworkEffect> data;

    private int power = 0;
    public WritableFireworkMeta(){
        try {
            data = new WritableArrayList<>(new ArrayList<>());
        } catch (TypeNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public WritableFireworkMeta(ItemMeta meta){
        if(meta instanceof FireworkMeta){
            FireworkMeta fm = (FireworkMeta) meta;
            try {
                List<WritableFireworkEffect> list = (List<WritableFireworkEffect>) data.getData(ObjectType.ORIGINAL);
                if(fm.hasEffects())
                    for(FireworkEffect ef : fm.getEffects())
                        list.add(new WritableFireworkEffect(ef));
                power = fm.getPower();
            } catch (ObjectNotSupportedException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void setToMeta(ItemMeta meta) {
        if(meta instanceof FireworkMeta){
            FireworkMeta fm = (FireworkMeta) meta;
            for(WritableFireworkEffect ef : (List<WritableFireworkEffect>)data)
                try {
                    fm.addEffect((FireworkEffect) ef.getData(ObjectType.ORIGINAL));
                    fm.setPower(power);
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
        stream.writeInt(power);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        data.readData(stream);
        power = stream.readInt();
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableFireworkMeta();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL)
            return data;
        else if(flavor == ObjectType.INTEGER)
            return power;
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        return getData(flavor);
    }
}
