package woowacourse.omok.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.OmokApplication
import woowacourse.omok.R
import woowacourse.omok.data.db.OmokDbHelper
import woowacourse.omok.util.setOnSingleClickListener

class LoginActivity : AppCompatActivity() {

    private lateinit var etNickName: EditText
    private lateinit var btnLogin: Button

    private val omokDbHelper = OmokDbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViewId()
        setButtonLoginOnClickListener()
    }

    private fun initViewId() {
        etNickName = findViewById(R.id.et_nick_name)
        btnLogin = findViewById(R.id.btn_login)
    }

    private fun setButtonLoginOnClickListener() {
        btnLogin.setOnSingleClickListener {
            login()
        }
    }

    private fun login() {
        val input = etNickName.text.toString()
        OmokApplication.user =
            omokDbHelper.selectUserByName(input) ?: run {
                omokDbHelper.insertUser(input)
                omokDbHelper.selectUserByName(input)
            }
        initMoveChooseRoomActivity()
    }

    private fun initMoveChooseRoomActivity() {
        val intent = Intent(this, ChooseRoomActivity::class.java)
        startActivity(intent)
        finish()
    }
}
