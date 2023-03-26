package omok.model.external.rule.other

interface Iterator<T> {
    fun hasNext(): Boolean
    fun next(): T
}
