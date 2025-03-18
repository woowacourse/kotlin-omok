import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameBordTest {
    @Test
    fun `오목판에 새로운 돌을 추가할 수 있다`() {
        val gameBord = GameBord()
        val stone = Stone(Position(Row.from(1), Col.from('A')), StoneColor.WHITE)
        gameBord.addStone(stone)

        assertThat(gameBord.stones[0]).isEqualTo(stone)
    }
}
