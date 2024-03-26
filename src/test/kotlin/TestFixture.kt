import omok.model.position.Column
import omok.model.position.Position
import omok.model.position.Row
import omok.model.stone.BlackStone
import omok.model.stone.WhiteStone

// x축
val X_A = Row('A')
val X_B = Row('B')
val X_C = Row('C')
val X_D = Row('D')
val X_E = Row('E')
val X_F = Row('F')
val X_G = Row('G')
val X_H = Row('H')
val X_I = Row('I')
val X_J = Row('J')
val X_K = Row('K')
val X_L = Row('L')
val X_M = Row('M')
val X_N = Row('N')
val X_O = Row('O')

// y축
val Y_1 = Column.from(1)
val Y_2 = Column.from(2)
val Y_3 = Column.from(3)
val Y_4 = Column.from(4)
val Y_5 = Column.from(5)
val Y_6 = Column.from(6)
val Y_7 = Column.from(7)
val Y_8 = Column.from(8)
val Y_9 = Column.from(9)
val Y_10 = Column.from(10)
val Y_11 = Column.from(11)
val Y_12 = Column.from(12)
val Y_13 = Column.from(13)
val Y_14 = Column.from(14)
val Y_15 = Column.from(15)

// 3-3 금수인 상황
val doubleThreeForbiddenCaseOne =
    listOf(
        Position(X_C, Y_12),
        Position(X_E, Y_12),
        Position(X_D, Y_13),
        Position(X_D, Y_14),
    )

val doubleThreeForbiddenCaseTwo =
    listOf(
        Position(X_B, Y_6),
        Position(X_C, Y_5),
        Position(X_E, Y_5),
        Position(X_E, Y_6),
    )

val doubleThreeForbiddenCaseThree =
    listOf(
        Position(X_J, Y_9),
        Position(X_N, Y_9),
        Position(X_M, Y_10),
        Position(X_M, Y_12),
    )

val doubleThreeForbiddenCaseFour =
    listOf(
        Position(X_K, Y_3),
        Position(X_K, Y_6),
        Position(X_M, Y_4),
        Position(X_N, Y_4),
    )

// 3-3 금수가 아닌 상황
val doubleThreeNotForbidden =
    listOf(
        Position(X_C, Y_14),
        Position(X_C, Y_13),
        Position(X_B, Y_12),
        Position(X_A, Y_12),
    )

fun createDoubleThree(positions: List<Position>) {
    val blackStone = BlackStone()

    positions.forEach {
        blackStone.putStone(it)
    }
}

fun createDoubleFour() {
    val blackStone = BlackStone()
    val whiteStone = WhiteStone()

    val blackStonePositions =
        listOf(
            Position(X_C, Y_10),
            Position(X_C, Y_11),
            Position(X_C, Y_12),
            Position(X_C, Y_14),
            Position(X_C, Y_15),
            Position(X_D, Y_12),
            Position(X_G, Y_12),
            Position(X_I, Y_12),
            Position(X_J, Y_12),
            Position(X_E, Y_5),
            Position(X_E, Y_6),
            Position(X_F, Y_5),
            Position(X_G, Y_5),
            Position(X_G, Y_4),
            Position(X_H, Y_6),
            Position(X_H, Y_7),
            Position(X_H, Y_8),
            Position(X_J, Y_8),
            Position(X_K, Y_8),
            Position(X_J, Y_9),
            Position(X_J, Y_6),
        )
    val whiteStonePositions =
        listOf(
            Position(X_D, Y_5),
            Position(X_H, Y_9),
        )

    blackStonePositions.forEach {
        blackStone.putStone(it)
    }
    whiteStonePositions.forEach {
        whiteStone.putStone(it)
    }
}
