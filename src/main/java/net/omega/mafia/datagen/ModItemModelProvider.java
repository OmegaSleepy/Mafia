package net.omega.mafia.datagen;

import com.mojang.logging.LogUtils;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.omega.mafia.items.ModItems;
import org.slf4j.Logger;

import static net.omega.mafia.Mafia.MODID;

public class ModItemModelProvider extends ItemModelProvider {

    private static final Logger LOGGER = LogUtils.getLogger();

    public ModItemModelProvider (PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels () {
        basicItem(ModItems.NAPKIN.getId());
    }
}