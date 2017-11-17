package milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Potion;

import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Colors.WritableBukkitColor;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritablePotionEffect implements WritableData{
    private static int hash = "Minecraft - WritablePotionEffect".hashCode();

    private int potionID;

    private int potionLv;

    private int potionDuration;

    private WritableBukkitColor color;

    private boolean isAmbient = false;

    private boolean hasParticle = false;

    public WritablePotionEffect() {

    }

    public WritablePotionEffect(PotionEffect effect){
        potionID = effect.getType().getId();

        potionLv = effect.getAmplifier();

        potionDuration = effect.getDuration();

        if(effect.getColor() != null)
          color = new WritableBukkitColor(effect.getColor());

        isAmbient  = effect.isAmbient();

        hasParticle = effect.hasParticles();

        System.out.println("Potion ID: " + potionID);
    }
    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeInt(potionID);
        stream.writeInt(potionLv);
        stream.writeInt(potionDuration);
        stream.writeBoolean(isAmbient);
        stream.writeBoolean(hasParticle);
        if(color != null){
            stream.writeBoolean(true);
            color.writeData(stream);
        }else
            stream.writeBoolean(false);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        potionID = stream.readInt();
        potionLv = stream.readInt();
        potionDuration = stream.readInt();
        isAmbient = stream.readBoolean();
        hasParticle = stream.readBoolean();
        if(stream.readBoolean())
            (color = new WritableBukkitColor()).readData(stream);
        Bukkit.getConsoleSender().sendMessage("Potion ID:" + potionID);
    }

    @Override
    public WritableData getNewInstance() {
        return new WritablePotionEffect();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor  == ObjectType.ORIGINAL)
        {
            return new PotionEffect(PotionEffectType.getById(potionID),potionDuration,potionLv,isAmbient,hasParticle,
                    (color !=  null) ? (Color) color.getData(ObjectType.ORIGINAL) : null);
        }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        switch (flavor){
            case ORIGINAL:
                return new PotionEffect(PotionEffectType.getById(potionID),potionDuration,potionLv,isAmbient,hasParticle,
                        (color !=  null) ? (Color) color.getData(ObjectType.ORIGINAL) : null);
            case INTEGER:
                switch (item){
                    case "id":
                        return potionID;
                    case "duration":
                        return potionDuration;
                    case "level":
                    case "lv":
                        return potionLv;
                }
            case OBJECT:
                switch (item){
                    case "ambient":
                        return isAmbient;
                    case "particle":
                        return hasParticle;
                    case "color":
                        return color;
                }

        }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }
}
