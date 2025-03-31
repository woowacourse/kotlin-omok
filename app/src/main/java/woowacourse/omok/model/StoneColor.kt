package model

enum class StoneColor {
    WHITE,
    BLACK,
    ;

    fun switch(): StoneColor =
        when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
        }

    override fun toString(): String =
        when (this) {
            WHITE -> "흰색"
            BLACK -> "검은색"
        }


    companion object{
        fun from(color : String):StoneColor{
            return when(color){
                "white" -> WHITE
                "black" -> BLACK
                else -> throw IllegalArgumentException("잘못된 색깔 값입니다.")
            }
        }
    }
}
