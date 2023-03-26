package woowacourse.omok.data.database.entity

enum class StoneColor(val id: Int) {
    BLACK(0),
    WHITE(1);

    companion object {
        fun get(id: Int): StoneColor =
            StoneColor.values().find { it.id == id }
                ?: throw IllegalArgumentException("올바르지 않은 식별자입니다.")
    }
}
