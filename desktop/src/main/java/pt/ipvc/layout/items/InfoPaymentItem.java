package pt.ipvc.layout.items;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.VBox;
import pt.ipvc.components.SmallLabel;
import pt.ipvc.components.Text;
import pt.ipvc.components.card.Card;
import pt.ipvc.dal.Payment;

public class InfoPaymentItem extends Card {

    public InfoPaymentItem(Payment data) {
        setSpacing(12);

        if(data == null) {
            getChildren().add(new Text("No payment found"));
            return;
        }

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col1.setPercentWidth(50);

        // ------
        SmallLabel methodLabel = new SmallLabel("Method");
        Text methodText = new Text(data.getPaymentType().getName());

        VBox methodBox = new VBox(0);
        methodBox.getChildren().addAll(methodLabel, methodText);
        // ------

        SmallLabel amountLabel = new SmallLabel("Amount");
        Text amountText = new Text(data.getAmount().toString() + " €");

        VBox amountBox = new VBox(0);
        amountBox.getChildren().addAll(amountLabel, amountText);
        // ------

        SmallLabel vatLabel = new SmallLabel("VAT");
        Text vatText = new Text("" + data.getVat());

        VBox vatBox = new VBox(0);
        vatBox.getChildren().addAll(vatLabel, vatText);


        getChildren().addAll(methodBox, amountBox, vatBox);
    }
}
