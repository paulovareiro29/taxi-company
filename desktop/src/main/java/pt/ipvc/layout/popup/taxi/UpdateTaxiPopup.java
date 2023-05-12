package pt.ipvc.layout.popup.taxi;

import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import pt.ipvc.base.ComboItem;
import pt.ipvc.base.EventListener;
import pt.ipvc.base.Popup;
import pt.ipvc.bll.BrandBLL;
import pt.ipvc.bll.ModelBLL;
import pt.ipvc.bll.TaxiBLL;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonAppearance;
import pt.ipvc.components.inputs.AutoCompleteComboBox;
import pt.ipvc.components.inputs.ComboBox;
import pt.ipvc.components.inputs.NumericField;
import pt.ipvc.components.inputs.TextField;
import pt.ipvc.dal.Brand;
import pt.ipvc.dal.Model;
import pt.ipvc.dal.Taxi;
import pt.ipvc.handlers.SceneHandler;
import pt.ipvc.handlers.ScreensEnum;
import pt.ipvc.layout.popup.DangerConfirmationPopup;
import pt.ipvc.utils.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateTaxiPopup extends Popup {

    private Taxi taxi;
    private Brand selectedBrand;
    private Model selectedModel;

    private final TextField plateField;
    private final TextField occupancyField;
    private final TextField yearField;
    private final TextField colorField;
    private final AutoCompleteComboBox brandField;
    private final ComboBox modelField;

    public UpdateTaxiPopup(EventListener listener) {
        super("Update Taxi", listener);

        plateField = new TextField();
        plateField.setPromptText("License Plate");
        plateField.setIcon("car--secondary.png");

        occupancyField = new NumericField();
        occupancyField.setPromptText("Occupancy");
        occupancyField.setIcon("double-users--secondary.png");

        yearField = new NumericField();
        yearField.setPromptText("Year");
        yearField.setIcon("calendar--secondary.png");

        colorField = new TextField();
        colorField.setPromptText("Color");
        colorField.setIcon("brush--secondary.png");

        brandField = new AutoCompleteComboBox(Collections.emptyList());
        brandField.setPrefWidth(Double.MAX_VALUE);
        brandField.setPromptText("Select brand");

        modelField = new ComboBox(Collections.emptyList());
        modelField.setPrefWidth(Double.MAX_VALUE);
        modelField.setPromptText("Select model");
        modelField.setVisible(false);

        Button cancelButton = new Button("Cancel", ButtonAppearance.outlined_primary);
        Button submitButton = new Button("Update");

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

        Button deleteButton = new Button("DELETE", ButtonAppearance.outlined_danger);
        deleteButton.setPrefWidth(Double.MAX_VALUE);
        deleteButton.setOnAction(e -> {
            DangerConfirmationPopup popup = new DangerConfirmationPopup(new EventListener() {
                @Override
                public void onSuccess() {
                    TaxiBLL.remove(taxi.getId());
                    hide();
                    listener.onSuccess();
                    SceneHandler.updateScreen(ScreensEnum.VEHICLES);
                }

                @Override
                public void onFail() {

                }

                @Override
                public void onCancel() {
                    System.out.println("Cancleed");
                }
            });
            popup.show(SceneHandler.stage);
        });

        options.getChildren().addAll(cancelButton, submitButton);
        addChildren(plateField, occupancyField, yearField, colorField, new Separator(), brandField, modelField, options, deleteButton);
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

        if(colorField.getText().isBlank()) {
            colorField.setError("Color is required");
            hasError = true;
        }

        if  (hasError) return;

        taxi.setLicensePlate(plateField.getText());
        taxi.setMaxOccupancy(Integer.parseInt(occupancyField.getText()));
        taxi.setYear(Integer.parseInt(yearField.getText()));
        taxi.setColor(colorField.getText());
        taxi.setModel(selectedModel);

        try {
            TaxiBLL.update(taxi);
            listener.onSuccess();
            clearFields();
            clearErrors();
            hide();
        } catch(Exception e) {
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

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
        selectedBrand = taxi.getModel().getBrand();
        selectedModel = taxi.getModel();
        this.update();
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


        plateField.setText(taxi.getLicensePlate());
        occupancyField.setText("" + taxi.getMaxOccupancy());
        yearField.setText("" + taxi.getYear());
        colorField.setText(taxi.getColor());
    }
}
