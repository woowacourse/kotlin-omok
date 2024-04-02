package omok.model.stone

enum class StoneType(val type: String) {
    BLACK_STONE("흑"),
    WHITE_STONE("백"),
    NONE("없음"),
    ;

    companion object {
        fun from(type: String): GoStone {
            return when (type) {
                BLACK_STONE.type -> BlackStone()
                WHITE_STONE.type -> WhiteStone()
                else -> BlackStone()
            }
        }
    }
}
