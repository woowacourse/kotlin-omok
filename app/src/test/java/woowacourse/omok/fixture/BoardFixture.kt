package omok.fixture

import omok.model.rule.OmokRuleManager
import omok.model.rule.count.OverlineRule
import omok.model.rule.lib.DoubleFourMoveRule
import omok.model.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.model.StoneColor
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.Point

private fun createRules(): OmokRuleManager =
    OmokRuleManager.apply {
        forbiddenMoveRule.add(OverlineRule())
        forbiddenMoveRule.add(DoubleThreeMoveRule())
        forbiddenMoveRule.add(DoubleFourMoveRule())
    }

val rules = createRules()

val defaultOmokBoardSize = BoardSize(15)

val verticalFiveInRowBoard: Board =
    Board(defaultOmokBoardSize, rules).apply {
        // 세로로 오목 (1, 1)에서 (5, 1)까지
        placeStone(Point(1, 1), StoneColor.WHITE)
        placeStone(Point(2, 1), StoneColor.WHITE)
        placeStone(Point(3, 1), StoneColor.WHITE)
        placeStone(Point(4, 1), StoneColor.WHITE)
    }

val horizontalFiveInRowBoard: Board =
    Board(defaultOmokBoardSize, rules).apply {
        // 가로로 오목 (1, 1)에서 (1, 5)까지
        placeStone(Point(1, 1), StoneColor.WHITE)
        placeStone(Point(1, 2), StoneColor.WHITE)
        placeStone(Point(1, 3), StoneColor.WHITE)
        placeStone(Point(1, 4), StoneColor.WHITE)
    }

val diagonalFiveInRowBoard: Board =
    Board(defaultOmokBoardSize, rules).apply {
        // 대각선으로 오목 (1, 1)에서 (5, 5)까지
        placeStone(Point(1, 1), StoneColor.BLACK)
        placeStone(Point(2, 2), StoneColor.BLACK)
        placeStone(Point(3, 3), StoneColor.BLACK)
        placeStone(Point(4, 4), StoneColor.BLACK)
    }

val antiDiagonalFiveInRowBoard: Board =
    Board(defaultOmokBoardSize, rules).apply {
        // 반대 대각선으로 오목 (1, 5)에서 (5, 1)까지
        placeStone(Point(1, 5), StoneColor.BLACK)
        placeStone(Point(2, 4), StoneColor.BLACK)
        placeStone(Point(3, 3), StoneColor.BLACK)
        placeStone(Point(4, 2), StoneColor.BLACK)
    }

// 오목 렌주룰 금수 테스트 피처

// 삼삼 금수 테스트
val doubleThreeForbiddenBoard: Board =
    Board(defaultOmokBoardSize, rules).apply {
        // A Point (4, 12) and surrounding stones
        placeStone(Point(3, 12), StoneColor.BLACK) // (C, 12)
        placeStone(Point(5, 12), StoneColor.BLACK) // (E, 12)
        placeStone(Point(4, 14), StoneColor.BLACK) // (D, 14)
        placeStone(Point(4, 13), StoneColor.BLACK) // (D, 13)

        // B Point (5, 3) and surrounding stones
        placeStone(Point(2, 6), StoneColor.BLACK) // (B, 6)
        placeStone(Point(3, 5), StoneColor.BLACK) // (C, 5)
        placeStone(Point(5, 6), StoneColor.BLACK) // (E, 6)
        placeStone(Point(5, 5), StoneColor.BLACK) // (E, 5)

        // C Point (12, 11) and surrounding stones
        placeStone(Point(10, 9), StoneColor.BLACK) // (J, 9)
        placeStone(Point(13, 12), StoneColor.BLACK) // (M, 12)
        placeStone(Point(13, 10), StoneColor.BLACK) // (M, 10)
        placeStone(Point(14, 9), StoneColor.BLACK) // (N, 9)

        // D Point (11, 4) and surrounding stones
        placeStone(Point(11, 6), StoneColor.BLACK) // (K, 6)
        placeStone(Point(11, 3), StoneColor.BLACK) // (K, 3)
        placeStone(Point(13, 4), StoneColor.BLACK) // (M, 4)
        placeStone(Point(14, 4), StoneColor.BLACK) // (N, 4)
    }

// 사사 금수 테스트
val doubleFourForbiddenBoard: Board =
    Board(defaultOmokBoardSize, rules).apply {

        // A Point (7, 7)
        placeStone(Point(5, 5), StoneColor.BLACK)
        placeStone(Point(6, 6), StoneColor.BLACK)
        placeStone(Point(8, 8), StoneColor.BLACK)
        placeStone(Point(8, 6), StoneColor.BLACK)
        placeStone(Point(9, 5), StoneColor.BLACK)
        placeStone(Point(6, 8), StoneColor.BLACK)
        placeStone(Point(8, 12), StoneColor.BLACK)

        // A Point (6, 12)
        placeStone(Point(5, 12), StoneColor.BLACK)
        placeStone(Point(7, 12), StoneColor.BLACK)
        placeStone(Point(6, 11), StoneColor.BLACK)
        placeStone(Point(6, 13), StoneColor.BLACK)
        placeStone(Point(6, 14), StoneColor.BLACK)
    }

// 장목 금수 테스트
val overlineForbiddenBoard: Board =
    Board(defaultOmokBoardSize, rules).apply {
        // A Point (5, 1)
        placeStone(Point(1, 1), StoneColor.BLACK)
        placeStone(Point(2, 1), StoneColor.BLACK)
        placeStone(Point(3, 1), StoneColor.BLACK)
        placeStone(Point(4, 1), StoneColor.BLACK)
        placeStone(Point(6, 1), StoneColor.BLACK)
    }

// 4-3 테스트
val fourThreeBoard: Board =
    Board(defaultOmokBoardSize, rules).apply {
        // A Point (8, 5)
        placeStone(Point(5, 5), StoneColor.BLACK)
        placeStone(Point(6, 5), StoneColor.BLACK)
        placeStone(Point(7, 5), StoneColor.BLACK)
        placeStone(Point(8, 6), StoneColor.BLACK)
        placeStone(Point(8, 7), StoneColor.BLACK)
    }

val falseDoubleThreeBoard: Board =
    Board(defaultOmokBoardSize, rules).apply {
        placeStone(Point(3, 3), StoneColor.BLACK)
        placeStone(Point(5, 3), StoneColor.BLACK)

        placeStone(Point(4, 2), StoneColor.BLACK)
        placeStone(Point(4, 4), StoneColor.BLACK)

        placeStone(Point(6, 3), StoneColor.WHITE)
    }

val whitePassForbiddenMoveBoard: Board =
    Board(defaultOmokBoardSize, rules).apply {
        // A Point (4, 12) and surrounding stones
        placeStone(Point(3, 12), StoneColor.WHITE) // (C, 12)
        placeStone(Point(5, 12), StoneColor.WHITE) // (E, 12)
        placeStone(Point(4, 14), StoneColor.WHITE) // (D, 14)
        placeStone(Point(4, 13), StoneColor.WHITE) // (D, 13)
    }
