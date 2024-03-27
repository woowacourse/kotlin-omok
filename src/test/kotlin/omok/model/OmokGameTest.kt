package omok.model

import omok.library.RenjuRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OmokGameTest {
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
    fun `게임 상태가 실행 중인지 확인`() {
        val omokGame = OmokGame(rule = RenjuRule)
        Assertions.assertTrue(omokGame.isRunning())
    }

    @Test
    fun `돌을 올바르게 놓는 경우`() {
        val omokGame = OmokGame(rule = RenjuRule)

        omokGame.placeStoneOnBoard(Black(0, 0))
        assertThat(Black(0, 0)).isEqualTo(omokGame.getBoard()[0][0])
    }

    @Test
    fun `이미 돌이 놓아진 자리에 다른 돌을 놓을 경우, 실패한다`() {
        val omokGame = OmokGame(rule = RenjuRule)
        omokGame.placeStoneOnBoard(Black(0, 0))
        val result = omokGame.placeStoneOnBoard(White(0, 0))
        assertThat(result).isEqualTo(false)
    }
}
