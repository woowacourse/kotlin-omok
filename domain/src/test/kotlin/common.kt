import domain.board.Board
import domain.player.Player
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone
fun Stone(x: Int, y: Int, color: Color = Color.White): Stone = Stone(Point(x, y), color)

fun Player.placeStone(currentBoard: Board, placingPoint: Point): Board {
    return this.placeStone(
        currentBoard = currentBoard,
        checkBoard = {},
        decidePoint = { _, _ -> placingPoint }
    )
}