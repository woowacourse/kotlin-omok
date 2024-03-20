package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StonesStateTest {
    class MockStonesState : StonesState()

    private val mockStonesState = MockStonesState()
    lateinit var coordinate: Coordinate

    @BeforeEach
    fun setUp() {
        val row = Row.from("6")
        val column = Column.from("C")
        coordinate = Coordinate(row, column)
        val blackStone = Stone("black", coordinate)
        mockStonesState.put(blackStone)
    }

    @Test
    fun `착수하려는 위치에 이미 돌이 있다면, true를 반환한다`() {
        val unableCoordinate = coordinate
        val actual = mockStonesState.checkOccupiedCoordinate(unableCoordinate)

        assertThat(actual).isTrue()
    }

    @Test
    fun `착수하려는 위치에 돌이 없다면, false를 반환한다`() {
        val ableCoordinate = Coordinate(Row.from("7"), Column.from("H"))
        val actual = mockStonesState.checkOccupiedCoordinate(ableCoordinate)

        assertThat(actual).isFalse()
    }
}
