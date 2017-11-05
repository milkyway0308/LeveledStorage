package milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta;

import milkyway.LeveledStorage.Data.WritableData;
import org.bukkit.inventory.meta.ItemMeta;


public abstract class AdditionalData implements WritableData {
    public abstract void setToMeta(ItemMeta meta);
}
