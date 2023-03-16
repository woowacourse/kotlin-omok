package model.domain.tools

@JvmInline
value class Coordination private constructor(val value: Int) {
    companion object {
        fun from(value: Int): Coordination = Coordination(value)
    }
}
