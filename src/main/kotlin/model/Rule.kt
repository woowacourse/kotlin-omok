package model

interface Rule {
    fun checkFoulByAllDirections(
        stone: Stone,
        stones: List<Stone>,
    ): Boolean

    fun checkFoul(
        stone: Stone,
        stones: List<Stone>,
        startPosition: Position,
        lastPosition: Position,
        direction: Direction,
    ): List<Stone>?
}
