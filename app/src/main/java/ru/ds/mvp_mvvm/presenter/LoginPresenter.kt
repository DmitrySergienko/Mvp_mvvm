package ru.ds.mvp_mvvm.presenter

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import ru.ds.mvp_mvvm.model.LoginContract
import ru.ds.mvp_mvvm.utils.Constants
import java.lang.Thread.sleep


class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())


    @MainThread
    override fun onAttach(view: LoginContract.View) {
        this.view = view

    }

    @MainThread
    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(1000)
            uiHandler.post {
                view?.showProgress()
                if (checkCredentials(login, password)) {
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