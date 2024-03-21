package omok.model

interface Rule {
    fun checkThreeThree(stone: Stone): Boolean

    fun checkFourFour(stone: Stone): Boolean

    fun checkMoreThanFive(stone: Stone): Boolean

    fun checkInvalid(stone: Stone): Boolean
}
