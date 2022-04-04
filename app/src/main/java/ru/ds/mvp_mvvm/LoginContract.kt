package ru.ds.mvp_mvvm

import androidx.annotation.MainThread
import java.util.logging.Handler

class LoginContract {

    interface View {
        @MainThread
        fun setSuccess()
        @MainThread
        fun setError(error: String)
        @MainThread
        fun showProgress()
        @MainThread
        fun hideProgress()

    }

    interface Presenter {
        fun onAttach(view: View)
        fun onLogin(login: String, password: String)
        fun onCredentialsChanged()
        private fun checkCredentials(login: String, password: String): Boolean {
            return login == password
        }


    }
}