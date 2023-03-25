package domain

fun BlackStone(x: Int, y: Int): Stone {
    return Stone(Color.BLACK, Coordinate.from(x, y))
}

fun WhiteStone(x: Int, y: Int): Stone {
    return Stone(Color.WHITE, Coordinate.from(x, y))
}
