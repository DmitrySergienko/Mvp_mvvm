package ru.ds.mvp_mvvm.ui.login

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import ru.ds.mvp_mvvm.data.WebLoginApiImpl
import ru.ds.mvp_mvvm.domain.LoginApp
import ru.ds.mvp_mvvm.utils.Constants


class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private val isSuccess: Boolean = true
    private val error: String =""
    private val api: LoginApp = WebLoginApiImpl()


    @MainThread
    override fun onAttach(view: LoginContract.View) {
        this.view = view

    }

    @MainThread
    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            val success = api.login(login,password)

            uiHandler.post {
                view?.showProgress()
                if (success) {
                    view?.setSuccess()
                    view?.hideProgress()

                } else {
                    view?.setError(Constants.WRONG_PASSWORD)

                }
            }

        }.start()
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