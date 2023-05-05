package pt.ipvc.layout.popup.taxi;

import javafx.collections.ObservableList;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.ComboItem;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.bll.BrandBLL;
import pt.ipvc.bll.ModelBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonAppearance;
import pt.ipvc.components.inputs.ComboBox;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.dal.Brand;
import pt.ipvc.dal.Model;
import pt.ipvc.utils.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CreateTaxiPopup extends Popup {

    private Brand selectedBrand;
    private Model selectedModel;

    private final TextField plateField;
    private final TextField occupancyField;
    private final TextField yearField;
    private final TextField colorField;
    private final ComboBox brandField;
    private final ComboBox modelField;

    public CreateTaxiPopup(EventListener listener) {
        super("New Taxi", listener);

        plateField = new TextField();
        plateField.setPromptText("License Plate");
        plateField.setIcon("car--secondary.png");

        occupancyField = new TextField();
        occupancyField.setPromptText("Occupancy");
        occupancyField.setIcon("double-users--secondary.png");

        yearField = new TextField();
        yearField.setPromptText("Year");
        yearField.setIcon("calendar--secondary.png");

        colorField = new TextField();
        colorField.setPromptText("Color");
        colorField.setIcon("brush--secondary.png");

        brandField = new ComboBox(Collections.emptyList());
        brandField.setPrefWidth(Double.MAX_VALUE);
        brandField.setPromptText("Select brand");

        modelField = new ComboBox(Collections.emptyList());
        modelField.setPrefWidth(Double.MAX_VALUE);
        modelField.setPromptText("Select model");
        modelField.setVisible(false);

        Button cancelButton = new Button("Cancel", ButtonAppearance.outlined_primary);
        Button submitButton = new Button("Create");

        cancelButton.setOnAction(e -> {
            listener.onCancel();
            hide();
        });
        submitButton.setOnAction(e -> handleSubmitButton());

        HBox options = new HBox(8);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        HBox.setHgrow(submitButton, Priority.ALWAYS);
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        submitButton.setMaxWidth(Double.MAX_VALUE);

        options.getChildren().addAll(cancelButton, submitButton);
        addChildren(plateField, occupancyField, yearField, colorField, new Separator(), brandField, modelField, options);
    }

    private void handleSubmitButton() {
        clearErrors();

        boolean hasError = false;

        if(plateField.getText().isBlank()) {
            plateField.setError("License plate is required");
            hasError = true;
        }

        if(occupancyField.getText().isBlank()) {
            occupancyField.setError("Occupancy is required");
            hasError = true;
        }

        if(yearField.getText().isBlank()) {
            yearField.setError("Year is required");
            hasError = true;
        }

        if  (hasError) return;

        try {
            //SessionBLL.register(nameField.getText().trim(), emailField.getText().trim(), "", passwordField.getText().trim());
            listener.onSuccess();
            clearFields();
            clearErrors();
            hide();
        } catch(Exception e) {
            //emailField.setError(e.getMessage());
            listener.onFail();
        }
    }

    private void clearFields() {
        plateField.getInput().clear();
        occupancyField.getInput().clear();
        yearField.getInput().clear();
    }

    private void clearErrors() {
        plateField.clearError();
        occupancyField.clearError();
        yearField.clearError();
    }

    private void refreshBrands() {
        brandField.setItems(BrandBLL.index().stream()
                .map(brand -> new ComboItem(StringUtils.capitalize(brand.getName()), () -> {
                    selectedBrand = brand;
                    refreshModels();
                }))
                .collect(Collectors.toList()));
    }

    private void refreshModels() {
        if(selectedBrand == null) {
            modelField.setVisible(false);
            return;
        }

        modelField.setItems(ModelBLL.getByBrand(selectedBrand).stream()
                .map(model -> new ComboItem(StringUtils.capitalize(model.getName()), () -> {
                    selectedModel = model;
                }))
                .collect(Collectors.toList()));
        modelField.setVisible(true);
        modelField.getSelectionModel().select(0);

        if(selectedBrand != null) {
            List<Model> models = ModelBLL.getByBrand(selectedBrand);
            if(models.size() > 0) selectedModel = models.get(0);
        }
    }

    @Override
    public void update() {
        List<Brand> brands = BrandBLL.index();
        if(brands.size() > 0) selectedBrand = brands.get(0);

        List<Model> models = ModelBLL.getByBrand(selectedBrand);
        if(models.size() > 0) selectedModel = models.get(0);

        refreshBrands();
        refreshModels();

        if(brands.size() > 0) brandField.getSelectionModel().select(0);
        if(models.size() > 0) modelField.getSelectionModel().select(0);

    }
}
