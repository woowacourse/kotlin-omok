package woowacourse.omok.dao

class SimpleOmokPlaceDaoImpl(dbHelper: OmokDbHelper) : SimpleOmokPlaceDao(dbHelper) {
    override val boardColumn: String
        get() = OmokDbHelper.BOARD_COLUMN
    override val nicknameColumn: String
        get() = OmokDbHelper.NICKNAME_COLUMN
    override val tableName: String
        get() = OmokDbHelper.OMOK_BOARD_TABLE
}
