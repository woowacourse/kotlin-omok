package woowacourse.omok.domain.model

enum class StoneType {
    BLACK,
    WHITE,
    EMPTY, ;

    companion object {
        fun getStoneTypeByName(name: String): StoneType {
            return entries.first { stoneType -> stoneType.name == name }
        }
    }
}
