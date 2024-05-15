package Assignment;

import javafx.scene.control.TableCell;
import javafx.scene.text.Text;

public class WrappingTableCell<S, T> extends TableCell<S, T> {

    private final Text text;

    public WrappingTableCell() {
        this.text = new Text();
        setGraphic(text);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            text.setText(null);
        } else {
            text.setText(item.toString());
        }
    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);
        if (getTableColumn() != null) {
            text.wrappingWidthProperty().bind(getTableColumn().widthProperty());
        }
    }
}
