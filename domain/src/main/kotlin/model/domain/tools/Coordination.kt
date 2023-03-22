package model.domain.tools

@JvmInline
value class Coordination private constructor(val value: Int) {
    companion object {
        private const val MINIMUM_NUMBER = 0
        private const val MAXIMUM_NUMBER = 14
        private val NUMBERS: Map<Int, Coordination> =
            (MINIMUM_NUMBER..MAXIMUM_NUMBER).associateWith(::Coordination)
        private const val COORDINATION_RANGE_ERROR =
            "좌표는 $MINIMUM_NUMBER 이상 $MAXIMUM_NUMBER 이하여야 합니다."

        fun from(value: Int): Coordination =
            requireNotNull(NUMBERS[value]) { COORDINATION_RANGE_ERROR }
    }
}
