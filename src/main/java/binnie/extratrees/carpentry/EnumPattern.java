package binnie.extratrees.carpentry;

import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.ILayout;
import binnie.extratrees.api.IPattern;
import binnie.extratrees.carpentry.Layout;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public enum EnumPattern implements IPattern {
   Blank,
   Octagon,
   Diamond,
   Ringed,
   Squared,
   Multiply,
   Circle,
   Plus,
   Creeper,
   Creeper2,
   Creeper3,
   Creeper4,
   Halved,
   Halved2,
   Striped,
   Striped2,
   ThinStriped,
   ThinStriped2,
   Chequered,
   Tiled,
   VeryThinCorner,
   VeryThinCorner2,
   VeryThinCorner3,
   VeryThinCorner4,
   ThinCorner,
   ThinCorner2,
   ThinCorner3,
   ThinCorner4,
   Corner,
   Corner2,
   Corner3,
   Corner4,
   ThickCorner,
   ThickCorner2,
   ThickCorner3,
   ThickCorner4,
   ThinCurvedCorner,
   ThinCurvedCorner2,
   ThinCurvedCorner3,
   ThinCurvedCorner4,
   CurvedCorner,
   CurvedCorner2,
   CurvedCorner3,
   CurvedCorner4,
   Edged,
   Edged2,
   Edged3,
   Edged4,
   ThinEdged,
   ThinEdged2,
   ThinEdged3,
   ThinEdged4,
   ThinBarred,
   ThinBarred2,
   Barred,
   Barred2,
   ThickBarred,
   ThickBarred2,
   BarredEnd,
   BarredEnd2,
   BarredEnd3,
   BarredEnd4,
   OrnateBarred,
   OrnateBarred2,
   OrnateThinBarred,
   OrnateThinBarred2,
   SplitBarred,
   SplitBarred2,
   SplitBarredCorner,
   SplitBarredCorner2,
   SplitBarredCorner3,
   SplitBarredCorner4,
   SplitBarredTSection,
   SplitBarredTSection2,
   SplitBarredTSection3,
   SplitBarredTSection4,
   SplitCrossed,
   SplitBarredEnd,
   SplitBarredEnd2,
   SplitBarredEnd3,
   SplitBarredEnd4,
   Diagonal,
   Diagonal2,
   ThickDiagonal,
   ThickDiagonal2,
   DiagonalCorner,
   DiagonalCorner2,
   DiagonalCorner3,
   DiagonalCorner4,
   DiagonalTSection,
   DiagonalTSection2,
   DiagonalTSection3,
   DiagonalTSection4,
   DiagonalCurvedCorner,
   DiagonalCurvedCorner2,
   DiagonalCurvedCorner3,
   DiagonalCurvedCorner4,
   DiagonalCurvedTSection,
   DiagonalCurvedTSection2,
   DiagonalCurvedTSection3,
   DiagonalCurvedTSection4,
   DiagonalStriped,
   DiagonalStriped2,
   ThinDiagonalStriped,
   ThinDiagonalStriped2,
   ThinSaltire,
   Saltire,
   ThickSaltire,
   ThinCrossed,
   Crossed,
   ThickCrossed,
   ThinCurvedCrossed,
   ThinTSection,
   ThinTSection2,
   ThinTSection3,
   ThinTSection4,
   TSection,
   TSection2,
   TSection3,
   TSection4,
   ThickTSection,
   ThickTSection2,
   ThickTSection3,
   ThickTSection4,
   ThinCurvedTSection,
   ThinCurvedTSection2,
   ThinCurvedTSection3,
   ThinCurvedTSection4,
   CurvedTSection,
   CurvedTSection2,
   CurvedTSection3,
   CurvedTSection4,
   ThinBarredCorner,
   ThinBarredCorner2,
   ThinBarredCorner3,
   ThinBarredCorner4,
   BarredCorner,
   BarredCorner2,
   BarredCorner3,
   BarredCorner4,
   ThickBarredCorner,
   ThickBarredCorner2,
   ThickBarredCorner3,
   ThickBarredCorner4,
   ThinCurvedBarredCorner,
   ThinCurvedBarredCorner2,
   ThinCurvedBarredCorner3,
   ThinCurvedBarredCorner4,
   BarredCurvedCorner,
   BarredCurvedCorner2,
   BarredCurvedCorner3,
   BarredCurvedCorner4,
   ThinStripedCorner,
   ThinStripedCorner2,
   ThinStripedCorner3,
   ThinStripedCorner4,
   StripedCorner,
   StripedCorner2,
   StripedCorner3,
   StripedCorner4,
   OrnateStripedCorner,
   OrnateStripedCorner2,
   OrnateStripedCorner3,
   OrnateStripedCorner4,
   Emblem1,
   Emblem2,
   Emblem3,
   Emblem4,
   Emblem5,
   LetterA,
   LetterA2,
   LetterA3,
   LetterA4,
   LetterB,
   LetterB2,
   LetterB3,
   LetterB4,
   LetterF,
   LetterF2,
   LetterF3,
   LetterF4,
   LetterS,
   LetterS2,
   LetterS3,
   LetterS4,
   LetterT,
   LetterT2,
   LetterT3,
   LetterT4,
   DiagonalHalved,
   DiagonalHalved2,
   Diagonal1Edged,
   Diagonal1Edged2,
   Diagonal1Edged3,
   Diagonal1Edged4,
   Diagonal2Edged,
   Diagonal2Edged2,
   ThickDiagonal1Edged,
   ThickDiagonal1Edged2,
   ThickDiagonal1Edged3,
   ThickDiagonal1Edged4,
   ThinBarredEnd,
   ThinBarredEnd2,
   ThinBarredEnd3,
   ThinBarredEnd4,
   ThickBarredEnd,
   ThickBarredEnd2,
   ThickBarredEnd3,
   ThickBarredEnd4,
   OverlappedSplitBarred,
   OverlappedSplitBarred2,
   OverlappedBarred,
   OverlappedBarred2;

   ILayout leftRotation = Layout.get(this, false);
   ILayout horizontalFlip = Layout.get(this, false);

   private EnumPattern() {
   }

   static void setupStripedPattern(EnumPattern vert, EnumPattern hori) {
      vert.setLeftRotation(hori, true);
      hori.setLeftRotation(vert, false);
      vert.setHorizontalFlip(vert, true);
   }

   static void setupChequeredPattern(EnumPattern cheq) {
      cheq.setLeftRotation(cheq, true);
      cheq.setHorizontalFlip(cheq, true);
   }

   static void setupCornerPattern(EnumPattern tl, EnumPattern tr, EnumPattern br, EnumPattern bl) {
      tl.setLeftRotation(bl, false);
      tr.setLeftRotation(tl, false);
      br.setLeftRotation(tr, false);
      bl.setLeftRotation(br, false);
      tl.setHorizontalFlip(tr, false);
      bl.setHorizontalFlip(br, false);
   }

   static void setupInvert2Rot(EnumPattern a, EnumPattern b) {
      a.setLeftRotation(b, true);
      b.setLeftRotation(a, false);
      a.setHorizontalFlip(b, false);
   }

   static void set2Rotation(EnumPattern a, EnumPattern b) {
      a.setLeftRotation(b, false);
      b.setLeftRotation(a, false);
      a.setHorizontalFlip(b, false);
   }

   static void setupBarredPattern(EnumPattern vert, EnumPattern hori) {
      vert.setLeftRotation(hori, false);
      hori.setLeftRotation(vert, false);
   }

   static void setupEdgedPattern(EnumPattern l, EnumPattern t, EnumPattern r, EnumPattern b) {
      l.setLeftRotation(b, false);
      t.setLeftRotation(l, false);
      r.setLeftRotation(t, false);
      b.setLeftRotation(r, false);
      l.setHorizontalFlip(r, false);
   }

   static void setupDiagonalPattern(EnumPattern a, EnumPattern b) {
      a.setLeftRotation(b, false);
      b.setLeftRotation(a, false);
      a.setHorizontalFlip(b, false);
   }

   static void setupBarredPattern(EnumPattern l, EnumPattern t, EnumPattern r, EnumPattern b) {
      l.setLeftRotation(b, false);
      t.setLeftRotation(l, false);
      r.setLeftRotation(t, false);
      b.setLeftRotation(r, false);
      l.setHorizontalFlip(r, false);
   }

   static void setupTSectionPattern(EnumPattern l, EnumPattern t, EnumPattern r, EnumPattern b) {
      setupEdgedPattern(l, t, r, b);
   }

   public IIcon getPrimaryIcon(IDesignSystem system) {
      return system.getPrimaryIcon(this);
   }

   private static void setupRotation(EnumPattern t, EnumPattern r, EnumPattern b, EnumPattern l) {
      setupEdgedPattern(l, t, r, b);
   }

   private void setHorizontalFlip(EnumPattern pattern, boolean inverted) {
      this.horizontalFlip = Layout.get(pattern, inverted);
      pattern.horizontalFlip = Layout.get(this, inverted);
   }

   public IIcon getSecondaryIcon(IDesignSystem system) {
      return system.getSecondaryIcon(this);
   }

   public ILayout getRotation() {
      return this.leftRotation;
   }

   public ILayout getHorizontalFlip() {
      return this.horizontalFlip;
   }

   protected void setLeftRotation(EnumPattern pattern, boolean inverted) {
      this.leftRotation = Layout.get(pattern, inverted);
   }

   public void registerIcons(IIconRegister register) {
   }

   public ILayout layout() {
      return this.layout(false);
   }

   public ILayout layout(boolean invert) {
      return Layout.get(this, invert);
   }

   static {
      setupStripedPattern(ThinStriped, ThinStriped2);
      setupStripedPattern(Striped, Striped2);
      setupStripedPattern(Halved, Halved2);
      setupChequeredPattern(Chequered);
      setupChequeredPattern(Tiled);
      setupCornerPattern(VeryThinCorner, VeryThinCorner2, VeryThinCorner3, VeryThinCorner4);
      setupCornerPattern(ThinCorner, ThinCorner2, ThinCorner3, ThinCorner4);
      setupCornerPattern(Corner, Corner2, Corner3, Corner4);
      setupCornerPattern(ThickCorner, ThickCorner2, ThickCorner3, ThickCorner4);
      setupCornerPattern(ThinCurvedCorner, ThinCurvedCorner2, ThinCurvedCorner3, ThinCurvedCorner4);
      setupCornerPattern(CurvedCorner, CurvedCorner2, CurvedCorner3, CurvedCorner4);
      setupBarredPattern(BarredEnd, BarredEnd2, BarredEnd3, BarredEnd4);
      setupEdgedPattern(ThinEdged, ThinEdged2, ThinEdged3, ThinEdged4);
      setupEdgedPattern(Edged, Edged2, Edged3, Edged4);
      setupBarredPattern(ThinBarred, ThinBarred2);
      setupBarredPattern(Barred, Barred2);
      setupBarredPattern(ThickBarred, ThickBarred2);
      setupDiagonalPattern(Diagonal, Diagonal2);
      setupDiagonalPattern(ThickDiagonal, ThickDiagonal2);
      setupTSectionPattern(ThinTSection, ThinTSection2, ThinTSection3, ThinTSection4);
      setupTSectionPattern(TSection, TSection2, TSection3, TSection4);
      setupTSectionPattern(ThickTSection, ThickTSection2, ThickTSection3, ThickTSection4);
      setupTSectionPattern(ThinCurvedTSection, ThinCurvedTSection2, ThinCurvedTSection3, ThinCurvedTSection4);
      setupTSectionPattern(CurvedTSection, CurvedTSection2, CurvedTSection3, CurvedTSection4);
      setupCornerPattern(ThinBarredCorner, ThinBarredCorner2, ThinBarredCorner3, ThinBarredCorner4);
      setupCornerPattern(BarredCorner, BarredCorner2, BarredCorner3, BarredCorner4);
      setupCornerPattern(ThickBarredCorner, ThickBarredCorner2, ThickBarredCorner3, ThickBarredCorner4);
      setupCornerPattern(ThinCurvedBarredCorner, ThinCurvedBarredCorner2, ThinCurvedBarredCorner3, ThinCurvedBarredCorner4);
      setupCornerPattern(BarredCurvedCorner, BarredCurvedCorner2, BarredCurvedCorner3, BarredCurvedCorner4);
      setupCornerPattern(ThinStripedCorner, ThinStripedCorner2, ThinStripedCorner3, ThinStripedCorner4);
      setupCornerPattern(StripedCorner, StripedCorner2, StripedCorner3, StripedCorner4);
      setupCornerPattern(OrnateStripedCorner, OrnateStripedCorner2, OrnateStripedCorner3, OrnateStripedCorner4);
      setupRotation(LetterA, LetterA2, LetterA3, LetterA4);
      setupRotation(LetterB, LetterB2, LetterB3, LetterB4);
      setupRotation(LetterF, LetterF2, LetterF3, LetterF4);
      setupRotation(LetterS, LetterS2, LetterS3, LetterS4);
      setupRotation(LetterT, LetterT2, LetterT3, LetterT4);
      setupEdgedPattern(DiagonalCorner, DiagonalCorner2, DiagonalCorner3, DiagonalCorner4);
      setupCornerPattern(DiagonalTSection, DiagonalTSection2, DiagonalTSection3, DiagonalTSection4);
      setupEdgedPattern(DiagonalCurvedCorner, DiagonalCurvedCorner2, DiagonalCurvedCorner3, DiagonalCurvedCorner4);
      setupCornerPattern(DiagonalCurvedTSection, DiagonalCurvedTSection2, DiagonalCurvedTSection3, DiagonalCurvedTSection4);
      setupBarredPattern(OrnateBarred, OrnateBarred2);
      setupBarredPattern(OrnateThinBarred, OrnateThinBarred2);
      setupBarredPattern(SplitBarred, SplitBarred2);
      setupCornerPattern(SplitBarredCorner, SplitBarredCorner2, SplitBarredCorner3, SplitBarredCorner4);
      setupTSectionPattern(SplitBarredTSection, SplitBarredTSection2, SplitBarredTSection3, SplitBarredTSection4);
      setupBarredPattern(SplitBarredEnd, SplitBarredEnd2, SplitBarredEnd3, SplitBarredEnd4);
      setupRotation(Creeper, Creeper2, Creeper3, Creeper4);
      setupDiagonalPattern(DiagonalStriped, DiagonalStriped2);
      setupDiagonalPattern(ThinDiagonalStriped, ThinDiagonalStriped2);
      setupCornerPattern(Diagonal1Edged, Diagonal1Edged2, Diagonal1Edged3, Diagonal1Edged4);
      setupCornerPattern(ThickDiagonal1Edged, ThickDiagonal1Edged2, ThickDiagonal1Edged3, ThickDiagonal1Edged4);
      setupInvert2Rot(DiagonalHalved, DiagonalHalved2);
      set2Rotation(Diagonal2Edged, Diagonal2Edged2);
      setupBarredPattern(ThinBarredEnd, ThinBarredEnd2, ThinBarredEnd3, ThinBarredEnd4);
      setupBarredPattern(ThickBarredEnd, ThickBarredEnd2, ThickBarredEnd3, ThickBarredEnd4);
      OverlappedBarred.setLeftRotation(OverlappedBarred2, false);
      OverlappedBarred2.setLeftRotation(OverlappedBarred, false);
      OverlappedSplitBarred.setLeftRotation(OverlappedSplitBarred2, false);
      OverlappedSplitBarred2.setLeftRotation(OverlappedSplitBarred, false);
   }
}
