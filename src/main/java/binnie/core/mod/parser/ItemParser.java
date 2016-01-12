package binnie.core.mod.parser;

import binnie.core.AbstractMod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import java.lang.reflect.Field;

public class ItemParser extends FieldParser {
    @Override
    public boolean isHandled(final Field field, final AbstractMod mod) {
        return Item.class.isAssignableFrom(field.getType());
    }

    @Override
    public void preInit(final Field field, final AbstractMod mod) throws IllegalArgumentException, IllegalAccessException {
        final Item item = (Item) field.get(null);
        if (item != null) {
            GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        }
    }
}
