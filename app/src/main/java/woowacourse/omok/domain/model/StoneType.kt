package woowacourse.omok.domain.model

enum class StoneType(val value: Int) {
    BLACK(1),
    WHITE(2),
    EMPTY(0), ;

    companion object {
        fun fromValue(value: Int): StoneType {
            return entries.find { it.value == value } ?: EMPTY
        }
    }
}
