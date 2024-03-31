package woowacourse.omok.local.presentation.model

import omok.model.PositionType

data class StoneEntity (
    val id: Long,
    val x: Int,
    val y: Int,
    val positionType: PositionType
) {
    companion object {
        fun create(id: Long, x: Int, y: Int, positionTypeName: String): StoneEntity {
            val positionType = PositionType.valueOf(positionTypeName)
            return StoneEntity(id, x, y, positionType)
        }
    }
}
