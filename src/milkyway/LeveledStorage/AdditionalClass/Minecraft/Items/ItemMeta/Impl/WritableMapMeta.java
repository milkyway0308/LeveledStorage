package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl;

import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Colors.WritableBukkitColor;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.AdditionalData;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableMapMeta extends AdditionalData {
    private static int hash = "Minecraft- WritableMapMeta".hashCode();

    private boolean isScaling = false;

    private String locationName = null;

    private WritableBukkitColor color;

    public WritableMapMeta(){

    }

    public WritableMapMeta(ItemMeta meta) {
        if(meta instanceof MapMeta) {
            MapMeta mm = (MapMeta) meta;
            isScaling = mm.isScaling();
            if(mm.hasLocationName())
                locationName =mm.getLocationName();
            if(mm.hasColor())
                color = new WritableBukkitColor(mm.getColor());
        }
    }
    @Override
    public void setToMeta(ItemMeta meta) {
        if(meta instanceof MapMeta) {
            MapMeta mm = (MapMeta) meta;
            mm.setScaling(isScaling);
            if(locationName != null)
                mm.setLocationName(locationName);
            if(color != null)
                try {
                    mm.setColor((Color) color.getData(ObjectType.ORIGINAL));
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
        stream.writeBoolean(isScaling);
        if(locationName != null)
        {
            stream.writeBoolean(true);
            stream.writeUTF(locationName);
        }else
            stream.writeBoolean(false);

        if(color != null){
            stream.writeBoolean(true);
            color.writeData(stream);
        }else
            stream.writeBoolean(false);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        isScaling = stream.readBoolean();
        if(stream.readBoolean())
            locationName = stream.readUTF();
        if(stream.readBoolean())
            (color = new WritableBukkitColor()).readData(stream);
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableMapMeta();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.STRING)
            return locationName;
        if(flavor == ObjectType.OBJECT)
            return color;
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        switch (flavor){
            case STRING:
                if(item.equals("location"))
                    return locationName;
            case OBJECT:
                switch (item) {
                    case "isscaling":
                        return isScaling;
                    case "color":
                        return color;
                }
        }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }
}
