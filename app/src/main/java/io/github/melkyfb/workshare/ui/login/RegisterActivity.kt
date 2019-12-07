package io.github.melkyfb.workshare.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import io.github.melkyfb.workshare.R
import io.github.melkyfb.workshare.data.DBHelper
import io.github.melkyfb.workshare.data.LoginDataSource
import io.github.melkyfb.workshare.data.model.LoggedInUser
import java.util.*

class RegisterActivity : AppCompatActivity() {

    var edtDisplayName: EditText? = null
    var edtUsername: EditText? = null
    var edtPassword: EditText? = null
    var edtPasswordConfirm: EditText? = null
    var btnRegister: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        this.edtDisplayName = this.findViewById(R.id.edt_register_display_name) as EditText
        this.edtUsername = this.findViewById(R.id.edt_register_username) as EditText
        this.edtPassword = this.findViewById(R.id.edt_register_password) as EditText
        this.edtPasswordConfirm = this.findViewById(R.id.edt_register_password_confirm) as EditText
        this.btnRegister = this.findViewById(R.id.btn_register)
        this.btnRegister!!.setOnClickListener({
            var displayName = this.edtDisplayName!!.text.toString()
            var username = this.edtUsername!!.text.toString()
            var password = this.edtPassword!!.text.toString()
            var passwordConfirm = this.edtPasswordConfirm!!.text.toString()
            if (isEmpty(username)) {
                this.edtUsername!!.requestFocus()
                this.edtUsername!!.setError("Usuario deve ser preenchido")
            } else if (isEmpty(password)) {
                this.edtPassword!!.requestFocus()
                this.edtPassword!!.setError("Senha deve ser digitada")
            } else if (password.count() <= 5) {
                this.edtPassword!!.requestFocus()
                this.edtPassword!!.setError("Senha deve conter mais de 5 caracteres")
            } else if (isEmpty(passwordConfirm)) {
                this.edtPasswordConfirm!!.requestFocus()
                this.edtPasswordConfirm!!.setError("Confirmação de senha deve ser digitada")
            } else if (!password.equals(passwordConfirm)) {
                this.edtPassword!!.requestFocus()
                this.edtPassword!!.setError("Senha e Confirmação de Senha não são iguais")
            } else {
                var user = LoggedInUser(
                    UUID.randomUUID().toString(),
                    displayName,
                    username,
                    password
                )
                var dbHelper = DBHelper(this)
                var loginDataSource = LoginDataSource(dbHelper)
                if (loginDataSource.findUserExists(username)) {
                    this.edtUsername!!.requestFocus()
                    this.edtUsername!!.setError("Este nome de usuário já existe, tente fazer Login ou escolha outro nome de usuário")
                } else {
                    var result = loginDataSource.insertUser(user)
                    if (result > 0) {
                        var i = Intent()
                        var b = bundleOf("user" to user)
                        i.putExtra("bundle", b)
                        setResult(Activity.RESULT_OK, i)
                    } else {
                        Toast.makeText(this, "Não foi possível salvar o usuario", Toast.LENGTH_LONG).show()
                    }
                    finish()
                }
            }
        })
    }
}
