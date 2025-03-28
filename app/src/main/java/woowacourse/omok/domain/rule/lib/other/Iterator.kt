package woowacourse.omok.domain.rule.lib.other

interface Iterator<T> {
    fun hasNext(): Boolean

    fun next(): T
}
