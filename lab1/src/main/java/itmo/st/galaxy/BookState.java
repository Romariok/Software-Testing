package itmo.st.galaxy;

public enum BookState {
    ENTERTAINING("Entertaining"),
    EDUCATIONAL("Educational"),
    SCIENTIFIC("Scientific"),
    BORING("Boring"),
    EXTRORDINARY("Extrordinary"),
    UNKNOWN("Unknown");

    private final String string;

    BookState(String string) {
        this.string = string;
    }
    
    public String getString() {
        return this.string;
    }
}
