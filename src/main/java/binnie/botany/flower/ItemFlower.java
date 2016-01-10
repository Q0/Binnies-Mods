package binnie.botany.flower;

import binnie.botany.api.EnumFlowerStage;

public class ItemFlower extends ItemBotany {
    public ItemFlower() {
        super("itemFlower");
    }

    public EnumFlowerStage getStage() {
        return EnumFlowerStage.FLOWER;
    }

    public String getTag() {
        return "";
    }
}
