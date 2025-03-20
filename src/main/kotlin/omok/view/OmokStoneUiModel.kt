package omok.view

import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType

data class OmokStoneUiModel(
    val stone: String,
    val position: String,
) {
    companion object {
        fun create(omokStone: OmokStone): OmokStoneUiModel {
            val stone = omokStone.stoneType.toKoreanRepresentation()
            val position = omokStone.position.toCoordinateString()
            return OmokStoneUiModel(stone, position)
        }

        private fun StoneType.toKoreanRepresentation(): String {
            return when (this) {
                StoneType.BLACK -> "흑"
                StoneType.WHITE -> "백"
                else -> throw IllegalArgumentException("돌이 존재하지 않습니다.")
            }
        }

        private fun Position.toCoordinateString(): String {
            val column = Column.COLUMNS[this.column.value - 1]
            val row = this.row.value
            return "${column}$row"
        }
    }
}
