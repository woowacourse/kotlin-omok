package woowacourse.omok.data

import domain.domain.BoardState
import domain.domain.CoordinateState
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class SerializableBoard(val value: List<List<CoordinateState>>) {

    companion object {
        fun stringToBoardState(boardString: String): BoardState =
            BoardState(Json.decodeFromString<SerializableBoard>(boardString).value.map { it.toMutableList() })

        fun boardStateToString(baordState: BoardState): String =
            Json.encodeToString(SerializableBoard(baordState.value))
    }
}
