package pt.ipvc.layout.brands;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.ipvc.bll.ModelBLL;
import pt.ipvc.components.Text;
import pt.ipvc.components.buttons.Button;
import pt.ipvc.components.buttons.ButtonSize;
import pt.ipvc.components.card.Card;
import pt.ipvc.components.card.CardTitle;
import pt.ipvc.dal.Brand;
import pt.ipvc.dal.Model;

import java.util.List;

public class BrandItem extends Card {

    public BrandItem(Brand data) {
        CardTitle title = new CardTitle(data.getName());
        VBox modelsContainer = new VBox(8);
        modelsContainer.setPadding(new Insets(8,0,8,8));

        List<Model> models = ModelBLL.getByBrand(data);
        models.forEach(model -> modelsContainer.getChildren().add(new ModelItem(model)));

        HBox options = new HBox(8);
        Button addModelButton = new Button("Add Model", ButtonSize.SMALL);

        options.getChildren().addAll(addModelButton);
        getChildren().addAll(title, modelsContainer, options);
    }
}
