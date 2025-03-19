package omok.domain

interface Turn {
    val color: StoneType
    fun stone(position: String): Stone
}