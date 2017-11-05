package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.Lists.WritableArrayList;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Colors.WritableBukkitColor;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Potion.WritablePotionData;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Potion.WritablePotionEffect;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.AdditionalData;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WritablePotionMeta extends AdditionalData{
    private static int hash = "Minecraft  - WritablePotionMeta".hashCode();

    private WritablePotionData base;

    private WritableBukkitColor color;

    private WritableArrayList<WritablePotionEffect> effect;

    public WritablePotionMeta(){
        try {
            effect =  new WritableArrayList<>(new ArrayList<>());
        } catch (TypeNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public WritablePotionMeta(ItemMeta meta){
        if(meta instanceof PotionMeta) {
            PotionMeta pm = (PotionMeta) meta;
            base =  new WritablePotionData(pm.getBasePotionData());
            if(pm.hasColor())
               color = new WritableBukkitColor(pm.getColor());
            try {
                List<WritablePotionEffect> wf = (List<WritablePotionEffect>) effect.getData(ObjectType.ORIGINAL);
                for(PotionEffect effect : pm.getCustomEffects())
                    wf.add(new WritablePotionEffect(effect));
            } catch (ObjectNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void setToMeta(ItemMeta meta) {
        if(meta instanceof PotionMeta){
            PotionMeta pm = (PotionMeta) meta;
            try {
                pm.setBasePotionData((PotionData) base.getData(ObjectType.ORIGINAL));
                if(color != null)
                    pm.setColor((Color) color.getData(ObjectType.ORIGINAL));
                for(WritablePotionEffect effect :  (List<WritablePotionEffect>)this.effect.getData(ObjectType.ORIGINAL))
                    pm.addCustomEffect((PotionEffect) effect.getData(ObjectType.ORIGINAL),true);
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
        base.writeData(stream);
        effect.writeData(stream);
        if(color != null)
        {
            stream.writeBoolean(true);
            color.writeData(stream);
        }else
            stream.writeBoolean(false);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        (base = new WritablePotionData()).readData(stream);
        effect.readData(stream);
        if(stream.readBoolean())
            (color = new WritableBukkitColor()).readData(stream);

    }

    @Override
    public WritableData getNewInstance() {
        return new WritablePotionData();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        switch (flavor){
            case OBJECT:
                switch (item){
                    case "potiondata":
                        return base;
                    case "color":
                        return color;
                    case "effect":
                        return effect;
                }
        }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }
}
