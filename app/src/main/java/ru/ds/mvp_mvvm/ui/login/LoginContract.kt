package ru.ds.mvp_mvvm.ui.login

import androidx.annotation.MainThread

interface LoginContract {

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
        @MainThread
        fun onAttach(view: View)
        @MainThread
        fun onLogin(login: String, password: String)
        @MainThread
        fun onCredentialsChanged()
        @MainThread
        private fun checkCredentials(login: String, password: String): Boolean {
            return login == password
        }

    }
}