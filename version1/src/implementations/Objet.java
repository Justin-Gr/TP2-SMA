package implementations;

public enum Objet {
    A("A"),
    B("B");

    private final String label;

    Objet(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
