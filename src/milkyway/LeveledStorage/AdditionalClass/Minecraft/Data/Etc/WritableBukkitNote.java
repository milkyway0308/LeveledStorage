package milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Etc;

import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.Note;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableBukkitNote implements WritableData {
    private static int hash = "Minecraft - WritableBukkitNote".hashCode();

    private byte note;

    public WritableBukkitNote(Note note) {
        this.note = note.getId();
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {

    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {

    }

    @Override
    public WritableData getNewInstance() {
        return null;
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        return null;
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        return null;
    }
}
