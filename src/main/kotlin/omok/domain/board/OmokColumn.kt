package omok.domain.board

import omok.domain.board.OmokRow.WALL

enum class OmokColumn(val value: Int) {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9),
    J(10),
    K(11),
    L(12),
    M(13),
    N(14),
    O(15),
    WALL(-1),
    ;

    companion object {
        fun entriesWithoutWall(): List<OmokColumn> = OmokColumn.entries.filter { it != WALL }.toList()

        fun find(value: Int): OmokColumn =
            OmokColumn.entries.find { it.value == value }
                ?: WALL
    }
}
