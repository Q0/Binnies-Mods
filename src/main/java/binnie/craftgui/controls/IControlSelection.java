package binnie.craftgui.controls;

public interface IControlSelection {
    Object getSelectedValue();

    void setSelectedValue(Object var1);

    boolean isSelected(IControlSelectionOption var1);
}
