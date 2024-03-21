package omok.model.board

enum class ColumnNumber(val coordsNumber: CoordsNumber) {
    A(CoordsNumber(1)),
    B(CoordsNumber(2)),
    C(CoordsNumber(3)),
    D(CoordsNumber(4)),
    E(CoordsNumber(5)),
    F(CoordsNumber(6)),
    G(CoordsNumber(7)),
    H(CoordsNumber(8)),
    I(CoordsNumber(9)),
    J(CoordsNumber(10)),
    K(CoordsNumber(11)),
    L(CoordsNumber(12)),
    M(CoordsNumber(13)),
    N(CoordsNumber(14)),
    O(CoordsNumber(15)),
    ;

    companion object {
        fun fromLetter(letter: Char): CoordsNumber? = entries.firstOrNull { it.name == letter.toString() }?.coordsNumber
    }
}
