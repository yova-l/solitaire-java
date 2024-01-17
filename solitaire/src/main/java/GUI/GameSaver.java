package GUI;

import SolitarioBase.Estado;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameSaver {
    private Estado gameState;
    private static final String savingPath = (Path.of(System.getProperty("user.dir"), "/solitaireSaved.bin")).toString();

    public void setEstado(Estado newState) { gameState = newState; }

    public Estado getEstado() { return gameState; }

    public void saveEstado() throws IOException {
        FileOutputStream fos = new FileOutputStream(savingPath);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        gameState.serializar(bos);
    }

    public void loadEstado() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(savingPath);
        BufferedInputStream bis = new BufferedInputStream(fis);
        gameState = Estado.deserializar(bis);
    }

    public void deleteGame() throws IOException {
        Files.delete(Paths.get(savingPath));
    }

    public boolean isSaved() {
        File f = new File(savingPath);
        return (f.exists() && !f.isDirectory());
    }
}

