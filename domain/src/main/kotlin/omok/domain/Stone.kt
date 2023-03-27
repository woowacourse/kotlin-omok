package omok.domain

interface Stone {
    fun findPosition(value: Position): Boolean

    fun getLocation(): Position
}
