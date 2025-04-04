package woowacourse.omok.model.rule

sealed class CoordinateError {
    data object InvalidCoordinateFormat : CoordinateError()
    data object InvalidRowNumber : CoordinateError()
    data object InvalidColString : CoordinateError()
}
