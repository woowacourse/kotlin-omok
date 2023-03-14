package domain

data class StonePosition private constructor(
    val x: Int,
    val y: Int
) {
    companion object {
        fun from(x: Int, y: Int) = StonePosition(x, y)
    }
}
