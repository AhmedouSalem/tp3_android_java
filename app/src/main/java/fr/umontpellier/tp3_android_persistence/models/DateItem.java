package fr.umontpellier.tp3_android_persistence.models;

public class DateItem {
    private String label;
    private String fullDate;
    private boolean isSelected;

    public DateItem(String label, String fullDate, boolean isSelected) {
        this.label = label;
        this.fullDate = fullDate;
        this.isSelected = isSelected;
    }

    public String getLabel() {
        return label;
    }

    public String getFullDate() {
        return fullDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

