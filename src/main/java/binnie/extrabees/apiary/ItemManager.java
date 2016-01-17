package binnie.extrabees.apiary;

public class ItemManager {
    public static ItemHiveFrame cocoaFrame;
    public static ItemHiveFrame soulFrame;
    public static ItemHiveFrame cageFrame;
    public static ItemHiveFrame clayFrame;
    public static ItemHiveFrame debugFrame;

    //---------------------------------------------------------------------------
    //
    // PUBLIC METHODS
    //
    //---------------------------------------------------------------------------

    public static void registerItems() {
        /**
         * Frames
         */
        cocoaFrame = new ItemHiveFrame("hiveFrame.cocoa", 240, 1.0f);
        HiveFrameBeeModifier modifier = (HiveFrameBeeModifier) cocoaFrame.getBeeModifier();
        modifier.setProductionModifier(1.5f, 5.0f);
        modifier.setLifespanModifier(0.75f, 0.25f);

        cageFrame = new ItemHiveFrame("hiveFrame.cage", 240, 1.0f);
        modifier = (HiveFrameBeeModifier) cageFrame.getBeeModifier();
        modifier.setProductionModifier(0.75f, 0.5f);
        modifier.setLifespanModifier(0.75f, 0.5f);
        modifier.setTerritoryModifier(0.5f, 0.1f);

        soulFrame = new ItemHiveFrame("hiveFrame.soul", 80, 1.0f);
        modifier = (HiveFrameBeeModifier) soulFrame.getBeeModifier();
        modifier.setProductionModifier(0.75f, 0.5f);
        modifier.setLifespanModifier(0.75f, 0.5f);
        modifier.setTerritoryModifier(0.5f, 0.1f);
        modifier.setMutationModifier(1.5f, 5.0f);

        clayFrame = new ItemHiveFrame("hiveFrame.clay", 80, 1.0f);
        modifier = (HiveFrameBeeModifier) clayFrame.getBeeModifier();
        modifier.setProductionModifier(0.75f, 0.2f);
        modifier.setLifespanModifier(1.5f, 5.0f);
        modifier.setMutationModifier(0.5f, 0.2f);

        debugFrame = new ItemHiveFrame("hiveFrame.debug", 1000, 1.0f);
        modifier = (HiveFrameBeeModifier) soulFrame.getBeeModifier();
        modifier.setLifespanModifier(1.0E-4f, 1.0E-4f);
    }
}
