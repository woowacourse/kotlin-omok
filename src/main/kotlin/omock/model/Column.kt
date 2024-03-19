package omock.model

@JvmInline
    value class Column(val comma: String) {
        init {
            require(comma in COLUM_RANGE) {
                ERROR_ROW_RANGE
            }
        }
        companion object {
            private const val MIN_COLUMN = 'A'
            private const val MAX_COLUMN = 'O'
            val COLUM_RANGE = (MIN_COLUMN..MAX_COLUMN).map { it.toString() }
            private const val ERROR_ROW_RANGE = "Row는 ${MIN_COLUMN}~${MAX_COLUMN} 사이여야 합니다."
        }
    }
