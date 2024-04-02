package woowacourse.omok.domain.model

enum class StoneType {
    BLACK,
    WHITE,
    EMPTY, ;

    companion object {
        fun getStoneTypeByName(name: String): StoneType {
            return if (name == BLACK.name) {
                BLACK
            } else if (name == WHITE.name) {
                WHITE
            } else {
                EMPTY
            }
        }
    }
}
