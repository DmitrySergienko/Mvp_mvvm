package ru.ds.mvp_mvvm.ui.login

import androidx.annotation.MainThread
import ru.ds.mvp_mvvm.domain.LoginUseCase
import ru.ds.mvp_mvvm.utils.Constants


class LoginPresenter(private val loginUseCase:LoginUseCase) : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private val isSuccess: Boolean = true
    private val error: String =""

    @MainThread
    override fun onAttach(view: LoginContract.View) {
        this.view = view

    }

    @MainThread
    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        loginUseCase.login(login, password) { result ->
            view?.hideProgress()
            view?.showProgress()

            if (result) {
                view?.setSuccess()
                view?.hideProgress()
            } else {
                view?.setError(Constants.WRONG_PASSWORD)
            }
        }
    }

    @MainThread
    override fun onCredentialsChanged() {
        TODO("Not yet implemented")
    }

    @MainThread
    private fun checkCredentials(login: String, password: String): Boolean {
        return login == password
    }
}