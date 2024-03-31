package woowacourse.omok.domain.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.domain.model.database.OmokTurnContract.OMOK_TURN_DATABASE_NAME
import woowacourse.omok.domain.model.database.OmokTurnContract.OMOK_TURN_DATABASE_VERSION

class OmokTurnDbHelper(context: Context) :
    SQLiteOpenHelper(context, OMOK_TURN_DATABASE_NAME, null, OMOK_TURN_DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(OmokTurnContract.CREATE_OMOK_TURN_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db?.execSQL(OmokTurnContract.DROP_OMOK_TURN_TABLE)
        onCreate(db)
    }
}
