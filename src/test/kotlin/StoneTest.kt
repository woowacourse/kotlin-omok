import domain.position.Col
import domain.position.Position
import domain.position.Row
import domain.stone.Stone
import domain.stone.StoneColor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class StoneTest {
    @Test
    fun `오목돌을 생성한다`() {
        assertDoesNotThrow {
            Stone(
                position = Position(Row.from(3), Col.from('C')),
                color = StoneColor.BLACK,
            )
        }
    }
}
