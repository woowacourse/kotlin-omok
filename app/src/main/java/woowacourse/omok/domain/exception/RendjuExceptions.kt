package woowacourse.omok.domain.exception

sealed class RendjuExceptions : Exceptions() {
    object DoubleThreeExceptions : RendjuExceptions()

    object DoubleFourExceptions : RendjuExceptions()

    object OverLineExceptions : RendjuExceptions()
}
