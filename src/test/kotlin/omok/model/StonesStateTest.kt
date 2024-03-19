package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StonesStateTest {
    class MockStonesState : StonesState()

    private val mockStonesState = MockStonesState()

    @BeforeEach
    fun setUp() {
        val coordinate = Coordinate(6, "C")
        val blackStone = Stone("black", coordinate)
        mockStonesState.put(blackStone)
    }

    @Test
    fun `착수하려는 위치에 이미 돌이 있다면, true를 반환한다`() {
        val unableCoordinate = Coordinate(6, "C")
        val actual = mockStonesState.checkOccupiedCoordinate(unableCoordinate)

        assertThat(actual).isTrue()
    }

    @Test
    fun `착수하려는 위치에 돌이 없다면, false를 반환한다`() {
        val ableCoordinate = Coordinate(7, "H")
        val actual = mockStonesState.checkOccupiedCoordinate(ableCoordinate)

        assertThat(actual).isFalse()
    }
}
