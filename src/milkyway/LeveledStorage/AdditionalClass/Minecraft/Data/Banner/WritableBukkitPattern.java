package milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Banner;

import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Colors.WritableDyeColor;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableBukkitPattern implements WritableData {
    private static int hash = "Minecraft - WritableBukkitPattern".hashCode();

    private String patternID;
    private WritableDyeColor color;

    public WritableBukkitPattern() {

    }

    public WritableBukkitPattern(Pattern pattern) {
        color = new WritableDyeColor(pattern.getColor());
        patternID = pattern.getPattern().getIdentifier();

    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeUTF(patternID);
        color.writeData(stream);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        patternID = stream.readUTF();
        color = new WritableDyeColor();
        color.readData(stream);
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableBukkitPattern();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if (flavor == ObjectType.ORIGINAL)
            return new Pattern((DyeColor) color.getData(ObjectType.ORIGINAL), PatternType.getByIdentifier(patternID));
        else if (flavor == ObjectType.OBJECT)
            return color;
        else if (flavor == ObjectType.STRING)
            return patternID;
        throw new ObjectNotSupportedException(flavor, this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        switch (flavor) {
            case ORIGINAL:
                return new Pattern((DyeColor) color.getData(ObjectType.ORIGINAL), PatternType.getByIdentifier(patternID));
            case OBJECT:
                switch (item) {
                    case "color":
                        return color;
                    case "pattern":
                        return patternID;
                }
                break;
            case STRING:
                return patternID;
        }
        throw new ObjectNotSupportedException(flavor, this.getClass());
    }
}
