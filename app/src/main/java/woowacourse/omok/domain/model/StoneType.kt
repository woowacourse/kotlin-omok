package woowacourse.omok.domain.model

enum class StoneType {
    BLACK,
    WHITE,
    EMPTY, ;

    companion object {
        fun getStoneTypeByIndex(index: Int): StoneType {
            return if (index % 2 == 0) BLACK else WHITE
        }
    }
}
