package binnie.botany.genetics;

import binnie.botany.api.IColourMix;
import binnie.botany.api.IFlowerColour;

public class ColourMix implements IColourMix {
    IFlowerColour colour1;
    IFlowerColour colour2;
    IFlowerColour result;
    int chance;

    public IFlowerColour getColour1() {
        return this.colour1;
    }

    public IFlowerColour getColour2() {
        return this.colour2;
    }

    public boolean isMutation(IFlowerColour a, IFlowerColour b) {
        return a == this.colour1 && b == this.colour2 || a == this.colour2 && b == this.colour1;
    }

    public int getChance() {
        return this.chance;
    }

    public IFlowerColour getResult() {
        return this.result;
    }

    public ColourMix(IFlowerColour colour1, IFlowerColour colour2, IFlowerColour result, int chance) {
        super();
        this.colour1 = colour1;
        this.colour2 = colour2;
        this.result = result;
        this.chance = chance;
    }
}
