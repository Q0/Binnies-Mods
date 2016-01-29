package binnie.core.machines.inventory;

import binnie.core.util.IValidator;
import forestry.api.core.INBTTagable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;
import java.util.EnumSet;

abstract class BaseSlot<T> implements INBTTagable, IValidator<T> {
    protected String unlocName;
    Validator<T> validator;
    private SidedAccess access;
    private boolean readOnly;
    private int index;

    public BaseSlot(final int index, final String unlocName) {
        this.access = new SidedAccess();
        this.validator = null;
        this.readOnly = false;
        this.unlocName = "";
        this.setIndex(index);
        this.setUnlocalisedName(unlocName);
    }

    public void setReadOnly() {
        this.readOnly = true;
        this.forbidInsertion();
    }

    public boolean isValid(final T item) {
        return item == null || this.validator == null || this.validator.isValid(item);
    }

    public abstract T getContent();

    public abstract void setContent(final T p0);

    public boolean isEmpty() {
        return this.getContent() == null;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public int getIndex() {
        return this.index;
    }

    private void setIndex(final int index) {
        this.index = index;
    }

    public boolean canInsert() {
        return !this.access.getInsertionSides().isEmpty();
    }

    public boolean canExtract() {
        return !this.access.getExtractionSides().isEmpty();
    }

    public void forbidInteraction() {
        this.forbidInsertion();
        this.forbidExtraction();
    }

    public void forbidExtraction() {
        this.access.setExtract(false);
        this.access.forbidExtractChange();
    }

    public void forbidInsertion() {
        this.access.setInsert(false);
        this.access.forbidInsertChange();
    }

    public boolean canInsert(final ForgeDirection dir) {
        return this.access.canInsert(dir);
    }

    public boolean canExtract(final ForgeDirection dir) {
        return this.access.canExtract(dir);
    }

    public Collection<ForgeDirection> getInputSides() {
        return this.access.getInsertionSides();
    }

    public void setInputSides(final EnumSet<ForgeDirection> sides) {
        for (final ForgeDirection side : EnumSet.complementOf(sides)) {
            if (side != ForgeDirection.UNKNOWN) {
                this.access.setInsert(side, false);
            }
        }
    }

    public Collection<ForgeDirection> getOutputSides() {
        return this.access.getExtractionSides();
    }

    public void setOutputSides(final EnumSet<ForgeDirection> sides) {
        for (final ForgeDirection side : EnumSet.complementOf(sides)) {
            if (side != ForgeDirection.UNKNOWN) {
                this.access.setExtract(side, false);
            }
        }
    }

    public void setUnlocalisedName(final String name) {
        this.unlocName = name;
    }

    public abstract String getName();

    public Validator<T> getValidator() {
        return this.validator;
    }

    public void setValidator(final Validator<T> val) {
        this.validator = val;
    }
}
