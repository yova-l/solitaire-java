package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardView {
    public ImageView fabricarCard(String descripcion) {
        var elementosCarta = descripcion.split("-");
        var estaOculta = Boolean.parseBoolean(elementosCarta[2]);
        var absoluteImgsResourcePath = "/img/cards/";
        var cardName = estaOculta? "card-back2.png" : String.format("%s%s",descripcion, ".png");
        var fullPath = absoluteImgsResourcePath.concat(cardName);

        var imageUrl = getClass().getResource(fullPath);
        var image = new Image(imageUrl.toString());
        var cardNode = new ImageView();
        cardNode.setImage(image);
        cardNode.getStyleClass().add("carta");
        cardNode.setId(descripcion);
        return cardNode;
    }
}
