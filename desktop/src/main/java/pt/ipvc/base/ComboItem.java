package pt.ipvc.base;


public class ComboItem {

    private final String label;
    private final EventOnSelected event;

    public ComboItem(String label, EventOnSelected event) {
        this.label = label;
        this.event = event;
    }

    public void onSelected() {
        event.onSelected();
    }

    public String getLabel() {
        return this.label;
    }
}
