package milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Colors;

import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableBukkitColor implements WritableData {
    private static int hash = "Minecraft - WritableBukkitColor".hashCode();
    private int rgbColor = 0;

    public WritableBukkitColor() {

    }

    public WritableBukkitColor(Color color) {
        rgbColor = color.asRGB();
    }

    public int getColorasRGB() {
        return rgbColor;
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeInt(rgbColor);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        rgbColor = stream.readInt();
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableBukkitColor();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {

        throw new ObjectNotSupportedException(flavor, this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        switch (flavor) {
            case ORIGINAL:
                return Color.fromRGB(rgbColor);
            case INTEGER:
                switch (item) {
                    case "rgb":
                        return rgbColor;
                }
                break;
        }
        throw new ObjectNotSupportedException(flavor, this.getClass());
    }
}
