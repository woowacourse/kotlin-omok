package error

import dto.VectorDTO

sealed class StoneReadResult : OmokError {
    data class Success(val vector: VectorDTO) : StoneReadResult()
    object ColumnNotAlpha : StoneReadResult()
    object RowNotNumeric : StoneReadResult()
}
