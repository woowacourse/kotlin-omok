package domain.stone

import domain.position.Position

class Stone(
    val position: Position,
    val color: StoneColor,
) {
    companion object {
        fun of(
            position: Position,
            color: StoneColor,
        ): Stone = Stone(position, color)
    }
}
