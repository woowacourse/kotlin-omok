package woowacourse.omok.model

val black = Color.BLACK
val white = Color.WHITE

val COORDINATE_A1 = Coordinate(Row(1), Column(1))
val COORDINATE_B2 = Coordinate(Row(2), Column(2))
val COORDINATE_B8 = Coordinate(Row(8), Column(2))
val COORDINATE_C3 = Coordinate(Row(3), Column(3))
val COORDINATE_C7 = Coordinate(Row(7), Column(3))
val COORDINATE_C8 = Coordinate(Row(8), Column(3))
val COORDINATE_D4 = Coordinate(Row(4), Column(4))
val COORDINATE_D6 = Coordinate(Row(6), Column(4))
val COORDINATE_D8 = Coordinate(Row(8), Column(4))
val COORDINATE_E5 = Coordinate(Row(5), Column(5))
val COORDINATE_E8 = Coordinate(Row(8), Column(5))
val COORDINATE_F4 = Coordinate(Row(4), Column(6))
val COORDINATE_F5 = Coordinate(Row(5), Column(6))
val COORDINATE_F6 = Coordinate(Row(6), Column(6))
val COORDINATE_F7 = Coordinate(Row(7), Column(6))
val COORDINATE_F8 = Coordinate(Row(8), Column(6))
val COORDINATE_F9 = Coordinate(Row(9), Column(6))
val COORDINATE_F10 = Coordinate(Row(10), Column(6))
val COORDINATE_F11 = Coordinate(Row(11), Column(6))

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
