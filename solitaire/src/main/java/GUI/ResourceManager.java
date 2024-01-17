package GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourceManager {
    private String getStylePathP(String styleSheetName) {
        var absoluteStyleResourcePath = "/styles/";
        var fullPath = String.format("%s%s%s",absoluteStyleResourcePath, styleSheetName, ".css");
        var styleSheetUrl = getClass().getResource(fullPath);
        assert styleSheetUrl != null;
        return styleSheetUrl.toString();
    }

    public static String getStylePath(String styleSheetName) {
        return new ResourceManager().getStylePathP(styleSheetName);
    }

    public ImageView getGenericImage(String filename) {
        var absoluteImgsResourcePath = "/img/";
        var cardName = String.format("%s%s",filename, ".png");
        var fullPath = absoluteImgsResourcePath.concat(cardName);

        var imageUrl = getClass().getResource(fullPath);
        var image = new Image(imageUrl.toString());
        var imgNode = new ImageView();
        imgNode.setImage(image);
        return imgNode;
    }
}
