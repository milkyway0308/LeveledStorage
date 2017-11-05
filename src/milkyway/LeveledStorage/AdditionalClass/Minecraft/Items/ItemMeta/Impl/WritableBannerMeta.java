package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.Lists.WritableArrayList;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Banner.WritableBukkitPattern;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Colors.WritableDyeColor;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.AdditionalData;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WritableBannerMeta extends AdditionalData {

    private static int hash = "Minecraft - WritableBannerMeta".hashCode();

    private WritableArrayList<WritableBukkitPattern> list;
    private WritableDyeColor baseColor;

    public WritableBannerMeta() {
        try {
            list = new WritableArrayList<>(new ArrayList<>());
        } catch (TypeNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public WritableBannerMeta(ItemMeta meta) {
        this();
        if (meta instanceof BannerMeta) {
            BannerMeta bm = (BannerMeta) meta;
            baseColor = new WritableDyeColor(bm.getBaseColor());
            try {
                List<WritableBukkitPattern> original = (List<WritableBukkitPattern>) list.getData(ObjectType.ORIGINAL);
                for (Pattern pattern : bm.getPatterns())
                    original.add(new WritableBukkitPattern(pattern));
            } catch (ObjectNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setToMeta(ItemMeta meta) {
        if (meta instanceof BannerMeta) {
            BannerMeta bm = (BannerMeta) meta;
            try {
                bm.setBaseColor((DyeColor) baseColor.getData(ObjectType.ORIGINAL));
                for (WritableBukkitPattern pattern : (List<WritableBukkitPattern>) list.getData(ObjectType.ORIGINAL))
                    bm.addPattern((Pattern) pattern.getData(ObjectType.ORIGINAL));
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
        baseColor.writeData(stream);
        list.writeData(stream);
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
