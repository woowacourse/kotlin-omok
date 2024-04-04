package woowacourse.omok.model

interface Rule {
    fun checkPlaceable(stone: Stone): Boolean
}
