package woowacourse.omok.model

import woowacourse.omok.model.state.CoordinateState
import woowacourse.omok.model.state.GameState
import woowacourse.omok.model.state.Turn

fun String.toCoordinate(): Coordinate {
    val x = this.first().uppercaseChar() - 'A'
    val y = this.drop(1).toIntOrNull()?.minus(1) ?: throw IllegalArgumentException()
    return Coordinate(x, y)
}

fun newBoardLayout(): MutableList<MutableList<CoordinateState>> =
    MutableList(Board.BOARD_SIZE) {
        MutableList(Board.BOARD_SIZE) { CoordinateState.Empty }
    }

val A1 = "A1".toCoordinate()
val A2 = "A2".toCoordinate()
val A3 = "A3".toCoordinate()
val A4 = "A4".toCoordinate()
val A5 = "A5".toCoordinate()
val B1 = "B1".toCoordinate()
val B2 = "B2".toCoordinate()
val B3 = "B3".toCoordinate()
val B4 = "B4".toCoordinate()
val B5 = "B5".toCoordinate()

// 3-3
val C12 = "C12".toCoordinate()
val E12 = "E12".toCoordinate()
val D13 = "D13".toCoordinate()
val D14 = "D14".toCoordinate()
val D12 = "D12".toCoordinate()

val M12 = "M12".toCoordinate()
val M10 = "M10".toCoordinate()
val N9 = "N9".toCoordinate()
val J9 = "J9".toCoordinate()

val K3 = "K3".toCoordinate()
val K6 = "K6".toCoordinate()
val M4 = "M4".toCoordinate()
val N4 = "N4".toCoordinate()

val B6 = "B6".toCoordinate()
val C5 = "C5".toCoordinate()
val E5 = "E5".toCoordinate()
val E6 = "E6".toCoordinate()

val G12 = "G12".toCoordinate()
val I12 = "I12".toCoordinate()
val J12 = "J12".toCoordinate()

val C10 = "C10".toCoordinate()
val C11 = "C11".toCoordinate()
val F5 = "F5".toCoordinate()
val G4 = "G4".toCoordinate()

val J6 = "J6".toCoordinate()
val J8 = "J8".toCoordinate()

val BLACK_STONE = CoordinateState.Placed(turn = Turn.Black)
val WHITE_STONE = CoordinateState.Placed(turn = Turn.White)

val OMOK_FINISH = listOf(A1, B1, A2, B2, A3, B3, A4, B4, A5)

val BLACK_STATE = GameState.Playing.Start(Board(), turn = Turn.Black)
val WHITE_STATE = GameState.Playing.Start(Board(), turn = Turn.White)
