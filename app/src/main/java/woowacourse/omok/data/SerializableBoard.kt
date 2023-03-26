package woowacourse.omok.data

import domain.domain.CoordinateState
import kotlinx.serialization.Serializable

@Serializable
data class SerializableBoard(val value: List<List<CoordinateState>>)
