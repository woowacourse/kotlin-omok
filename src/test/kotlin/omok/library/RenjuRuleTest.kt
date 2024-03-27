package omok.library

import omok.model.Black
import omok.model.CoordsNumber
import omok.model.OmokGame
import omok.model.White
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RenjuRuleTest {
    @Test
    fun `돌을 올바르게 놓는 경우`() {
        val omokGame = OmokGame(rule = RenjuRule)
        val x = CoordsNumber(0)
        val y = CoordsNumber(0)
        omokGame.setStone(Black(x, y))
        assertThat(Black(x, y)).isEqualTo(omokGame.gameBoard[y.number][x.number])
    }

    @Test
    fun `돌을 놓은 위치가 비어 있지 않은 경우 확인`() {
        val omokGame = OmokGame(rule = RenjuRule)
        val x = CoordsNumber(0)
        val y = CoordsNumber(0)
        omokGame.setStone(White(x, y))
        val result2 = omokGame.setStone(White(x, y))
        assertThat(result2).isEqualTo(false)
    }

    @Test
    fun `돌을 놓은 후 게임이 종료되는 경우 확인`() {
        val omokGame = OmokGame(rule = RenjuRule)
        for (i in 0 until 4) {
            omokGame.setStone(Black(CoordsNumber(i), CoordsNumber(0)))
        }
        omokGame.setStone(Black(CoordsNumber(4), CoordsNumber(0)))
        println(omokGame.omokStones)
        assertThat(omokGame.isRunning()).isEqualTo(false)
    }

    @Test
    fun `33 금수 확인`() {
        val omokGame = OmokGame(rule = RenjuRule)
        for (i in 2 until 4) {
            omokGame.setStone(Black(CoordsNumber(i), CoordsNumber(1)))
        }
        for (j in 2 until 4) {
            omokGame.setStone(Black(CoordsNumber(1), CoordsNumber(j)))
        }
        val forbids = omokGame.getForbiddenPositions()
        assertThat(forbids).isEqualTo(listOf(CoordsNumber(1) to CoordsNumber(1)))
    }

    @Test
    fun `43은 금수아니다`() {
        val omokGame = OmokGame(rule = RenjuRule)
        for (i in 2 until 5) {
            omokGame.setStone(Black(CoordsNumber(i), CoordsNumber(1)))
        }
        for (j in 2 until 4) {
            omokGame.setStone(Black(CoordsNumber(1), CoordsNumber(j)))
        }
        val forbids = omokGame.getForbiddenPositions()
        assertThat(forbids.size).isEqualTo(0)
    }

    @Test
    fun `44는 한쪽이 막혀있어도 금수다`() {
        val omokGame = OmokGame(rule = RenjuRule)
        for (i in 2 until 5) {
            omokGame.setStone(Black(CoordsNumber(i), CoordsNumber(1)))
        }
        for (j in 2 until 5) {
            omokGame.setStone(Black(CoordsNumber(1), CoordsNumber(j)))
        }
        omokGame.setStone(White(CoordsNumber(1), CoordsNumber(5)))
        omokGame.setStone(Black(CoordsNumber(14), CoordsNumber(14)))
        val forbids = omokGame.getForbiddenPositions()
        assertThat(forbids).isEqualTo(listOf(CoordsNumber(1) to CoordsNumber(1)))
    }

    @Test
    fun `두쪽이 다 막힌 44는 금수아니다`() {
        val omokGame = OmokGame(rule = RenjuRule)
        for (i in 2 until 5) {
            omokGame.setStone(Black(CoordsNumber(i), CoordsNumber(1)))
        }
        for (j in 2 until 5) {
            omokGame.setStone(Black(CoordsNumber(1), CoordsNumber(j)))
        }
        omokGame.setStone(White(CoordsNumber(1), CoordsNumber(5)))
        omokGame.setStone(White(CoordsNumber(1), CoordsNumber(1)))
        omokGame.setStone(Black(CoordsNumber(14), CoordsNumber(14)))
        val forbids = omokGame.getForbiddenPositions()
        assertThat(forbids.size).isEqualTo(0)
    }
}
