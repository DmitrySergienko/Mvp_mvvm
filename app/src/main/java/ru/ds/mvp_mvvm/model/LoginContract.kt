package ru.ds.mvp_mvvm.model

import androidx.annotation.MainThread

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
        @MainThread
        fun showSymbols()

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