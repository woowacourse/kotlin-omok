package woowacourse.omok.domain.model

enum class StoneType {
    BLACK,
    WHITE,
    EMPTY, ;

    companion object {
        fun getStoneTypeByName(name: String): StoneType {
            return when (name) {
                BLACK.name -> BLACK
                WHITE.name -> WHITE
                else -> EMPTY
            }
        }
    }
}
