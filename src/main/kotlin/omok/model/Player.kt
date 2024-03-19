package omok.model

class Player(val color: Color, positions: List<Position>) {
    private val _positions = positions.toMutableList()
    val positions: List<Position>
        get() = _positions

    fun place(position: Position)  {
        _positions.add(position)
    }
}
