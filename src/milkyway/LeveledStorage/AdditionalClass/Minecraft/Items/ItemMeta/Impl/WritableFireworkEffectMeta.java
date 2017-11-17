package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl;

import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Fireworks.WritableFireworkEffect;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.AdditionalData;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableFireworkEffectMeta extends AdditionalData {
    private static int hash = "Minecraft - WritableFireworkEffectMeta".hashCode();

    private WritableFireworkEffect wf;

    private boolean hasFirework  = false;

    public WritableFireworkEffectMeta(){

    }

    public WritableFireworkEffectMeta(ItemMeta meta){
        if(meta instanceof FireworkEffectMeta){
            FireworkEffectMeta fm = (FireworkEffectMeta) meta;
            if(fm.hasEffect())
            {
                hasFirework = true;
                wf = new WritableFireworkEffect(fm.getEffect());
            }
        }
    }

    @Override
    public void setToMeta(ItemMeta meta) {
        if(meta instanceof FireworkEffectMeta){
            if(wf != null)
                try {
                    ((FireworkEffectMeta) meta).setEffect((FireworkEffect) wf.getData(ObjectType.ORIGINAL));
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
        stream.writeBoolean(hasFirework);
        if(wf != null)
            wf.writeData(stream);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        if(stream.readBoolean())
            (wf = new WritableFireworkEffect()).readData(stream);
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableFireworkEffectMeta();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL)
            return wf;
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        return getData(flavor);
    }
}
