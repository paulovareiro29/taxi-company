package pt.ipvc.components.inputs;

public class NumericField extends TextField{

    public NumericField(int value) {
        this();
        getInput().setText("" + value);
    }

    public NumericField() {
        getInput().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            getInput().setText(newValue.replaceAll("[^\\d]", ""));
        });
    }
}
