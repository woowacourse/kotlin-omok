package omok.model

interface Rule {
    fun checkPlaceable(stone: Stone): Boolean
}
