package woowacourse.omok.database.user

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.database.user.UserContract.USER_DATABASE_NAME
import woowacourse.omok.database.user.UserContract.USER_DATABASE_VERSION

class UserDbHelper(context: Context) : SQLiteOpenHelper(context, USER_DATABASE_NAME, null, USER_DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(UserContract.CREATE_USER_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db?.execSQL(UserContract.DROP_USER_TABLE)
        onCreate(db)
    }
}
