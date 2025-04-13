package ai.code;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class State {

    private byte[] board;
    private byte zeroPosition;

    State(byte[] tab) {
        board = tab;
        for (byte i = 0; i < tab.length; i++) {
            if (tab[i] == 0) {
                zeroPosition = i;
            }
        }
    }

    public byte getNumber(byte pos) {
        return board[pos];
    }

    public byte[] getBoard() {
        byte[] copy = new byte[board.length];
        System.arraycopy(board, 0, copy, 0, board.length);
        return copy;
    }

    public byte getZeroPosition() {
        return zeroPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return new EqualsBuilder().append(board, state.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }
}