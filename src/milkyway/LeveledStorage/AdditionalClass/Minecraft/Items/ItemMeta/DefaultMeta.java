package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta;

import milkyway.LeveledStorage.AdditionalClass.Java.Collections.Lists.WritableArrayList;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Enchants.WritableEnchant;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl.*;
import milkyway.LeveledStorage.Data.WritableData;
import milkyway.LeveledStorage.Enums.ObjectType;
import milkyway.LeveledStorage.Exception.ObjectNotSupportedException;
import milkyway.LeveledStorage.Util.GenericsResolver.Exceptions.TypeNotSupportedException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultMeta implements WritableData {

    private static HashMap<Integer, AdditionalData> adData = null;

    private final static int hash = "Minecraft - DefaultMeta".hashCode();

    private AdditionalData additionalMeta = new EmptyMeta();

    private WritableRepairs rep = null;

    private String display;

    private WritableArrayList<String> lore;

    private WritableArrayList<WritableEnchant> enchants;

    private WritableArrayList<String> flags;

    private static void initializeTest(){
        if(adData == null){
            adData = new HashMap<>();
            adData.put(new EmptyMeta().getDataID(), new EmptyMeta());
            adData.put(new WritableBannerMeta().getDataID(),new WritableBannerMeta());
            adData.put(new WritableBookMeta().getDataID(),new WritableBookMeta());
            adData.put(new WritableEnchantBookMeta().getDataID(),new WritableBookMeta());
            adData.put(new WritableFireworkEffectMeta().getDataID(),new WritableFireworkEffectMeta());
            adData.put(new WritableFireworkMeta().getDataID(),new WritableFireworkMeta());
            adData.put(new WritableLeatherArmorMeta().getDataID(),new WritableLeatherArmorMeta());
            adData.put(new WritableMapMeta().getDataID(),new WritableMapMeta());
            adData.put(new WritablePotionMeta().getDataID(),new WritablePotionMeta());
            adData.put(new WritableSkullMeta().getDataID(),new WritableSkullMeta());
            adData.put(new WritableSpawnedEggMeta().getDataID(),new WritableSpawnedEggMeta());

        }
    }

    public DefaultMeta() {
        try {
            enchants = new WritableArrayList<>(new ArrayList<>());
            flags  = new WritableArrayList<>(new ArrayList<>());
            lore = new WritableArrayList<>(new ArrayList<>());
        } catch (TypeNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public DefaultMeta(ItemMeta meta) {
        this();
        if(meta.hasDisplayName())
            display = meta.getDisplayName();
        if(meta.hasLore()){
            try {
                List<String> lr = (List<String>) lore.getData(ObjectType.ORIGINAL);
                lr.addAll(meta.getLore());
            } catch (ObjectNotSupportedException e) {
                e.printStackTrace();
            }
        }
        try{
            if(meta.hasLore())
            {
                List<String> lr = (List<String>) lore.getData(ObjectType.ORIGINAL);
                lr.addAll(meta.getLore());
            }
            List<String> flag = (List<String>) flags.getData(ObjectType.ORIGINAL);
            for(ItemFlag n : meta.getItemFlags())
                flag.add(n.name());
            List<WritableEnchant> ench = (List<WritableEnchant>) enchants.getData(ObjectType.ORIGINAL);
            for(Map.Entry<Enchantment,Integer> entry  : meta.getEnchants().entrySet())
                ench.add(new WritableEnchant(entry.getKey(),entry.getValue()));
        }catch (ObjectNotSupportedException e){

        }

        if (meta instanceof SkullMeta)
            additionalMeta = new WritableSkullMeta(meta);
        else if(meta instanceof BannerMeta)
            additionalMeta = new WritableBannerMeta(meta);
        else if(meta instanceof BookMeta)
            additionalMeta = new WritableBookMeta(meta);
        else if(meta instanceof EnchantmentStorageMeta)
            additionalMeta = new WritableEnchantBookMeta(meta);
        else if(meta instanceof FireworkEffectMeta)
            additionalMeta = new WritableFireworkEffectMeta(meta);
        else if(meta instanceof FireworkMeta)
            additionalMeta = new WritableFireworkMeta(meta);
        else if(meta instanceof LeatherArmorMeta)
            additionalMeta  = new WritableLeatherArmorMeta(meta);
        else if(meta instanceof MapMeta)
            additionalMeta =  new WritableMapMeta(meta);
        else if(meta instanceof PotionMeta)
            additionalMeta = new WritablePotionMeta(meta);
        else if(meta instanceof SpawnEggMeta)
            additionalMeta =new WritableSpawnedEggMeta(meta);

        if(meta instanceof Repairable)
            rep  = new WritableRepairs((Repairable) meta);
    }

    public void setItemMeta(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        try {
            List<String> flag = (List<String>) flags.getData(ObjectType.ORIGINAL);
            for(String f : flag)
                meta.addItemFlags(ItemFlag.valueOf(f));
            if(display != null)
                meta.setDisplayName(display);
            meta.setLore((List<String>) lore.getData(ObjectType.ORIGINAL));
            if(rep != null)
                rep.setToMeta(meta);
            List<WritableEnchant>  we = (List<WritableEnchant>) enchants.getData(ObjectType.ORIGINAL);
            for(WritableEnchant ec : we)
                meta.addEnchant(ec.getEnchantment(),ec.getLevel(),true);
        } catch (ObjectNotSupportedException e) {
            e.printStackTrace();
        }
    }



    @Override
    public int getDataID() {
        return hash;
    }

    @Override
    public void writeData(ObjectOutputStream stream) throws IOException {
        stream.writeInt(additionalMeta.getDataID());
        additionalMeta.writeData(stream);
        if(rep != null){
            stream.writeBoolean(true);
            rep.writeData(stream);
        }else
            stream.writeBoolean(false);
        if(display != null){
            stream.writeBoolean(true);
            stream.writeUTF(display);
        }else{
            stream.writeBoolean(false);
        }

        lore.writeData(stream);
        enchants.writeData(stream);
        flags.writeData(stream);
    }

    @Override
    public void readData(ObjectInputStream stream) throws IOException {
        initializeTest();
        additionalMeta = adData.getOrDefault(stream.readInt(),new EmptyMeta());
        additionalMeta.readData(stream);
        boolean b = stream.readBoolean();
        if(b)
            (rep = new WritableRepairs()).readData(stream);
        b = stream.readBoolean();
        if(b)
            display = stream.readUTF();
        lore.readData(stream);
        enchants.readData(stream);
        flags.readData(stream);
    }

    @Override
    public WritableData getNewInstance() {
        return new DefaultMeta();
    }

    @Override
    public Object getData(ObjectType flavor) throws ObjectNotSupportedException {


        throw new ObjectNotSupportedException(flavor, this.getClass());
    }

    @Override
    public Object getData(ObjectType flavor, String item) throws ObjectNotSupportedException {
        switch (flavor) {
            case OBJECT:
                switch (item) {
                    case "lore":
                        return lore;
                    case "name":
                        return display;
                    case "enchant":
                        return enchants;
                    case "additionalmeta":
                        return additionalMeta;
                    case "flags":
                        return flags;
                }
                break;
            case STRING:
                switch (item) {
                    case "name":
                        return display;
                }
                break;
        }

        throw new ObjectNotSupportedException(flavor, this.getClass());
    }
}
