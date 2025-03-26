package woowacourse.omok.domain

enum class StoneColor {
    BLACK,
    WHITE,
    ;

    companion object {
        fun opposite(stoneColor: StoneColor): StoneColor {
            return when (stoneColor) {
                BLACK -> WHITE
                WHITE -> BLACK
            }
        }
    }
}
