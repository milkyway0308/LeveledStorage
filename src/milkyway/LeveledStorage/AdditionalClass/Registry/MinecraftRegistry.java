package milkyway.LeveledStorage.AdditionalClass.Registry;

import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Banner.WritableBukkitPattern;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Colors.WritableBukkitColor;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Colors.WritableDyeColor;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Enchants.WritableEnchant;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Etc.WritableBukkitNote;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Fireworks.WritableFireworkEffect;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Potion.WritablePotionData;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Data.Potion.WritablePotionEffect;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.DefaultMeta;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.Impl.*;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.ItemMeta.WritableRepairs;
import milkyway.LeveledStorage.AdditionalClass.Minecraft.Items.WritableItemStack;
import milkyway.LeveledStorage.Data.DataWritableRegistry;
import milkyway.LeveledStorage.Data.WritableData;
import org.bukkit.Note;

public class MinecraftRegistry {

    public static void registerWritable() {
        registerData();
    }

    private static void registerData() {
        registerItemMeta();
        registerBukkitData();
        JavaRegistry.registerWritable();
    }

    private static void registerItemMeta() {
        register(new EmptyMeta());
        register(new WritableBannerMeta());
        register(new WritableSkullMeta());
        register(new DefaultMeta());
        register(new WritableBookMeta());
        register(new WritableEnchantBookMeta());
        register(new WritableFireworkEffectMeta());
        register(new WritableFireworkMeta());
        register(new WritableLeatherArmorMeta());
        register(new WritableMapMeta());
        register(new WritablePotionMeta());
        register(new WritableSpawnedEggMeta());
        register(new WritableRepairs());
        register(new WritableItemStack());
    }

    private static void registerBukkitData() {
        register(new WritableBukkitPattern());
        register(new WritableBukkitColor());
        register(new WritableDyeColor());
        register(new WritableEnchant());
        register(new WritableBukkitNote(Note.flat(0, Note.Tone.A)));
        register(new WritableFireworkEffect());
        register(new WritablePotionData());
        register(new WritablePotionEffect());
    }


    private static void register(WritableData data) {
        DataWritableRegistry.registerWritable(data);
    }
}
