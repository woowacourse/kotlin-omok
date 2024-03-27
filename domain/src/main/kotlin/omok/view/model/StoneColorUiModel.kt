package omok.view.model

enum class StoneColorUiModel(val symbol: String = "") {
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
