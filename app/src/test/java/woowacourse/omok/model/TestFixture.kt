package omok.model

val black = Color.BLACK
val white = Color.WHITE

val COORDINATE_A1 = Coordinate(PositionX(1), PositionY(1))
val COORDINATE_C8 = Coordinate(PositionX(8), PositionY(3))
val COORDINATE_D8 = Coordinate(PositionX(8), PositionY(4))
val COORDINATE_E8 = Coordinate(PositionX(8), PositionY(5))
val COORDINATE_F4 = Coordinate(PositionX(4), PositionY(6))
val COORDINATE_F5 = Coordinate(PositionX(5), PositionY(6))
val COORDINATE_F6 = Coordinate(PositionX(6), PositionY(6))
val COORDINATE_F7 = Coordinate(PositionX(7), PositionY(6))
val COORDINATE_F8 = Coordinate(PositionX(8), PositionY(6))
val COORDINATE_F9 = Coordinate(PositionX(9), PositionY(6))
val COORDINATE_F10 = Coordinate(PositionX(10), PositionY(6))
val COORDINATE_F11 = Coordinate(PositionX(11), PositionY(6))
val COORDINATE_F12 = Coordinate(PositionX(12), PositionY(6))
val COORDINATE_F13 = Coordinate(PositionX(13), PositionY(6))

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
