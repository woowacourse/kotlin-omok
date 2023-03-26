package woowacourse.omok

import domain.domain.BoardState
import domain.domain.CoordinateState
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.omok.data.SerializableBoard

class SerializationTest {

    @Test
    fun `BoardState 직렬화 후 역직렬화 테스트`() {
        val temp = List(14) { MutableList(14) { CoordinateState.EMPTY } }
        temp[1][1] = CoordinateState.BLACK
        val boardState = BoardState(temp)
        val serializableBoard = SerializableBoard(boardState.value)

        val json = Json.encodeToString(serializableBoard)

        val serializableObject: SerializableBoard = Json.decodeFromString<SerializableBoard>(json)

        assertThat(serializableObject).isEqualTo(serializableBoard)
    }
}
