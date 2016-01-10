package binnie.extratrees.carpentry;

import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignCategory;
import binnie.extratrees.api.ILayout;
import binnie.extratrees.carpentry.EnumPattern;
import binnie.extratrees.carpentry.Layout;
import java.util.ArrayList;
import java.util.List;

public enum EnumDesign implements IDesign {
   Blank("Blank"),
   Octagon("Octagon"),
   Diamond("Diamond"),
   Ringed("Ringed"),
   Squared("Squared"),
   Multiply("Multiply"),
   Halved("Halved"),
   Striped("Striped"),
   ThinStriped("Thin Striped"),
   Chequered("Full Chequered"),
   Tiled("Full Tiled"),
   ChequeredB("Chequered"),
   TiledB("Tiled"),
   VeryThinCorner("Very Thin Cornered"),
   ThinCorner("Thin Cornered"),
   Corner("Cornered"),
   ThickCorner("Thick Cornered"),
   Edged("Edged"),
   ThinEdged("Thin Edged"),
   ThinBarred("Thin Barred"),
   Barred("Barred"),
   ThickBarred("Thick Barred"),
   Diagonal("Diagonal"),
   ThickDiagonal("Thick Diagonal"),
   ThinSaltire("Thin Saltire"),
   Saltire("Saltire"),
   ThickSaltire("Thick Saltire"),
   ThinCrossed("Thin Crossed"),
   Crossed("Crossed"),
   ThickCrossed("Thick Crossed"),
   ThinTSection("Thin T Section"),
   TSection("T Section"),
   ThickTSection("Thick T Section"),
   ThinBarredCorner("Thin Barred Corner"),
   BarredCorner("Barred Corner"),
   ThickBarredCorner("Thick Barred Corner"),
   ThinStripedCorner("Thin Striped Corner"),
   StripedCorner("Striped Corner"),
   Emblem1("Emblem 1"),
   Emblem2("Emblem 2"),
   Emblem3("Emblem 3"),
   Emblem4("Emblem 4"),
   Emblem5("Emblem 5"),
   LetterA("Letter A"),
   LetterB("Letter B"),
   LetterC("Letter C"),
   LetterD("Letter D"),
   LetterE("Letter E"),
   LetterF("Letter F"),
   LetterG("Letter G"),
   LetterH("Letter H"),
   LetterI("Letter I"),
   LetterJ("Letter J"),
   LetterK("Letter K"),
   LetterL("Letter L"),
   LetterM("Letter M"),
   LetterN("Letter N"),
   LetterO("Letter O"),
   LetterP("Letter P"),
   LetterQ("Letter Q"),
   LetterR("Letter R"),
   LetterS("Letter S"),
   LetterT("Letter T"),
   LetterU("Letter U"),
   LetterV("Letter V"),
   LetterW("Letter W"),
   LetterX("Letter X"),
   LetterY("Letter Y"),
   LetterZ("Letter Z"),
   ThinCurvedCrossed("Thin Curved Crossed"),
   ThinCurvedBarredCorner("Thin Curved Barred Corner"),
   CurvedBarredCorner("Curved Barred Corner"),
   ThinCurvedCorner("Thin Curved Corner"),
   CurvedCorner("Curved Corner"),
   ThinCurvedTSection("Thin Curved T Section"),
   CurvedTSection("Curved T Section"),
   BarredEnd("Barred End"),
   DiagonalCorner("Diagonal Corner"),
   DiagonalTSection("Diagonal T Section"),
   DiagonalCurvedCorner("Diagonal Curved Corner"),
   DiagonalCurvedTSection("Diagonal Curved T Section"),
   OrnateBarred("Ornate Barred"),
   SplitBarred("Split Barred"),
   SplitBarredCorner("Split Barred Corner"),
   SplitBarredTSection("Split Barred T Section"),
   SplitCrossed("Split Crossed"),
   SplitBarredEnd("Split Barred End"),
   OrnateThinBarred("Ornate Thin Barred"),
   Circle("Circle"),
   Plus("Plus"),
   Creeper("Creeper"),
   OrnateStripedCorner("Ornate Striped Corner"),
   Test("Testing Block"),
   DiagonalHalved("Diagonal Halved"),
   Diagonal1Edged("Cornered Diagonal"),
   Diagonal2Edged("Opposite Cornered Diagonal"),
   ThickDiagonal1Edged("Thick Cornered Diagonal"),
   ThinBarredEnd("Thin Barred End"),
   ThickBarredEnd("Thick Barred End"),
   OverlappedBarred("Overlapped Barred"),
   OverlappedSplitBarred("Overlapped Split Barred");

   String name;
   ILayout topPattern = Layout.get(EnumPattern.Blank, false);
   ILayout bottomPattern = Layout.get(EnumPattern.Blank, false);
   ILayout northPattern = Layout.get(EnumPattern.Blank, false);
   ILayout southPattern = Layout.get(EnumPattern.Blank, false);
   ILayout eastPattern = Layout.get(EnumPattern.Blank, false);
   ILayout westPattern = Layout.get(EnumPattern.Blank, false);

   void setEdgePatterns(ILayout north, ILayout east, ILayout south, ILayout west) {
      this.setNorthPattern(north);
      this.setEastPattern(east);
      this.setSouthPattern(south);
      this.setWestPattern(west);
   }

   void setEdgePatterns(ILayout face) {
      this.setEdgePatterns(face, face, face, face);
   }

   void setupStriped(ILayout vert) {
      this.setTopPattern(vert);
      this.setEdgePatterns(vert.invert(), EnumPattern.Blank.layout(true), vert, EnumPattern.Blank.layout());
   }

   void setChequered(ILayout cheq) {
      this.setAllPatterns(cheq);
      this.setNorthPattern(cheq.invert());
      this.setSouthPattern(cheq.invert());
      this.setBottomPattern(cheq.invert());
   }

   void setStripedChequered(ILayout cheq, ILayout stripe) {
      this.setAllPatterns(cheq);
      this.setNorthPattern(stripe.invert());
      this.setSouthPattern(stripe.invert());
      this.setEastPattern(stripe);
      this.setWestPattern(stripe);
   }

   void setCornered(ILayout corner, ILayout edge) {
      this.setTopPattern(corner);
      this.setNorthPattern(EnumPattern.Blank.layout());
      this.setEastPattern(edge.flipHorizontal());
      this.setSouthPattern(edge);
      this.setWestPattern(EnumPattern.Blank.layout());
   }

   void setEdged(ILayout edge) {
      this.setAllPatterns(edge);
      this.setWestPattern(EnumPattern.Blank.layout());
      this.setEastPattern(EnumPattern.Blank.layout(true));
      this.northPattern = this.northPattern.flipHorizontal();
   }

   void setBarred(ILayout bar) {
      this.setAllPatterns(bar);
      this.setEastPattern(EnumPattern.Blank.layout(true));
      this.setWestPattern(EnumPattern.Blank.layout(true));
   }

   void setDiagonal(ILayout diagonal, ILayout edged) {
      this.setAllPatterns(edged);
      this.setTopPattern(diagonal);
      this.northPattern = this.northPattern.flipHorizontal();
      this.southPattern = this.southPattern.flipHorizontal();
   }

   void setSaltire(ILayout saltire, ILayout bar) {
      this.setTopPattern(saltire);
      this.setEdgePatterns(bar.invert());
   }

   void setCross(ILayout saltire, ILayout bar) {
      this.setTopPattern(saltire);
      this.setEdgePatterns(bar);
   }

   void setTSection(ILayout tsection, ILayout bar) {
      this.setTopPattern(tsection);
      this.setEdgePatterns(bar);
      this.setWestPattern(EnumPattern.Blank.layout(true));
   }

   void setBarredCorner(ILayout corner, ILayout bar) {
      this.setTSection(corner, bar);
      this.setNorthPattern(EnumPattern.Blank.layout(true));
   }

   void setStripedCorner(ILayout corner, ILayout striped) {
      this.setCornered(corner, striped);
   }

   void setLetterPattern(ILayout letter) {
      this.setAllPatterns(EnumPattern.Blank.layout(true));
      this.setTopPattern(letter);
      this.setBottomPattern(EnumPattern.Blank.layout(true));
   }

   void setBarredEndPattern(ILayout end, ILayout bar) {
      this.setAllPatterns(EnumPattern.Blank.layout(true));
      this.setTopPattern(end);
      this.setWestPattern(bar);
   }

   void setDiagonalCorner(ILayout diagonal, ILayout bar, ILayout edged) {
      this.setAllPatterns(EnumPattern.Blank.layout(true));
      this.setTopPattern(diagonal);
      this.setWestPattern(bar.invert());
      this.setNorthPattern(edged.flipHorizontal());
      this.setSouthPattern(edged);
   }

   void setDiagonalTSection(ILayout diagonal, ILayout bar, ILayout edged) {
      this.setAllPatterns(EnumPattern.Blank.layout(true));
      this.setTopPattern(diagonal);
      this.setWestPattern(bar.invert());
      this.setNorthPattern(bar.invert());
      this.setEastPattern(edged.flipHorizontal());
      this.setSouthPattern(edged);
   }

   private EnumDesign(String name) {
      this.name = name;
   }

   private void setAllPatterns(ILayout layout) {
      this.setTopPattern(layout);
      this.setBottomPattern(layout);
      this.setNorthPattern(layout);
      this.setEastPattern(layout);
      this.setSouthPattern(layout);
      this.setWestPattern(layout);
   }

   public String getName() {
      return this.name;
   }

   public ILayout getTopPattern() {
      return this.topPattern;
   }

   public void setTopPattern(ILayout layout) {
      this.topPattern = layout;
      this.setBottomPattern(layout);
   }

   public ILayout getBottomPattern() {
      return this.bottomPattern;
   }

   public void setBottomPattern(ILayout layout) {
      this.bottomPattern = layout;
   }

   public ILayout getNorthPattern() {
      return this.northPattern;
   }

   public void setNorthPattern(ILayout layout) {
      this.northPattern = layout;
   }

   public ILayout getSouthPattern() {
      return this.southPattern;
   }

   public void setSouthPattern(ILayout layout) {
      this.southPattern = layout;
   }

   public ILayout getEastPattern() {
      return this.eastPattern;
   }

   public void setEastPattern(ILayout layout) {
      this.eastPattern = layout;
   }

   public ILayout getWestPattern() {
      return this.westPattern;
   }

   public void setWestPattern(ILayout layout) {
      this.westPattern = layout;
   }

   public void setName(String name) {
      this.name = name;
   }

   static {
      EnumDesign.Category.Design.addDesign(Blank);
      EnumDesign.Category.Design.addDesign(Octagon);
      EnumDesign.Category.Design.addDesign(Diamond);
      EnumDesign.Category.Design.addDesign(Ringed);
      EnumDesign.Category.Design.addDesign(Squared);
      EnumDesign.Category.Design.addDesign(Multiply);
      EnumDesign.Category.Design.addDesign(Plus);
      EnumDesign.Category.Design.addDesign(Circle);
      EnumDesign.Category.Design.addDesign(Emblem1);
      EnumDesign.Category.Design.addDesign(Emblem2);
      EnumDesign.Category.Design.addDesign(Emblem3);
      EnumDesign.Category.Design.addDesign(Emblem4);
      EnumDesign.Category.Design.addDesign(Emblem5);
      EnumDesign.Category.Design.addDesign(Creeper);
      EnumDesign.Category.Stripes.addDesign(Chequered);
      EnumDesign.Category.Stripes.addDesign(ChequeredB);
      EnumDesign.Category.Stripes.addDesign(Tiled);
      EnumDesign.Category.Stripes.addDesign(TiledB);
      EnumDesign.Category.Stripes.addDesign(Striped);
      EnumDesign.Category.Stripes.addDesign(ThinStriped);
      EnumDesign.Category.Stripes.addDesign(ThinStripedCorner);
      EnumDesign.Category.Stripes.addDesign(StripedCorner);
      EnumDesign.Category.Stripes.addDesign(OrnateStripedCorner);
      EnumDesign.Category.Edges.addDesign(Halved);
      EnumDesign.Category.Edges.addDesign(Corner);
      EnumDesign.Category.Edges.addDesign(ThickCorner);
      EnumDesign.Category.Edges.addDesign(Edged);
      EnumDesign.Category.Edges.addDesign(ThinCorner);
      EnumDesign.Category.Edges.addDesign(ThinEdged);
      EnumDesign.Category.Edges.addDesign(VeryThinCorner);
      EnumDesign.Category.Edges.addDesign(ThinCurvedCorner);
      EnumDesign.Category.Edges.addDesign(CurvedCorner);
      EnumDesign.Category.Barred.addDesign(ThinBarred);
      EnumDesign.Category.Barred.addDesign(ThinBarredCorner);
      EnumDesign.Category.Barred.addDesign(ThinTSection);
      EnumDesign.Category.Barred.addDesign(ThinCrossed);
      EnumDesign.Category.Barred.addDesign(ThinBarredEnd);
      EnumDesign.Category.Barred.addDesign(OrnateThinBarred);
      EnumDesign.Category.Barred.addDesign(Barred);
      EnumDesign.Category.Barred.addDesign(BarredCorner);
      EnumDesign.Category.Barred.addDesign(TSection);
      EnumDesign.Category.Barred.addDesign(Crossed);
      EnumDesign.Category.Barred.addDesign(BarredEnd);
      EnumDesign.Category.Barred.addDesign(OverlappedBarred);
      EnumDesign.Category.Barred.addDesign(OrnateBarred);
      EnumDesign.Category.Barred.addDesign(ThickBarred);
      EnumDesign.Category.Barred.addDesign(ThickBarredCorner);
      EnumDesign.Category.Barred.addDesign(ThickTSection);
      EnumDesign.Category.Barred.addDesign(ThickCrossed);
      EnumDesign.Category.Barred.addDesign(ThickBarredEnd);
      EnumDesign.Category.Barred.addDesign(ThinCurvedBarredCorner);
      EnumDesign.Category.Barred.addDesign(CurvedBarredCorner);
      EnumDesign.Category.Barred.addDesign(ThinCurvedTSection);
      EnumDesign.Category.Barred.addDesign(CurvedTSection);
      EnumDesign.Category.Barred.addDesign(ThinCurvedCrossed);
      EnumDesign.Category.Barred.addDesign(SplitBarred);
      EnumDesign.Category.Barred.addDesign(SplitBarredCorner);
      EnumDesign.Category.Barred.addDesign(SplitBarredTSection);
      EnumDesign.Category.Barred.addDesign(SplitCrossed);
      EnumDesign.Category.Barred.addDesign(SplitBarredEnd);
      EnumDesign.Category.Barred.addDesign(OverlappedSplitBarred);
      EnumDesign.Category.Diagonal.addDesign(ThinSaltire);
      EnumDesign.Category.Diagonal.addDesign(Diagonal);
      EnumDesign.Category.Diagonal.addDesign(DiagonalCorner);
      EnumDesign.Category.Diagonal.addDesign(DiagonalTSection);
      EnumDesign.Category.Diagonal.addDesign(DiagonalCurvedCorner);
      EnumDesign.Category.Diagonal.addDesign(DiagonalCurvedTSection);
      EnumDesign.Category.Diagonal.addDesign(Saltire);
      EnumDesign.Category.Diagonal.addDesign(ThickDiagonal);
      EnumDesign.Category.Diagonal.addDesign(ThickSaltire);
      EnumDesign.Category.Diagonal.addDesign(DiagonalHalved);
      EnumDesign.Category.Diagonal.addDesign(Diagonal1Edged);
      EnumDesign.Category.Diagonal.addDesign(Diagonal2Edged);
      EnumDesign.Category.Diagonal.addDesign(ThickDiagonal1Edged);
      Octagon.setAllPatterns(EnumPattern.Octagon.layout());
      Diamond.setAllPatterns(EnumPattern.Diamond.layout());
      Ringed.setAllPatterns(EnumPattern.Ringed.layout());
      Squared.setAllPatterns(EnumPattern.Squared.layout());
      Multiply.setAllPatterns(EnumPattern.Multiply.layout());
      ThinStriped.setupStriped(EnumPattern.ThinStriped.layout());
      Striped.setupStriped(EnumPattern.Striped.layout());
      Halved.setupStriped(EnumPattern.Halved.layout());
      Chequered.setChequered(EnumPattern.Chequered.layout());
      Tiled.setChequered(EnumPattern.Tiled.layout());
      ChequeredB.setStripedChequered(EnumPattern.Chequered.layout(), EnumPattern.Halved.layout());
      TiledB.setStripedChequered(EnumPattern.Tiled.layout(), EnumPattern.Striped.layout());
      VeryThinCorner.setCornered(EnumPattern.VeryThinCorner.layout(), EnumPattern.ThinEdged.layout());
      ThinCorner.setCornered(EnumPattern.ThinCorner.layout(), EnumPattern.Edged.layout());
      Corner.setCornered(EnumPattern.Corner.layout(), EnumPattern.Halved.layout());
      ThickCorner.setCornered(EnumPattern.ThickCorner.layout(), EnumPattern.Edged.layout(true).flipHorizontal());
      ThinCurvedCorner.setCornered(EnumPattern.ThinCurvedCorner.layout(), EnumPattern.Edged.layout());
      CurvedCorner.setCornered(EnumPattern.CurvedCorner.layout(), EnumPattern.Halved.layout());
      Edged.setEdged(EnumPattern.Edged.layout());
      ThinEdged.setEdged(EnumPattern.ThinEdged.layout());
      ThinBarred.setBarred(EnumPattern.ThinBarred.layout());
      Barred.setBarred(EnumPattern.Barred.layout());
      ThickBarred.setBarred(EnumPattern.ThickBarred.layout());
      Diagonal.setDiagonal(EnumPattern.Diagonal.layout(), EnumPattern.Edged.layout());
      ThickDiagonal.setDiagonal(EnumPattern.ThickDiagonal.layout(), EnumPattern.Halved.layout());
      ThinSaltire.setSaltire(EnumPattern.ThinSaltire.layout(), EnumPattern.ThickBarred.layout());
      Saltire.setSaltire(EnumPattern.Saltire.layout(), EnumPattern.Barred.layout());
      ThickSaltire.setSaltire(EnumPattern.ThickSaltire.layout(), EnumPattern.ThinBarred.layout());
      ThinCrossed.setCross(EnumPattern.ThinCrossed.layout(), EnumPattern.ThinBarred.layout());
      Crossed.setCross(EnumPattern.Crossed.layout(), EnumPattern.Barred.layout());
      ThickCrossed.setCross(EnumPattern.ThickCrossed.layout(), EnumPattern.ThickBarred.layout());
      ThinCurvedCrossed.setCross(EnumPattern.ThinCurvedCrossed.layout(), EnumPattern.ThinBarred.layout());
      ThinTSection.setTSection(EnumPattern.ThinTSection.layout(), EnumPattern.ThinBarred.layout());
      TSection.setTSection(EnumPattern.TSection.layout(), EnumPattern.Barred.layout());
      ThickTSection.setTSection(EnumPattern.ThickTSection.layout(), EnumPattern.ThickBarred.layout());
      ThinCurvedTSection.setTSection(EnumPattern.ThinCurvedTSection.layout(), EnumPattern.ThinBarred.layout());
      CurvedTSection.setTSection(EnumPattern.CurvedTSection.layout(), EnumPattern.Barred.layout());
      ThinBarredCorner.setBarredCorner(EnumPattern.ThinBarredCorner.layout(), EnumPattern.ThinBarred.layout());
      BarredCorner.setBarredCorner(EnumPattern.BarredCorner.layout(), EnumPattern.Barred.layout());
      ThickBarredCorner.setBarredCorner(EnumPattern.ThickBarredCorner.layout(), EnumPattern.ThickBarred.layout());
      ThinCurvedBarredCorner.setBarredCorner(EnumPattern.ThinCurvedBarredCorner.layout(), EnumPattern.ThinBarred.layout());
      CurvedBarredCorner.setBarredCorner(EnumPattern.BarredCurvedCorner.layout(), EnumPattern.Barred.layout());
      ThinStripedCorner.setStripedCorner(EnumPattern.ThinStripedCorner.layout(), EnumPattern.ThinStriped.layout());
      StripedCorner.setStripedCorner(EnumPattern.StripedCorner.layout(), EnumPattern.Striped.layout());
      OrnateStripedCorner.setStripedCorner(EnumPattern.OrnateStripedCorner.layout(), EnumPattern.ThinStriped.layout());
      Emblem1.setAllPatterns(EnumPattern.Emblem1.layout());
      Emblem2.setAllPatterns(EnumPattern.Emblem2.layout());
      Emblem3.setAllPatterns(EnumPattern.Emblem3.layout());
      Emblem4.setAllPatterns(EnumPattern.Emblem4.layout());
      Emblem5.setAllPatterns(EnumPattern.Emblem5.layout());
      LetterA.setLetterPattern(EnumPattern.LetterA.layout());
      LetterB.setLetterPattern(EnumPattern.LetterB.layout());
      LetterF.setLetterPattern(EnumPattern.LetterF.layout());
      LetterS.setLetterPattern(EnumPattern.LetterS.layout());
      LetterT.setLetterPattern(EnumPattern.LetterT.layout());
      BarredEnd.setBarredEndPattern(EnumPattern.BarredEnd.layout(), EnumPattern.Barred.layout());
      DiagonalCorner.setDiagonalCorner(EnumPattern.DiagonalCorner.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
      DiagonalTSection.setDiagonalTSection(EnumPattern.DiagonalTSection.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
      DiagonalCurvedCorner.setDiagonalCorner(EnumPattern.DiagonalCurvedCorner.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
      DiagonalCurvedTSection.setDiagonalTSection(EnumPattern.DiagonalCurvedTSection.layout(), EnumPattern.Barred.layout(), EnumPattern.Edged.layout());
      OrnateBarred.setBarred(EnumPattern.OrnateBarred.layout());
      OrnateThinBarred.setBarred(EnumPattern.OrnateThinBarred.layout());
      SplitBarred.setBarred(EnumPattern.SplitBarred.layout());
      SplitBarredCorner.setBarredCorner(EnumPattern.SplitBarredCorner.layout(), EnumPattern.SplitBarred.layout());
      SplitBarredTSection.setTSection(EnumPattern.SplitBarredTSection.layout(), EnumPattern.SplitBarred.layout());
      SplitCrossed.setCross(EnumPattern.SplitCrossed.layout(), EnumPattern.SplitBarred.layout());
      SplitBarredEnd.setBarredEndPattern(EnumPattern.SplitBarredEnd.layout(), EnumPattern.SplitBarred.layout());
      Circle.setAllPatterns(EnumPattern.Circle.layout());
      Plus.setAllPatterns(EnumPattern.Plus.layout());
      Creeper.setAllPatterns(EnumPattern.Blank.layout(true));
      Creeper.setTopPattern(EnumPattern.Creeper.layout());
      DiagonalHalved.setTopPattern(EnumPattern.DiagonalHalved.layout());
      DiagonalHalved.setEdgePatterns(EnumPattern.Blank.layout(), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout());
      Diagonal1Edged.setTopPattern(EnumPattern.Diagonal1Edged.layout());
      Diagonal1Edged.setEdgePatterns(EnumPattern.Edged.layout().flipHorizontal(), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout(true), EnumPattern.Edged.layout());
      Diagonal2Edged.setTopPattern(EnumPattern.Diagonal2Edged.layout());
      Diagonal2Edged.setEdgePatterns(EnumPattern.Edged.layout(), EnumPattern.Edged.layout().flipHorizontal(), EnumPattern.Edged.layout(), EnumPattern.Edged.layout().flipHorizontal());
      ThickDiagonal1Edged.setTopPattern(EnumPattern.ThickDiagonal1Edged.layout());
      ThickDiagonal1Edged.setEdgePatterns(EnumPattern.Halved.layout().flipHorizontal(), EnumPattern.Blank.layout(true), EnumPattern.Blank.layout(true), EnumPattern.Halved.layout());
      ThickBarredEnd.setBarredEndPattern(EnumPattern.ThickBarredEnd.layout(), EnumPattern.ThickBarred.layout());
      ThinBarredEnd.setBarredEndPattern(EnumPattern.ThinBarredEnd.layout(), EnumPattern.ThinBarred.layout());
      OverlappedSplitBarred.setAllPatterns(EnumPattern.SplitBarred.layout());
      OverlappedSplitBarred.setTopPattern(EnumPattern.OverlappedSplitBarred.layout());
      OverlappedBarred.setAllPatterns(EnumPattern.Barred.layout());
      OverlappedBarred.setTopPattern(EnumPattern.OverlappedBarred.layout());
   }

   static enum Category implements IDesignCategory {
      Design("Designs & Emblems"),
      Stripes("Squares & Stripes"),
      Edges("Edges"),
      Barred("Bars"),
      Letters("Letters"),
      Diagonal("Diagonals");

      String name;
      List designs = new ArrayList();

      private Category(String name) {
         this.name = name;
         CarpentryManager.carpentryInterface.registerDesignCategory(this);
      }

      public String getName() {
         return this.name;
      }

      public List getDesigns() {
         return this.designs;
      }

      public void addDesign(IDesign design) {
         this.designs.add(design);
      }

      public String getId() {
         return this.toString().toLowerCase();
      }
   }
}
