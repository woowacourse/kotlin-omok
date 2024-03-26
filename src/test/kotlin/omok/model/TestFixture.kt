package omok.model

val A1 = Coordinate(0, 0)
val A2 = Coordinate(0, 1)
val A3 = Coordinate(0, 2)
val A4 = Coordinate(0, 3)
val A5 = Coordinate(0, 4)
val B1 = Coordinate(1, 0)
val B2 = Coordinate(1, 1)
val B3 = Coordinate(1, 2)
val B4 = Coordinate(1, 3)
val B5 = Coordinate(1, 4)

// 3-3
val C12 = Coordinate(2, 11)
val E12 = Coordinate(4, 11)
val D13 = Coordinate(3, 12)
val D14 = Coordinate(3, 13)
val D12 = Coordinate(3, 11)

val M12 = Coordinate(12, 11)
val M10 = Coordinate(12, 9)
val N9 = Coordinate(13, 8)
val J9 = Coordinate(9, 8)

val K3 = Coordinate(10, 2)
val K6 = Coordinate(10, 5)
val M4 = Coordinate(12, 3)
val N4 = Coordinate(13, 3)

val B6 = Coordinate(1, 5)
val C5 = Coordinate(2, 4)
val E5 = Coordinate(4, 4)
val E6 = Coordinate(4, 5)

val G12 = Coordinate(6, 11)
val I12 = Coordinate(8, 11)
val J12 = Coordinate(9, 11)

val C10 = Coordinate(2, 9)
val C11 = Coordinate(2, 10)
val F5 = Coordinate(5, 4)
val G4 = Coordinate(6, 3)

val J6 = Coordinate(9, 5)
val J8 = Coordinate(9, 7)

val BLACK_STONE = PositionType.BLACK_STONE
val WHITE_STONE = PositionType.WHITE_STONE

val OMOK_FINISH = listOf(A1, B1, A2, B2, A3, B3, A4, B4, A5)

val BLACK_STATE = GameState.Running.BlackTurn(Board())
val WHITE_STATE = GameState.Running.WhiteTurn(Board())
