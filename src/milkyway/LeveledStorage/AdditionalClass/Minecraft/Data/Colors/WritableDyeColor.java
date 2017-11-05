package milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Colors;

import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.Color;
import org.bukkit.DyeColor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableDyeColor implements WritableData {
    private static int hash = "Minecraft - WritableDyeColor".hashCode();
    private WritableBukkitColor bukkitColor = new WritableBukkitColor();

    public WritableDyeColor() {

    }

    public WritableDyeColor(DyeColor color) {
        bukkitColor = new WritableBukkitColor(color.getColor());
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        bukkitColor.writeData(stream);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        bukkitColor.readData(stream);
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableDyeColor();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if (flavor == ObjectType.ORIGINAL)
            return DyeColor.getByColor((Color) bukkitColor.getData(ObjectType.ORIGINAL));
        else if (flavor == ObjectType.OBJECT)
            return bukkitColor;
        throw new ObjectNotSupportedException(flavor, this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        switch (flavor) {
            case ORIGINAL:
                switch (item) {
                    case "dyecolor":
                        return getData(flavor);
                    case "bukkitcolor":
                    case "writable":
                    case "writablebukkitcolor":
                        return bukkitColor;
                }
            case OBJECT:
                switch (item) {
                    case "dyecolor":
                        return getData(flavor);
                    case "bukkitcolor":
                    case "writable":
                    case "writablebukkitcolor":
                        return bukkitColor;
                }
        }
        throw new ObjectNotSupportedException(flavor, this.getClass());
    }
}
