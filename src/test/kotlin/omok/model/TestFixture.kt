package omok.model

val black = Color.BLACK
val white = Color.WHITE

val samsamStones =
    listOf(
        Stone(black, Coordinate(Row.from("8"), Column.from("E"))),
        Stone(black, Coordinate(Row.from("8"), Column.from("G"))),
        Stone(black, Coordinate(Row.from("9"), Column.from("F"))),
        Stone(black, Coordinate(Row.from("10"), Column.from("F"))),
        Stone(white, Coordinate(Row.from("1"), Column.from("A"))),
    )

val fourfourStones =
    listOf(
        Stone(black, Coordinate(Row.from("8"), Column.from("C"))),
        Stone(black, Coordinate(Row.from("8"), Column.from("D"))),
        Stone(black, Coordinate(Row.from("8"), Column.from("E"))),
        Stone(black, Coordinate(Row.from("11"), Column.from("F"))),
        Stone(black, Coordinate(Row.from("10"), Column.from("F"))),
        Stone(black, Coordinate(Row.from("9"), Column.from("F"))),
        Stone(white, Coordinate(Row.from("1"), Column.from("A"))),
    )

val moreThanFiveStones =
    listOf(
        Stone(black, Coordinate(Row.from("5"), Column.from("F"))),
        Stone(black, Coordinate(Row.from("6"), Column.from("F"))),
        Stone(black, Coordinate(Row.from("7"), Column.from("F"))),
        Stone(black, Coordinate(Row.from("9"), Column.from("F"))),
        Stone(black, Coordinate(Row.from("10"), Column.from("F"))),
        Stone(white, Coordinate(Row.from("1"), Column.from("A"))),
    )

val openFourFourStones =
    listOf(
        Stone(black, Coordinate(Row.from("8"), Column.from("C"))),
        Stone(black, Coordinate(Row.from("8"), Column.from("D"))),
        Stone(black, Coordinate(Row.from("8"), Column.from("G"))),
        Stone(black, Coordinate(Row.from("8"), Column.from("I"))),
        Stone(black, Coordinate(Row.from("8"), Column.from("J"))),
        Stone(white, Coordinate(Row.from("1"), Column.from("A"))),
    )
