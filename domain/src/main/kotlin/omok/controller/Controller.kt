package omok.controller

class Controller() {
    // private val board = Board(Player(), Player())

    // fun gameStart() {
    //     turn(Turn.Black)
    // }
    //
    // fun turn(state: State) {
    //     when (state) {
    //         Turn.Black -> blackTurn(position)
    //         Turn.White -> whiteTurn(position)
    //         Win.Black -> ""
    //         Win.White -> ""
    //     }
    // }
    //
    // fun blackTurn(position) {
    //     board.blackPlayer.put(BlackStone(position))
    //     board.occupyPosition(position)
    //     return if (lineJudge(board.blackPlayer, BlackStone(position))) turn(Win.Black) else turn(Turn.White)
    // }
    //
    // fun whiteTurn(position: Position) {
    //     board.whitePlayer.put(WhiteStone(position))
    //     board.occupyPosition(position)
    //     return if (lineJudge(board.whitePlayer, WhiteStone(position))) turn(Win.White) else turn(Turn.Black)
    // }
    //
    // fun positionFind(index: Int): Position{
    //     val x = (index % 15) + 1
    //     val y = 15 - index / 15
    //     return Position(HorizontalAxis.getHorizontalAxis(x), y)
    // }
    //
    // private fun lineJudge(player: Player, stone: Stone) = LineJudgement(player, stone).check()
}
