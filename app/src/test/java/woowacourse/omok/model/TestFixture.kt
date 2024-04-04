package woowacourse.omok.model

val black = Color.BLACK
val white = Color.WHITE

val COORDINATE_A1 = Coordinate(1, 1)
val COORDINATE_C8 = Coordinate(8, 3)
val COORDINATE_D8 = Coordinate(8, 4)
val COORDINATE_E8 = Coordinate(8, 5)
val COORDINATE_F4 = Coordinate(4, 6)
val COORDINATE_F5 = Coordinate(5, 6)
val COORDINATE_F6 = Coordinate(6, 6)
val COORDINATE_F7 = Coordinate(7, 6)
val COORDINATE_F8 = Coordinate(8, 6)
val COORDINATE_F9 = Coordinate(9, 6)
val COORDINATE_F10 = Coordinate(10, 6)
val COORDINATE_F11 = Coordinate(11, 6)
val COORDINATE_F12 = Coordinate(12, 6)
val COORDINATE_F13 = Coordinate(13, 6)

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
