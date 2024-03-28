package woowacourse.omokApp.omok.model.board

enum class ColumnNumber(val coordsNumber: CoordsNumber) {
    A(CoordsNumber(0)),
    B(CoordsNumber(1)),
    C(CoordsNumber(2)),
    D(CoordsNumber(3)),
    E(CoordsNumber(4)),
    F(CoordsNumber(5)),
    G(CoordsNumber(6)),
    H(CoordsNumber(7)),
    I(CoordsNumber(8)),
    J(CoordsNumber(9)),
    K(CoordsNumber(10)),
    L(CoordsNumber(11)),
    M(CoordsNumber(12)),
    N(CoordsNumber(13)),
    O(CoordsNumber(14)),
    ;

    companion object {
        fun fromLetter(letter: Char): CoordsNumber? = entries.firstOrNull { it.name == letter.toString() }?.coordsNumber
    }
}
