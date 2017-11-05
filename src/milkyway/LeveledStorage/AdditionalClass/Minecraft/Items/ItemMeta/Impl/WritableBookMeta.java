package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.Lists.WritableArrayList;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.AdditionalData;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WritableBookMeta extends AdditionalData {
    private static int hash = "Minecraft - WritableBookMeta".hashCode();

    private static String generation;

    private String title;

    private String author;

    private WritableArrayList<String> data;

    public WritableBookMeta() {
        try {
            data = new WritableArrayList<>(new ArrayList<>());
        } catch (TypeNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public WritableBookMeta(ItemMeta meta) {
        this();
        if (meta instanceof BookMeta) {
            BookMeta bm = (BookMeta) meta;
            title = bm.getTitle();
            author = bm.getAuthor();
            generation = bm.hasGeneration() ? bm.getGeneration().name() : "EMPTY";
            try {
                ((List<String>) data.getData(ObjectType.ORIGINAL)).addAll(bm.getPages());
            } catch (ObjectNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setToMeta(ItemMeta meta) {
        if (meta instanceof BookMeta) {
            BookMeta bm = (BookMeta) meta;
            if (!generation.equals("EMPTY"))
                bm.setGeneration(BookMeta.Generation.valueOf(generation));
            try {
                bm.setPages((List<String>) data.getData(ObjectType.ORIGINAL));
            } catch (ObjectNotSupportedException e) {
                e.printStackTrace();
            }
            bm.setAuthor(author);
            bm.setTitle(title);
        }
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeUTF(title);
        stream.writeUTF(author);
        stream.writeUTF(generation);
        data.writeData(stream);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        title = stream.readUTF();
        author = stream.readUTF();
        generation = stream.readUTF();
        data.readData(stream);
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableBookMeta();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if (flavor == ObjectType.ORIGINAL)
            return data;
        throw new ObjectNotSupportedException(flavor, this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        switch (flavor) {
            case ORIGINAL:
                return data;
            case STRING:
                switch (item) {
                    case "title":
                        return title;
                    case "author":
                        return author;
                    case "generation":
                        return generation;
                }
        }
        throw new ObjectNotSupportedException(flavor, this.getClass());
    }
}
