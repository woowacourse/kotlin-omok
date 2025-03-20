package omok.fixture

import omok.model.board.Board
import omok.model.stone.Position
import omok.model.stone.StoneColor

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

// 오목 렌주룰 금수 테스트 피처

// 삼삼 금수 테스트
val doubleThreeForbiddenBoard: Board =
    Board().apply {
        // A position (4, 12) and surrounding stones
        placeStone(Position(3, 12), StoneColor.BLACK) // (C, 12)
        placeStone(Position(5, 12), StoneColor.BLACK) // (E, 12)
        placeStone(Position(4, 14), StoneColor.BLACK) // (D, 14)
        placeStone(Position(4, 13), StoneColor.BLACK) // (D, 13)

        // B position (5, 3) and surrounding stones
        placeStone(Position(2, 6), StoneColor.BLACK) // (B, 6)
        placeStone(Position(3, 5), StoneColor.BLACK) // (C, 5)
        placeStone(Position(5, 6), StoneColor.BLACK) // (E, 6)
        placeStone(Position(5, 5), StoneColor.BLACK) // (E, 5)

        // C position (12, 11) and surrounding stones
        placeStone(Position(10, 9), StoneColor.BLACK) // (J, 9)
        placeStone(Position(13, 12), StoneColor.BLACK) // (M, 12)
        placeStone(Position(13, 10), StoneColor.BLACK) // (M, 10)
        placeStone(Position(14, 9), StoneColor.BLACK) // (N, 9)

        // D position (11, 4) and surrounding stones
        placeStone(Position(11, 6), StoneColor.BLACK) // (K, 6)
        placeStone(Position(11, 3), StoneColor.BLACK) // (K, 3)
        placeStone(Position(13, 4), StoneColor.BLACK) // (M, 4)
        placeStone(Position(14, 4), StoneColor.BLACK) // (N, 4)
    }

// 사사 금수 테스트
val doubleFourForbiddenBoard: Board =
    Board().apply {
        // A position (3, 13), B position (3, 8)
        placeStone(Position(3, 15), StoneColor.BLACK) // (C,15)
        placeStone(Position(3, 14), StoneColor.BLACK) // (C, 14)
        placeStone(Position(3, 12), StoneColor.BLACK) // (C, 12)
        placeStone(Position(3, 11), StoneColor.BLACK) // (C, 11)
        placeStone(Position(3, 10), StoneColor.BLACK) // (C, 10)
        placeStone(Position(4, 12), StoneColor.BLACK) // (D, 12)
        placeStone(Position(7, 12), StoneColor.BLACK) // (G, 12)
    }

// 장목 금수 테스트
val overlineForbiddenBoard: Board =
    Board().apply {
        // A position (5, 1)
        placeStone(Position(1, 1), StoneColor.BLACK)
        placeStone(Position(2, 1), StoneColor.BLACK)
        placeStone(Position(3, 1), StoneColor.BLACK)
        placeStone(Position(4, 1), StoneColor.BLACK)
        placeStone(Position(6, 1), StoneColor.BLACK)
    }
