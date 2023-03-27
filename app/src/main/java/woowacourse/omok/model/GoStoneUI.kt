package woowacourse.omok.model

enum class GoStoneUI(val number: Int, val korean: String) {
    BLACK(0, "흑"),
    WHITE(1, "백"),
    ;

    companion object {
        fun aa(number: Int): GoStoneUI =
            values().first { it.number == number }
    }
}