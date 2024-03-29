@file:Suppress("ktlint:standard:no-wildcard-imports")

package omok.library

import omok.model.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.OmokGame

class RenjuRuleTest {
    fun Black(
        row: Int,
        col: Int,
    ): Black {
        return Black(BoardPosition(BoardCoordinate(row), BoardCoordinate(col)))
    }

    fun White(
        row: Int,
        col: Int,
    ): White {
        return White(BoardPosition(BoardCoordinate(row), BoardCoordinate(col)))
    }

    @Test
    fun `돌을 놓은 후 게임이 종료되는 경우 확인`() {
        val omokGame = OmokGame(rule = RenjuRule)
        for (i in 0 until 4) {
            omokGame.placeStoneOnBoard(Black(i, 0))
        }
        omokGame.placeStoneOnBoard(Black(4, 0))
        assertThat(RenjuRule.isGameOver(omokGame.getBoard(), omokGame.getCurrentStone())).isEqualTo(true)
    }

    @Test
    fun `33 금수 확인`() {
        val omokGame = OmokGame(rule = RenjuRule)
        for (i in 2 until 4) {
            omokGame.placeStoneOnBoard(Black(i, 1))
        }
        for (j in 2 until 4) {
            omokGame.placeStoneOnBoard(Black(1, j))
        }
        val forbids = RenjuRule.findForbiddenMoves(omokGame.getBoard(), omokGame.getCurrentStone())
        assertThat(forbids).isEqualTo(listOf(1 to 1))
    }

    @Test
    fun `43은 금수아니다`() {
        val omokGame = OmokGame(rule = RenjuRule)
        for (i in 2 until 5) {
            omokGame.placeStoneOnBoard(Black(i, 1))
        }
        for (j in 2 until 4) {
            omokGame.placeStoneOnBoard(Black(1, j))
        }
        val forbids = RenjuRule.findForbiddenMoves(omokGame.getBoard(), omokGame.getCurrentStone())
        assertThat(forbids.size).isEqualTo(0)
    }

    @Test
    fun `44는 한쪽이 막혀있어도 금수다`() {
        val omokGame = OmokGame(rule = RenjuRule)
        for (i in 2 until 5) {
            omokGame.placeStoneOnBoard(Black(i, 1))
        }
        for (j in 2 until 5) {
            omokGame.placeStoneOnBoard(Black(1, j))
        }
        omokGame.placeStoneOnBoard(White(1, 5))
        omokGame.placeStoneOnBoard(Black(14, 14)) // 검을 돌을 놔야 금수로직이 발동
        val forbids = RenjuRule.findForbiddenMoves(omokGame.getBoard(), omokGame.getCurrentStone())
        assertThat(forbids).isEqualTo(listOf((1 to 1)))
    }

    @Test
    fun `두쪽이 다 막힌 44는 금수아니다`() {
        val omokGame = OmokGame(rule = RenjuRule)
        for (i in 2 until 5) {
            omokGame.placeStoneOnBoard(Black(i, 1))
        }
        for (j in 2 until 5) {
            omokGame.placeStoneOnBoard(Black(1, j))
        }
        omokGame.placeStoneOnBoard(White(1, 5))
        omokGame.placeStoneOnBoard(White(1, 1))
        omokGame.placeStoneOnBoard(Black(14, 14)) // 검을 돌을 놔야 금수로직이 발동
        val forbids = RenjuRule.findForbiddenMoves(omokGame.getBoard(), omokGame.getCurrentStone())
        assertThat(forbids.size).isEqualTo(0)
    }
}
