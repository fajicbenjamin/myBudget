package Model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author benjaminfajic
 */
public class Input {
    public final SimpleStringProperty timestamp;
    public final SimpleStringProperty description;
    public final SimpleStringProperty category;
    public final SimpleStringProperty type;
    public final SimpleStringProperty amount;

    public Input(String timestamp, String description, String category, String type, String amount) {
        this.timestamp = new SimpleStringProperty(timestamp);
        this.description = new SimpleStringProperty(description);
        this.category = new SimpleStringProperty(category);
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleStringProperty(amount);
    }

    public String getTimestamp() {
        return timestamp.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getCategory() {
        return category.get();
    }

    public String getType() {
        return type.get();
    }

    public String getAmount() {
        return amount.get();
    }        


}
