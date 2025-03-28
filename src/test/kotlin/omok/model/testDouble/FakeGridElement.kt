package omok.model.testDouble

import omok.model.entity.position.GridElement

class FakeGridElement : GridElement {
    override val value: Int = 5

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FakeGridElement

        return value == other.value
    }

    override fun hashCode(): Int = value
}
