package omok

import omok.controller.OmokController
import omok.view.BoardView
import omok.view.ResultView
import omok.view.StartView
import omok.view.StonePositionView

fun main() {
    OmokController(
        StonePositionView(),
        StartView(),
        BoardView(),
        ResultView(),
        boardSize = 15,
    ).startGame()
}
