package io.github.melkyfb.workshare.ui.login

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.melkyfb.workshare.data.DBHelper
import io.github.melkyfb.workshare.data.LoginDataSource
import io.github.melkyfb.workshare.data.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(val dbHelper: DBHelper) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource(this.dbHelper)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
