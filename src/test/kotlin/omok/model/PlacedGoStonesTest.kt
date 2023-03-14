package omok.model

import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class PlacedGoStonesTest {
    @Test
    fun `입력받은 위치에 이미 바둑돌이 있다면 오류가 발생한다`() {
        val stone = GoStone(GoStoneColor.BLACK, Coordinate("H8"))
        val placedStones = PlacedGoStones(mutableListOf(stone))

        assertThatIllegalArgumentException()
            .isThrownBy { placedStones.addStone(GoStoneColor.WHITE, ::getCoordinate) }
            .withMessageContaining("해당 위치에 이미 바둑돌이 있습니다.")
    }

    private fun getCoordinate() = Coordinate("H8")
}
