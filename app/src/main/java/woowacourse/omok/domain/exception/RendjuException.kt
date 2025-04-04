package woowacourse.omok.domain.exception

sealed class RendjuException : Exceptions() {
    object DoubleThreeException : RendjuException()

    object DoubleFourException : RendjuException()

    object OverLineException : RendjuException()
}
