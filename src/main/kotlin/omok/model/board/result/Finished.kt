package omok.model.board.result

import omok.model.board.Point

sealed class Finished : PlaceStoneResult {
    data class GameFinished(val point: Point) : Finished()

    data class BoardFull(val point: Point) : Finished()
}
