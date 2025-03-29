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

    companion object {
        fun valueOf(name: String) =
            when (name) {
                BLACK.name -> BLACK
                WHITE.name -> WHITE
                else -> throw IllegalArgumentException()
            }
    }
}
