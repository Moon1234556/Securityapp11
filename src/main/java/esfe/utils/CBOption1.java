package esfe.utils;

public class CBOption1 {
    private String displayText;
    private Object value;

    public CBOption1(String displayText, Object value) {
        this.displayText = displayText;
        this.value = value;
    }

    public String getDisplayText() {
        return displayText;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return displayText; // Esto es lo que se mostrará en el JComboBox
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final CBOption1 other = (CBOption1) obj;

        if (this.value == null) {
            return other.value == null;
        }

        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}