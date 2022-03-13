package implementations;

public enum Objet {
    A("A", false),
    B("B", false),
    C("C", true);

    private final String label;
    private final boolean lourd;

    Objet(String label, boolean lourd) {
        this.label = label;
        this.lourd = lourd;
    }

    public boolean isLourd() {
        return lourd;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
