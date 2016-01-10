package binnie.core.network.packet;

public class IndexInPayload {
    public int intIndex = 0;
    public int floatIndex = 0;
    public int stringIndex = 0;

    public IndexInPayload(int intIndex, int floatIndex, int stringIndex) {
        super();
        this.intIndex = intIndex;
        this.floatIndex = floatIndex;
        this.stringIndex = stringIndex;
    }
}
