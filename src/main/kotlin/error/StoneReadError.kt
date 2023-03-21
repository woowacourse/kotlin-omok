package error

sealed interface StoneReadError : OmokError {
    object ColumnNotAlpha : StoneReadError
    object RowNotNumeric : StoneReadError
}
