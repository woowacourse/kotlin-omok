package omok.model.stone

enum class StoneType(val type: String) {
    BLACK_STONE("흑"),
    WHITE_STONE("백"),
    NONE("없음"),
    ;

    companion object {
        fun from(type: String): StoneType {
            return when (type) {
                "흑" -> BLACK_STONE
                "백" -> WHITE_STONE
                "없음" -> NONE
                else -> NONE
            }
        }
    }
}
