package omok.model

val black = Color.BLACK
val white = Color.WHITE

val COORDINATE_A1 = Coordinate(Row.from("1"), Column.from("A"))
val COORDINATE_C8 = Coordinate(Row.from("8"), Column.from("C"))
val COORDINATE_D8 = Coordinate(Row.from("8"), Column.from("D"))
val COORDINATE_E8 = Coordinate(Row.from("8"), Column.from("E"))
val COORDINATE_F4 = Coordinate(Row.from("4"), Column.from("F"))
val COORDINATE_F5 = Coordinate(Row.from("5"), Column.from("F"))
val COORDINATE_F6 = Coordinate(Row.from("6"), Column.from("F"))
val COORDINATE_F7 = Coordinate(Row.from("7"), Column.from("F"))
val COORDINATE_F8 = Coordinate(Row.from("8"), Column.from("F"))
val COORDINATE_F9 = Coordinate(Row.from("9"), Column.from("F"))
val COORDINATE_F10 = Coordinate(Row.from("10"), Column.from("F"))
val COORDINATE_F11 = Coordinate(Row.from("11"), Column.from("F"))

val samSamBlackStones =
    listOf(
        Stone(black, COORDINATE_D8),
        Stone(black, COORDINATE_E8),
        Stone(black, COORDINATE_F9),
        Stone(black, COORDINATE_F10),
        Stone(white, COORDINATE_A1),
    )

val fourFourBlackStones =
    listOf(
        Stone(black, COORDINATE_C8),
        Stone(black, COORDINATE_D8),
        Stone(black, COORDINATE_E8),
        Stone(black, COORDINATE_F9),
        Stone(black, COORDINATE_F10),
        Stone(black, COORDINATE_F11),
        Stone(white, COORDINATE_A1),
    )

val moreThanFiveBlackStones =
    listOf(
        Stone(black, COORDINATE_F5),
        Stone(black, COORDINATE_F6),
        Stone(black, COORDINATE_F7),
        Stone(black, COORDINATE_F9),
        Stone(black, COORDINATE_F10),
        Stone(white, COORDINATE_A1),
    )

val openFourFourBlackStones =
    listOf(
        Stone(black, COORDINATE_F4),
        Stone(black, COORDINATE_F6),
        Stone(black, COORDINATE_F7),
        Stone(black, COORDINATE_F10),
        Stone(black, COORDINATE_F11),
        Stone(white, COORDINATE_A1),
    )

val samSamWhiteStones =
    listOf(
        Stone(white, COORDINATE_D8),
        Stone(white, COORDINATE_E8),
        Stone(white, COORDINATE_F9),
        Stone(white, COORDINATE_F10),
        Stone(black, COORDINATE_A1),
    )
