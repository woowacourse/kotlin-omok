package woowacourse.omok.domain.model.stone

enum class StoneType {
    BLACK,
    WHITE,
    ;

    fun reverse() =
        when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }
}
