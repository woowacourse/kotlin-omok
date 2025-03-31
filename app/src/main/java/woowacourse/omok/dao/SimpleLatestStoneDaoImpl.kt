package woowacourse.omok.dao

class SimpleLatestStoneDaoImpl(dbHelper: OmokDbHelper) : SimpleLatestStoneDao(dbHelper) {
    override val nicknameColumn: String
        get() = OmokDbHelper.NICKNAME_COLUMN
    override val latestStoneColumn: String
        get() = OmokDbHelper.LATEST_PLACE_COLUMN
    override val tableName: String
        get() = OmokDbHelper.LATEST_PLACE_TABLE
}
