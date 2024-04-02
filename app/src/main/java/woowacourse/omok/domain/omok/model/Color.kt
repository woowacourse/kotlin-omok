package woowacourse.omok.domain.omok.model

enum class Color(val label: String) {
    BLACK("흑"),
    WHITE("백"),
    NONE("공백"),
    ;

    companion object {
        fun of(color: String): Color {
            return entries.first { it.label == color }
        }
    }
}
