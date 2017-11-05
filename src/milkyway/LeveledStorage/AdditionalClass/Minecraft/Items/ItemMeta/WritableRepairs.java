package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta;

import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableRepairs implements WritableData{
    private static int hash =  "Minecraft - WritableRepairs".hashCode();

    private int repairLevel = -1;

    public WritableRepairs(){

    }

    public WritableRepairs(Repairable rp){
        if(rp.hasRepairCost())
            repairLevel = rp.getRepairCost();
    }

    public void setToMeta(ItemMeta meta){
        if(meta instanceof Repairable)
            ((Repairable) meta).setRepairCost(repairLevel);
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeInt(repairLevel);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        repairLevel= stream.readInt();
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableRepairs();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL)
            return repairLevel;
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        return getData(flavor);
    }
}
