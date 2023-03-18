import domain.player.Player
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

fun createPlayer(): Player {

    return object : Player() {
        override val color: Color
            get() = Color.WHITE
    }
}

fun Stone(x: Int, y: Int, color: Color): Stone = Stone(Point(x, y), color)
