package woowacourse.omok.model.console

enum class BlockStateConsoleModel(val symbol: String = "") {
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
