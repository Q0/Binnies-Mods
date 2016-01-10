package binnie.core;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.Restriction;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Mods {
    public static Mods.Mod Forestry = new Mods.Mod("Forestry", null) {
        public boolean dev() {
            String forVersion = ((ModContainer) Loader.instance().getIndexedModList().get("Forestry")).getVersion();
            Restriction rest = new Restriction(new DefaultArtifactVersion("3.6"), true, (ArtifactVersion) null, false);
            return rest.containsVersion(new DefaultArtifactVersion(forVersion));
        }
    };
    public static Mods.Mod IC2 = new Mods.Mod("IC2");
    public static Mods.Mod Botania = new Mods.Mod("Botania");
    private static boolean WARN = true;

    public Mods() {
        super();
    }

    private static Item findItem(String modId, String name) {
        Item stack = GameRegistry.findItem(modId, name);
        if (stack == null && WARN && modId == "Forestry") {
            throw new RuntimeException("Item not found: " + modId + ":" + name);
        } else {
            return stack;
        }
    }

    private static ItemStack findItemStack(String modId, String name, int stackSize) {
        ItemStack stack = GameRegistry.findItemStack(modId, name, stackSize);
        if (stack == null && WARN && modId == "Forestry") {
            throw new RuntimeException("Stack not found: " + modId + ":" + name);
        } else {
            return stack;
        }
    }

    private static Block findBlock(String modId, String name) {
        Block stack = GameRegistry.findBlock(modId, name);
        if (stack == null && WARN && modId == "Forestry") {
            throw new RuntimeException("Block not found: " + modId + ":" + name);
        } else {
            return stack;
        }
    }

    public static class Mod {
        private String id;

        private Mod(String id) {
            super();
            this.id = id;
        }

        public Item item(String name) {
            return Mods.findItem(this.id, name);
        }

        public Block block(String name) {
            return Mods.findBlock(this.id, name);
        }

        public ItemStack stack(String name, int stackSize) {
            return Mods.findItemStack(this.id, name, stackSize);
        }

        public ItemStack stack(String name) {
            return this.stack(name, 1);
        }

        public ItemStack stack(String string, int i, int j) {
            return new ItemStack(this.item(string), i, j);
        }

        public boolean active() {
            return Loader.isModLoaded(this.id);
        }

        public boolean dev() {
            return false;
        }
    }
}
