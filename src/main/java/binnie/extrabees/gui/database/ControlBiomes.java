package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.List;

public class ControlBiomes extends Control implements ITooltip {
    List tolerated = new ArrayList();

    public ControlBiomes(IWidget parent, int x, int y, int width, int height) {
        super(parent, (float) x, (float) y, (float) (width * 16), (float) (height * 16));
        this.addAttribute(Attribute.MouseOver);
    }

    public void getTooltip(Tooltip list) {
        if (!this.tolerated.isEmpty()) {
            int x = (int) (this.getRelativeMousePosition().x() / 16.0F);
            int y = (int) (this.getRelativeMousePosition().y() / 16.0F);
            int i = x + y * 8;
            if (i < this.tolerated.size()) {
                list.add(BiomeGenBase.getBiome(((Integer) this.tolerated.get(i)).intValue()).biomeName);
            }

        }
    }

    public void onRenderForeground() {
        for (int i = 0; i < this.tolerated.size(); ++i) {
            int x = i % 8 * 16;
            int y = i / 8 * 16;
            if (BiomeGenBase.getBiome(i) != null) {
                CraftGUI.Render.colour(BiomeGenBase.getBiome(i).color);
            }

            CraftGUI.Render.texture((Object) CraftGUITexture.Button, (IArea) (new IArea((float) x, (float) y, 16.0F, 16.0F)));
        }

    }

    public void setSpecies(IAlleleBeeSpecies species) {
        this.tolerated.clear();
        if (species != null) {
            IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()));
            IBee bee = Binnie.Genetics.getBeeRoot().getBee(BinnieCore.proxy.getWorld(), genome);
        }
    }
}
