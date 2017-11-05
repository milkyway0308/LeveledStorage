package milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Fireworks;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.Lists.WritableArrayList;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Colors.WritableBukkitColor;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WritableFireworkEffect implements WritableData{
    private static int hash = "Minecraft - WritableFireworkEffect".hashCode();

    private boolean hasFlicker = false;

    private boolean hasTrail = false;

    private WritableArrayList<WritableBukkitColor> fade;

    private WritableArrayList<WritableBukkitColor> color;

    private String type;

    public WritableFireworkEffect(){
        try {
            fade = new WritableArrayList<>(new ArrayList<>());
            color = new WritableArrayList<>(new ArrayList<>());
        } catch (TypeNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public WritableFireworkEffect(FireworkEffect ef) {
        hasFlicker = ef.hasFlicker();
        hasTrail = ef.hasTrail();
        try {
            List<WritableBukkitColor> main = (List<WritableBukkitColor>) color.getData(ObjectType.ORIGINAL);
            List<WritableBukkitColor> sub = (List<WritableBukkitColor>) fade.getData(ObjectType.ORIGINAL);
            type = ef.getType().name();

            for(Color c : ef.getColors())
                main.add(new WritableBukkitColor(c));

            for(Color c : ef.getFadeColors())
                sub.add(new WritableBukkitColor(c));
        } catch (ObjectNotSupportedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeBoolean(hasFlicker);
        stream.writeBoolean(hasTrail);
        color.writeData(stream);
        fade.writeData(stream);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        hasFlicker = stream.readBoolean();
        hasTrail  =  stream.readBoolean();
        color.readData(stream);
        fade.readData(stream);
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableFireworkEffect();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL){
            FireworkEffect.Builder builder = FireworkEffect.builder();
            builder.flicker(hasFlicker);
            builder.trail(hasTrail);
            for(WritableFireworkEffect effect : (List<WritableFireworkEffect>)color.getData(ObjectType.ORIGINAL)){
                builder.withColor((Color) effect.getData(ObjectType.ORIGINAL));
            }
            for(WritableFireworkEffect effect : (List<WritableFireworkEffect>)fade.getData(ObjectType.ORIGINAL)){
                builder.withFade((Color) effect.getData(ObjectType.ORIGINAL));
            }
            builder.with(FireworkEffect.Type.valueOf(type));
            return builder.build();
        }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        switch (flavor){
            case ORIGINAL:
                return getData(flavor);
            case OBJECT:
                switch (item){
                    case "flicker":
                        return hasFlicker;
                    case "trail":
                        return hasTrail;
                    case "type":
                        return FireworkEffect.Type.valueOf(type);
                    case "color":
                        return color;
                    case "fade":
                        return fade;
                }
        }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }
}
