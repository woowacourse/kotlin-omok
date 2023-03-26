import domain.OmokGame
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OmokGameTest {


    @Test
    fun `오목 게임이 끝난 상태에서 placeStone을 호출하면 예외를 던진다`() {
        val omokGame = OmokGame(makeBlackWin())

        assertThrows<IllegalStateException> {
            omokGame.placeStone(Point(1, 6))
        }
    }


    private fun makeBlackWin(): List<Stone> {

        return listOf(
            Stone(1, 1, Color.Black),
            Stone(1, 2, Color.Black),
            Stone(1, 3, Color.Black),
            Stone(1, 4, Color.Black),
            Stone(1, 5, Color.Black),
        )
    }
}