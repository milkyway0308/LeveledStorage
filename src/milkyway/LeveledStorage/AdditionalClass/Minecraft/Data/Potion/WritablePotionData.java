package milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Potion;

import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritablePotionData implements WritableData{
    private static int hash = "Minecraft - WritablePotionData".hashCode();

    private String potion;

    private boolean extend = false;

    private boolean upgrade = false;

    public WritablePotionData(){

    }

    public WritablePotionData(PotionData data) {
        potion = data.getType().name();
        extend = data.isExtended();
        upgrade = data.isUpgraded();
    }

    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeUTF(potion);
        stream.writeBoolean(extend);
        stream.writeBoolean(upgrade);
        System.out.println("Save: " + potion);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        potion = stream.readUTF();
        extend = stream.readBoolean();
        upgrade = stream.readBoolean();
        System.out.println("Load: "+  potion);
    }

    @Override
    public WritableData getNewInstance() {
        return new WritablePotionData();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL){
            return new PotionData(PotionType.valueOf(potion),extend,upgrade);
        }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        switch (flavor){
            case ORIGINAL:
                return getData(flavor);
            case STRING:
                return potion;
            case OBJECT:
                switch (item){
                    case "extend":
                        return extend;
                    case "upgrade":
                        return upgrade;
                }
        }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }
}
