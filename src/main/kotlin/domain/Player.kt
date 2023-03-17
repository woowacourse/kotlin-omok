package domain

abstract class Player {
    abstract val color: Color
    abstract fun validateOmokRule(stones: Stones, stone: Stone): Boolean
}
