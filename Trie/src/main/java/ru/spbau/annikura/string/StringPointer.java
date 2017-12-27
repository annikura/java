package ru.spbau.annikura.string;

/**
 * Iterator-like string viewer.
 */
public class StringPointer {
    private final String str;
    private int pointer = 0;

    public StringPointer(final String str) {
        this.str = str;
    }

    /**
     * Moves pointer in the string to the next symbol.
     * @return self
     * @throws StringIndexOutOfBoundsException if the pointer was already
     * pointing at the end of the string before call.
     */
    public StringPointer next() throws StringIndexOutOfBoundsException {
        if (++pointer > str.length()) {
            throw new StringIndexOutOfBoundsException(
                    "Pointer has reached the end of the string");
        }
        return this;
    }

    /**
     * Current symbol getter.
     * @return symbol on the current position.
     */
    public char symbol() {
        return str.charAt(pointer);
    }

    /**
     * Checks if call of the "symbol" method is still possible.
     * @return true if pointer is now on the end of the string. False if it still points at some symbol.
     */
    public boolean pointsAtEnd() {
        return pointer == str.length();
    }
}
