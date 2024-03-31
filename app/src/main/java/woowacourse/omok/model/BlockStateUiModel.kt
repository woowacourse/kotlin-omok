package woowacourse.omok.model

enum class BlockStateUiModel(val symbol: String = "") {
    BLACK("●"),

    WHITE("○"),

    EMPTY(""),
    ;

    fun format() =
        when (this) {
            BLACK -> "흑"
            WHITE -> "백"
            EMPTY -> ""
        }
}
