package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlowerType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public enum EnumFlowerType implements IFlowerType {
   Dandelion,
   Poppy,
   Orchid,
   Allium,
   Bluet,
   Tulip,
   Daisy,
   Cornflower,
   Pansy,
   Iris,
   Lavender(2),
   Viola,
   Daffodil,
   Dahlia,
   Peony(2),
   Rose(2),
   Lilac(2),
   Hydrangea(2),
   Foxglove(2),
   Zinnia,
   Mums,
   Marigold,
   Geranium,
   Azalea,
   Primrose,
   Aster,
   Carnation,
   Lily,
   Yarrow,
   Petunia,
   Agapanthus,
   Fuchsia,
   Dianthus,
   Forget,
   Anemone,
   Aquilegia,
   Edelweiss,
   Scabious,
   Coneflower,
   Gaillardia,
   Auricula,
   Camellia(2),
   Goldenrod(2),
   Althea(2),
   Penstemon(2),
   Delphinium(2),
   Hollyhock(2);

   int sections;
   IIcon[] stem;
   IIcon[] petal;
   IIcon[] variant;
   IIcon[] unflowered;
   IIcon seedStem;
   IIcon seedPetal;
   IIcon seedVariant;
   IIcon pollenStem;
   IIcon pollenPetal;
   IIcon pollenVariant;
   IIcon blank;

   private EnumFlowerType() {
      this(1);
   }

   private EnumFlowerType(int sections) {
      this.sections = 1;
      this.sections = sections;
      this.stem = new IIcon[sections];
      this.petal = new IIcon[sections];
      this.variant = new IIcon[sections];
      this.unflowered = new IIcon[sections];
   }

   public IIcon getStem(EnumFlowerStage stage, boolean flowered, int section) {
      return stage == EnumFlowerStage.SEED?this.seedStem:(stage == EnumFlowerStage.POLLEN?this.pollenStem:this.stem[section % this.sections]);
   }

   public IIcon getPetalIcon(EnumFlowerStage stage, boolean flowered, int section) {
      return stage == EnumFlowerStage.SEED?this.seedPetal:(stage == EnumFlowerStage.POLLEN?this.pollenPetal:(!flowered?this.unflowered[section % this.sections]:this.petal[section % this.sections]));
   }

   public IIcon getVariantIcon(EnumFlowerStage stage, boolean flowered, int section) {
      return stage == EnumFlowerStage.SEED?this.seedVariant:(stage == EnumFlowerStage.POLLEN?this.pollenVariant:(!flowered?this.blank:this.variant[section % this.sections]));
   }

   public void registerIcons(IIconRegister register) {
      for(int i = 0; i < this.sections; ++i) {
         String suf = i == 0?"":"" + (i + 1);
         String pre = this.sections == 1?"":"double/";
         this.stem[i] = Botany.proxy.getIcon(register, "flowers/" + pre + this.toString().toLowerCase() + suf + ".0");
         this.petal[i] = Botany.proxy.getIcon(register, "flowers/" + pre + this.toString().toLowerCase() + suf + ".1");
         this.variant[i] = Botany.proxy.getIcon(register, "flowers/" + pre + this.toString().toLowerCase() + suf + ".2");
         this.unflowered[i] = Botany.proxy.getIcon(register, "flowers/" + pre + this.toString().toLowerCase() + suf + ".3");
      }

      this.blank = Botany.proxy.getIcon(register, "flowers/blank");
      this.seedStem = Botany.proxy.getIcon(register, "flowers/seed.0");
      this.seedPetal = Botany.proxy.getIcon(register, "flowers/seed.1");
      this.seedVariant = Botany.proxy.getIcon(register, "flowers/seed.2");
      this.pollenStem = Botany.proxy.getIcon(register, "flowers/pollen.0");
      this.pollenPetal = Botany.proxy.getIcon(register, "flowers/pollen.1");
      this.pollenVariant = Botany.proxy.getIcon(register, "flowers/pollen.2");
   }

   public int getID() {
      return this.ordinal();
   }

   public int getSections() {
      return this.sections;
   }

   public IIcon getBlank() {
      return this.blank;
   }
}
