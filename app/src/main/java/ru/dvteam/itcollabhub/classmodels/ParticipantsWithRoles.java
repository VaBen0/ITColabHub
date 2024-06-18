package ru.dvteam.itcollabhub.classmodels;

public class ParticipantsWithRoles {
    private String valueText = "";
    private int valueId = 0;

    private boolean itemParent = false;
    private int parentId = -1;
    private boolean childVisibility = false;

    public boolean isItemParent() {
        return itemParent;
    }

    public void setItemParent(boolean newItemParent) {
        itemParent = newItemParent;
    }

    public boolean isChildVisibility() {
        return childVisibility;
    }

    public void setChildVisibility(boolean newChildVisibility) {
        childVisibility = newChildVisibility;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int newParentId) {
        parentId = newParentId;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String newValueText) {
        valueText = newValueText;
    }

    public int getValueId() {
        return valueId;
    }

    public void setValueId(int newValueId) {
        valueId = newValueId;
    }
}
