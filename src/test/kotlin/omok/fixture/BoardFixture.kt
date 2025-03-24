package omok.fixture

import omok.model.StoneColor
import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.board.Point
import omok.model.rule.count.FiveInRowRule

val defaultOmokBoardSize = BoardSize(15)
val rule = FiveInRowRule

val verticalFiveInRowBoard: Board =
    Board(defaultOmokBoardSize).apply {
        // 세로로 오목 (1, 1)에서 (5, 1)까지
        placeStone(Point(1, 1), StoneColor.WHITE, rule)
        placeStone(Point(2, 1), StoneColor.WHITE, rule)
        placeStone(Point(3, 1), StoneColor.WHITE, rule)
        placeStone(Point(4, 1), StoneColor.WHITE, rule)
    }

val horizontalFiveInRowBoard: Board =
    Board(defaultOmokBoardSize).apply {
        // 가로로 오목 (1, 1)에서 (1, 5)까지
        placeStone(Point(1, 1), StoneColor.WHITE, rule)
        placeStone(Point(1, 2), StoneColor.WHITE, rule)
        placeStone(Point(1, 3), StoneColor.WHITE, rule)
        placeStone(Point(1, 4), StoneColor.WHITE, rule)
    }

val diagonalFiveInRowBoard: Board =
    Board(defaultOmokBoardSize).apply {
        // 대각선으로 오목 (1, 1)에서 (5, 5)까지
        placeStone(Point(1, 1), StoneColor.BLACK, rule)
        placeStone(Point(2, 2), StoneColor.BLACK, rule)
        placeStone(Point(3, 3), StoneColor.BLACK, rule)
        placeStone(Point(4, 4), StoneColor.BLACK, rule)
    }

val antiDiagonalFiveInRowBoard: Board =
    Board(defaultOmokBoardSize).apply {
        // 반대 대각선으로 오목 (1, 5)에서 (5, 1)까지
        placeStone(Point(1, 5), StoneColor.BLACK, rule)
        placeStone(Point(2, 4), StoneColor.BLACK, rule)
        placeStone(Point(3, 3), StoneColor.BLACK, rule)
        placeStone(Point(4, 2), StoneColor.BLACK, rule)
    }

// 오목 렌주룰 금수 테스트 피처

// 삼삼 금수 테스트
val doubleThreeForbiddenBoard: Board =
    Board(defaultOmokBoardSize).apply {
        // A Point (4, 12) and surrounding stones
        placeStone(Point(3, 12), StoneColor.BLACK, rule) // (C, 12)
        placeStone(Point(5, 12), StoneColor.BLACK, rule) // (E, 12)
        placeStone(Point(4, 14), StoneColor.BLACK, rule) // (D, 14)
        placeStone(Point(4, 13), StoneColor.BLACK, rule) // (D, 13)

        // B Point (5, 3) and surrounding stones
        placeStone(Point(2, 6), StoneColor.BLACK, rule) // (B, 6)
        placeStone(Point(3, 5), StoneColor.BLACK, rule) // (C, 5)
        placeStone(Point(5, 6), StoneColor.BLACK, rule) // (E, 6)
        placeStone(Point(5, 5), StoneColor.BLACK, rule) // (E, 5)

        // C Point (12, 11) and surrounding stones
        placeStone(Point(10, 9), StoneColor.BLACK, rule) // (J, 9)
        placeStone(Point(13, 12), StoneColor.BLACK, rule) // (M, 12)
        placeStone(Point(13, 10), StoneColor.BLACK, rule) // (M, 10)
        placeStone(Point(14, 9), StoneColor.BLACK, rule) // (N, 9)

        // D Point (11, 4) and surrounding stones
        placeStone(Point(11, 6), StoneColor.BLACK, rule) // (K, 6)
        placeStone(Point(11, 3), StoneColor.BLACK, rule) // (K, 3)
        placeStone(Point(13, 4), StoneColor.BLACK, rule) // (M, 4)
        placeStone(Point(14, 4), StoneColor.BLACK, rule) // (N, 4)
    }

// 사사 금수 테스트
val doubleFourForbiddenBoard: Board =
    Board(defaultOmokBoardSize).apply {

        // A Point (7, 7)
        placeStone(Point(5, 5), StoneColor.BLACK, rule)
        placeStone(Point(6, 6), StoneColor.BLACK, rule)
        placeStone(Point(8, 8), StoneColor.BLACK, rule)
        placeStone(Point(8, 6), StoneColor.BLACK, rule)
        placeStone(Point(9, 5), StoneColor.BLACK, rule)
        placeStone(Point(6, 8), StoneColor.BLACK, rule)
        placeStone(Point(8, 12), StoneColor.BLACK, rule)

        // A Point (6, 12)
        placeStone(Point(5, 12), StoneColor.BLACK, rule)
        placeStone(Point(7, 12), StoneColor.BLACK, rule)
        placeStone(Point(6, 11), StoneColor.BLACK, rule)
        placeStone(Point(6, 13), StoneColor.BLACK, rule)
        placeStone(Point(6, 14), StoneColor.BLACK, rule)
    }

// 장목 금수 테스트
val overlineForbiddenBoard: Board =
    Board(defaultOmokBoardSize).apply {
        // A Point (5, 1)
        placeStone(Point(1, 1), StoneColor.BLACK, rule)
        placeStone(Point(2, 1), StoneColor.BLACK, rule)
        placeStone(Point(3, 1), StoneColor.BLACK, rule)
        placeStone(Point(4, 1), StoneColor.BLACK, rule)
        placeStone(Point(6, 1), StoneColor.BLACK, rule)
    }

// 4-3 테스트
val fourThreeBoard: Board =
    Board(defaultOmokBoardSize).apply {
        // A Point (8, 5)
        placeStone(Point(5, 5), StoneColor.BLACK, rule)
        placeStone(Point(6, 5), StoneColor.BLACK, rule)
        placeStone(Point(7, 5), StoneColor.BLACK, rule)
        placeStone(Point(8, 6), StoneColor.BLACK, rule)
        placeStone(Point(8, 7), StoneColor.BLACK, rule)
    }

val falseDoubleThreeBoard: Board =
    Board(defaultOmokBoardSize).apply {
        placeStone(Point(3, 3), StoneColor.BLACK, rule)
        placeStone(Point(5, 3), StoneColor.BLACK, rule)

        placeStone(Point(4, 2), StoneColor.BLACK, rule)
        placeStone(Point(4, 4), StoneColor.BLACK, rule)

        placeStone(Point(6, 3), StoneColor.WHITE, rule)
    }

val whitePassForbiddenMoveBoard: Board =
    Board(defaultOmokBoardSize).apply {
        // A Point (4, 12) and surrounding stones
        placeStone(Point(3, 12), StoneColor.WHITE, rule) // (C, 12)
        placeStone(Point(5, 12), StoneColor.WHITE, rule) // (E, 12)
        placeStone(Point(4, 14), StoneColor.WHITE, rule) // (D, 14)
        placeStone(Point(4, 13), StoneColor.WHITE, rule) // (D, 13)
    }
