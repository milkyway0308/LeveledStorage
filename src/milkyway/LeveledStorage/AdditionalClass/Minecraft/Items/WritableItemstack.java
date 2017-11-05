package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items;

import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.DefaultMeta;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class WritableItemstack implements WritableData{
    private static int hash = "Minecraft - WritableItemstack".hashCode();

    private int itemID;

    private short durability;



    private DefaultMeta meta;

    public WritableItemstack(){

    }

    public WritableItemstack(ItemStack n){
        itemID = n.getTypeId();
        durability = n.getDurability();
        if(n.hasItemMeta())
            meta = new DefaultMeta(n.getItemMeta());
    }
    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeInt(itemID);
        stream.writeShort(durability);
        if(meta != null)
        {
            stream.writeBoolean(true);
            meta.writeData(stream);
        }else
            stream.writeBoolean(false);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        itemID = stream.readInt();
        durability = stream.readShort();
        if(stream.readBoolean()){
            (meta = new DefaultMeta()).readData(stream);
        }
    }

    @Override
    public WritableData getNewInstance() {
        return new WritableItemstack();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {
        if(flavor == ObjectType.ORIGINAL){
            ItemStack item = new ItemStack(Material.getMaterial(itemID));
            item.setDurability(durability);
            if(this.meta != null)
            {
                this.meta.setItemMeta(item);
                return item;
            }
        }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
       switch (flavor){
           case ORIGINAL:
               return getData(flavor);
           case INTEGER:
               switch (item){
                   case "id":
                       return itemID;
                   case "durability":
                       return durability;
               }

           case OBJECT:
               if(item.equals("meta"))
                   return meta;
       }
        throw new ObjectNotSupportedException(flavor,this.getClass());
    }
}
