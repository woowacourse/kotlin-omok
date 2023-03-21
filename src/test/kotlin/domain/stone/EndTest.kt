package domain.stone

import domain.state.End
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rule.wrapper.point.Point

class EndTest {

    @Test
    fun `게임이 끝난 상태일 때 바둑알을 놓으려고 하면 에러가 발생한다`() {
        val stonePoint = Point(1, 1)
        val stoneType = StoneType.BLACK
        val stone = Stone(stonePoint, stoneType)

        val end = TestEnd()
        assertThrows<IllegalStateException> {
            end.put(stone)
        }
    }
}

class TestEnd : End() {
    override fun getWinner(): StoneType {
        TODO("Not yet implemented")
    }
}
