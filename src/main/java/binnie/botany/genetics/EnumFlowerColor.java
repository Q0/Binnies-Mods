package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.api.IFlowerColour;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.AlleleColor;
import binnie.botany.genetics.ColourMix;
import forestry.api.genetics.IAlleleInteger;

public enum EnumFlowerColor implements IFlowerColour {
   Aquamarine("aquamarine", 8388564),
   Black("black", 2631720),
   Blue("blue", 255),
   Brown("brown", 10824234),
   CadetBlue("cadetBlue", 6266528),
   Chocolate("chocolate", 13789470),
   Coral("coral", 16744272),
   Crimson("crimson", 14423100),
   Cyan("cyan", '\uffff'),
   DarkGoldenrod("darkGoldenrod", 12092939),
   DarkGray("darkGray", 11119017),
   DarkGreen("darkGreen", 25600),
   DarkKhaki("darkKhaki", 12433259),
   DarkOliveGreen("darkOliveGreen", 5597999),
   DarkOrange("darkOrange", 16747520),
   DarkSalmon("darkSalmon", 15308410),
   DarkSeaGreen("darkSeaGreen", 9419915),
   DarkSlateBlue("darkSlateBlue", 4734347),
   DarkSlateGray("darkSlateGray", 3100495),
   DarkTurquoise("darkTurquoise", '컑'),
   DarkViolet("darkViolet", 9699539),
   DeepPink("deepPink", 16716947),
   DeepSkyBlue("deepSkyBlue", '뿿'),
   DimGray("dimGray", 6908265),
   DodgerBlue("dodgerBlue", 2003199),
   Gold("gold", 16766720),
   Goldenrod("goldenrod", 14329120),
   Gray("gray", 8421504),
   Green("green", '耀'),
   HotPink("hotPink", 16738740),
   IndianRed("indianRed", 13458524),
   Indigo("indigo", 4915330),
   Khaki("khaki", 15787660),
   Lavender("lavender", 15132410),
   LemonChiffon("lemonChiffon", 16775885),
   LightGray("lightGray", 13882323),
   LightSeaGreen("lightSeaGreen", 2142890),
   LightSteelBlue("lightSteelBlue", 11584734),
   Lime("lime", '\uff00'),
   LimeGreen("limeGreen", 3329330),
   Magenta("magenta", 16711935),
   Maroon("maroon", 8388608),
   MediumAquamarine("mediumAquamarine", 6737322),
   MediumOrchid("mediumOrchid", 12211667),
   MediumPurple("mediumPurple", 9662683),
   MediumSeaGreen("mediumSeaGreen", 3978097),
   MediumVioletRed("mediumVioletRed", 13047173),
   MistyRose("mistyRose", 16770273),
   Navy("navy", 128),
   Olive("olive", 8421376),
   OliveDrab("oliveDrab", 7048739),
   Orange("orange", 16753920),
   PaleGreen("paleGreen", 10025880),
   PaleTurquoise("paleTurquoise", 11529966),
   PaleVioletRed("paleVioletRed", 14381203),
   Peru("peru", 13468991),
   Pink("pink", 16761035),
   Plum("plum", 14524637),
   Purple("purple", 8388736),
   Red("red", 16711680),
   RosyBrown("rosyBrown", 12357519),
   RoyalBlue("royalBlue", 4286945),
   Salmon("salmon", 16416882),
   SandyBrown("sandyBrown", 16032864),
   SeaGreen("seaGreen", 3050327),
   Sienna("sienna", 10506797),
   SkyBlue("skyBlue", 8900331),
   SlateBlue("slateBlue", 6970061),
   SlateGray("slateGray", 7372944),
   SpringGreen("springGreen", 'ｿ'),
   SteelBlue("steelBlue", 4620980),
   Tan("tan", 13808780),
   Teal("teal", '肀'),
   Thistle("thistle", 14204888),
   Turquoise("turquoise", 4251856),
   Violet("violet", 15631086),
   Wheat("wheat", 16113331),
   White("white", 16777215),
   Yellow("yellow", 16776960),
   YellowGreen("yellowGreen", 10145074);

   int color;
   int colorDis;
   AlleleColor allele;
   String ident;

   private EnumFlowerColor(String ident, int c) {
      this(c);
   }

   private EnumFlowerColor(int c) {
      this.color = c;
      this.allele = new AlleleColor(this, "botany.color" + this.name(), this.toString(), this.color);
      int r = this.color >> 16 & 255;
      int g = this.color >> 8 & 255;
      int b = this.color & 255;
      r = (int)(0.45D * (double)(r + 214));
      g = (int)(0.45D * (double)(r + 174));
      b = (int)(0.45D * (double)(r + 131));
      this.colorDis = (r << 16) + (g << 8) + b;
   }

   public int getColor(boolean dis) {
      return dis?this.colorDis:this.color;
   }

   public IAlleleInteger getAllele() {
      return this.allele;
   }

   public int getID() {
      return this.ordinal();
   }

   public static void addMix(EnumFlowerColor start1, EnumFlowerColor start2, EnumFlowerColor result, int chance) {
      BotanyCore.getFlowerRoot().registerColourMix(new ColourMix(start1, start2, result, chance));
   }

   public static void setupMutations() {
      // $FF: Couldn't be decompiled
   }

   public String getName() {
      return Botany.proxy.localise("colour." + this.name().toLowerCase());
   }

   public static EnumFlowerColor get(int i) {
      return values()[Math.max(0, i) % values().length];
   }

   public String getHTMLName() {
      return this.name();
   }
}
