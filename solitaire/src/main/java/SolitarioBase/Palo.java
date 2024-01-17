package SolitarioBase;

public enum Palo {
    CORAZON (Color.ROJO),
    PICA (Color.NEGRO),
    TREBOL (Color.NEGRO),
    DIAMANTE (Color.ROJO);

    private final Color color;

    Palo(Color color) { this.color = color; }

    public Color getColor() { return color; }
}
