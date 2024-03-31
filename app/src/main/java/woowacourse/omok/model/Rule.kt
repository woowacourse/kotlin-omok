package woowacourse.omok.model

interface Rule {
    fun checkPlaceable(stones: Stones, stone: Stone): Boolean
}
