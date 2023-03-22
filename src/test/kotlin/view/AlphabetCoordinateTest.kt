package view

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class AlphabetCoordinateTest {
    @ParameterizedTest
    @CsvSource(
        "A,0",
        "B,1",
        "C,2",
        "D,3",
        "E,4",
        "F,5",
        "G,6",
        "H,7",
        "I,8",
        "J,9",
        "K,10",
        "L,11",
        "M,12",
        "N,13",
        "O,14",
    )
    fun `알파벳을 x죄표 값으로 변환한다`(coordinateString: String, coordinate: Int) {
        assertEquals(AlphabetCoordinate.convertX(coordinateString), coordinate)
    }

    @ParameterizedTest
    @CsvSource(
        "A,0",
        "B,1",
        "C,2",
        "D,3",
        "E,4",
        "F,5",
        "G,6",
        "H,7",
        "I,8",
        "J,9",
        "K,10",
        "L,11",
        "M,12",
        "N,13",
        "O,14",
    )
    fun `x죄표 값을 알파벳으로 변환한다`(coordinateString: String, coordinate: Int) {
        assertEquals(AlphabetCoordinate.convertAlphabet(coordinate).name, coordinateString)
    }
}
