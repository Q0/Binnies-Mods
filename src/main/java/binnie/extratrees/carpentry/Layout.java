package binnie.extratrees.carpentry;

import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.ILayout;
import binnie.extratrees.api.IPattern;
import net.minecraft.util.IIcon;

public class Layout implements ILayout {
    IPattern pattern;
    boolean inverted;

    public IPattern getPattern() {
        return this.pattern;
    }

    public boolean isInverted() {
        return this.inverted;
    }

    private Layout(IPattern pattern, boolean inverted) {
        super();
        this.pattern = pattern;
        this.inverted = inverted;
    }

    private Layout(IPattern pattern) {
        this(pattern, false);
    }

    ILayout newLayout(ILayout newLayout) {
        return new Layout(newLayout.getPattern(), this.inverted ^ newLayout.isInverted());
    }

    public ILayout rotateRight() {
        return this.rotateLeft().rotateLeft().rotateLeft();
    }

    public ILayout rotateLeft() {
        return this.newLayout(this.pattern.getRotation());
    }

    public ILayout flipHorizontal() {
        return this.newLayout(this.pattern.getHorizontalFlip());
    }

    public ILayout flipVertical() {
        return this.newLayout(this.pattern.getHorizontalFlip().rotateLeft().rotateLeft());
    }

    public IIcon getPrimaryIcon(IDesignSystem system) {
        return this.inverted ? this.pattern.getSecondaryIcon(system) : this.pattern.getPrimaryIcon(system);
    }

    public IIcon getSecondaryIcon(IDesignSystem system) {
        return this.inverted ? this.pattern.getPrimaryIcon(system) : this.pattern.getSecondaryIcon(system);
    }

    public ILayout invert() {
        return new Layout(this.pattern, !this.inverted);
    }

    public static ILayout get(IPattern pattern, boolean inverted) {
        return new Layout(pattern, inverted);
    }

    public static ILayout get(IPattern pattern) {
        return new Layout(pattern, false);
    }
}
