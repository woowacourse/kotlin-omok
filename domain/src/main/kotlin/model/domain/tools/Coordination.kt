package model.domain.tools

@JvmInline
value class Coordination private constructor(
    val value: Int,
) {
    companion object {
        private const val MINIMUM_NUMBER = 0
        private const val MAXIMUM_NUMBER = 14
        private val NUMBERS: Map<Int, Coordination> =
            (MINIMUM_NUMBER..MAXIMUM_NUMBER).associateWith(::Coordination)

        fun from(value: Int): Coordination = requireNotNull(NUMBERS[value])
    }
}
