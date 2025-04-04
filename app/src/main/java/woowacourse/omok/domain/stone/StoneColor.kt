package woowacourse.omok.domain.stone

enum class StoneColor {
    BLACK,
    WHITE,
    ;

    fun toggle(): StoneColor = if (this == BLACK) WHITE else BLACK

    companion object {
        fun fromString(value: String?): StoneColor {
            return entries.find { it.name == value } ?: throw IllegalArgumentException()
        }
    }
}
