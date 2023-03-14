package omok.domain

class YLine(val value: Map<YCoordinate, XLine>) {
    val keys = value.keys
    val values = value.values

    constructor () : this(YCoordinate.all().associateWith { XLine() })
}
