package omok.model

val A1 = Coordinate.from("A1")
val A2 = Coordinate.from("A2")
val A3 = Coordinate.from("A3")
val A4 = Coordinate.from("A4")
val A5 = Coordinate.from("A5")
val B1 = Coordinate.from("B1")
val B2 = Coordinate.from("B2")
val B3 = Coordinate.from("B3")
val B4 = Coordinate.from("B4")
val B5 = Coordinate.from("B5")

// 3-3
val C12 = Coordinate.from("C12")
val E12 = Coordinate.from("E12")
val D13 = Coordinate.from("D13")
val D14 = Coordinate.from("D14")
val D12 = Coordinate.from("D12")

val M12 = Coordinate.from("M12")
val M10 = Coordinate.from("M10")
val N9 = Coordinate.from("N9")
val J9 = Coordinate.from("J9")

val K3 = Coordinate.from("K3")
val K6 = Coordinate.from("K6")
val M4 = Coordinate.from("M4")
val N4 = Coordinate.from("N4")

val B6 = Coordinate.from("B6")
val C5 = Coordinate.from("C5")
val E5 = Coordinate.from("E5")
val E6 = Coordinate.from("E6")

val G12 = Coordinate.from("G12")
val I12 = Coordinate.from("I12")
val J12 = Coordinate.from("J12")

val C10 = Coordinate.from("C10")
val C11 = Coordinate.from("C11")
val F5 = Coordinate.from("F5")
val G4 = Coordinate.from("G4")

val J6 = Coordinate.from("J6")
val J8 = Coordinate.from("J8")

val BLACK_STONE = PositionType.BLACK_STONE
val WHITE_STONE = PositionType.WHITE_STONE

val OMOK_FINISH = listOf(A1, B1, A2, B2, A3, B3, A4, B4, A5)

val BLACK_STATE = GameState.Running.BlackTurn(Board())
val WHITE_STATE = GameState.Running.WhiteTurn(Board())
