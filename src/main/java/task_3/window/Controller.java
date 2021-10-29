package task_3.window;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import task_3.figures.Polygon;
import task_3.graphics.drawers.PolygonDrawer;
import task_3.graphics.points.Point;


public class Controller {
    private final PixelPane pixelPane = new PixelPane();
    
    @FXML
    private HBox mainPane;
    
    @FXML
    public void initialize() {
        mainPane.getChildren().add(pixelPane);
        init();
    }
    
    private void init() {
        pixelPane.addDrawer(
            new PolygonDrawer(new Polygon(
                300.0, 5, Math.PI / 2, new Point(0, 0)
            ))
        );
    }
}
