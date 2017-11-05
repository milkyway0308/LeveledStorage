package milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Enchants;

import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.enchantments.Enchantment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableEnchant implements WritableData {

    private static int hash = "Minecraft - WritableEnchant".hashCode();

    private int enchantID;

    private int enchantLevel = 0;

    public WritableEnchant() {

    }

    public WritableEnchant(Enchantment ench) {
        enchantID = ench.getId();
    }

    public WritableEnchant(Enchantment ench, int lv) {
        this(ench);
        enchantLevel = lv;
    }

    public Enchantment getEnchantment(){
        return Enchantment.getById(enchantID);
    }

    public int getLevel(){
        return enchantLevel;
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeInt(enchantID);
        stream.writeInt(enchantLevel);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        enchantID = stream.readInt();
        enchantLevel = stream.readInt();
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableEnchant();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL)
            return Enchantment.getById(enchantID);
        else if(flavor == ObjectType.INTEGER)
            return enchantLevel;
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
       return getData(flavor);
    }
}
