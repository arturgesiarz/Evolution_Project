package oop.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class WorldElementBox {
    private static int IMAGE_WIDTH = 50;
    private static int IMAGE_HEIGHT = 50;
    private final Image image;
    private Label positionLabel;
    private VBox container;

    public WorldElementBox(WorldElement worldElement) {
        this.image = new Image(worldElement.getFileName());  // zaciaganie obrazka

        ImageView imageView = setImageView();  // tworzenie widoku
        initializationPositionLabel(worldElement);  // tworzenie positionLabel

        this.container = initializationContainer(imageView);  // dodanie calosci do jednego kontenera
    }

    private ImageView setImageView() {
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(IMAGE_WIDTH);
        imageView.setFitHeight(IMAGE_HEIGHT);

        return imageView;
    }

    private void initializationPositionLabel(WorldElement worldElement) {
        this.positionLabel = new Label(worldElement.getDescription());
    }

    private VBox initializationContainer(ImageView imageView) {
        this.container = new VBox();
        this.container.getChildren().addAll(imageView, this.positionLabel);
        this.container.setAlignment(Pos.CENTER);

        return container;
    }

    public VBox getContainer(){
        return container;
    }
}
