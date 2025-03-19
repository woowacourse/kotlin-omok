package omok.fixture

import omok.board.Board
import omok.stone.Position
import omok.stone.StoneColor

val verticalFiveInRowBoard: Board =
    Board().apply {
        // 세로로 오목 (1, 1)에서 (5, 1)까지
        placeStone(Position(1, 1), StoneColor.WHITE)
        placeStone(Position(2, 1), StoneColor.WHITE)
        placeStone(Position(3, 1), StoneColor.WHITE)
        placeStone(Position(4, 1), StoneColor.WHITE)
    }

val horizontalFiveInRowBoard: Board =
    Board().apply {
        // 가로로 오목 (1, 1)에서 (1, 5)까지
        placeStone(Position(1, 1), StoneColor.WHITE)
        placeStone(Position(1, 2), StoneColor.WHITE)
        placeStone(Position(1, 3), StoneColor.WHITE)
        placeStone(Position(1, 4), StoneColor.WHITE)
    }

val diagonalFiveInRowBoard: Board =
    Board().apply {
        // 대각선으로 오목 (1, 1)에서 (5, 5)까지
        placeStone(Position(1, 1), StoneColor.BLACK)
        placeStone(Position(2, 2), StoneColor.BLACK)
        placeStone(Position(3, 3), StoneColor.BLACK)
        placeStone(Position(4, 4), StoneColor.BLACK)
    }

val antiDiagonalFiveInRowBoard: Board =
    Board().apply {
        // 반대 대각선으로 오목 (1, 5)에서 (5, 1)까지
        placeStone(Position(1, 5), StoneColor.BLACK)
        placeStone(Position(2, 4), StoneColor.BLACK)
        placeStone(Position(3, 3), StoneColor.BLACK)
        placeStone(Position(4, 2), StoneColor.BLACK)
    }
