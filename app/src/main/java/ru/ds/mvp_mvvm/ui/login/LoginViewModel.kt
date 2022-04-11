package ru.ds.mvp_mvvm.ui.login

import androidx.annotation.MainThread
import ru.ds.mvp_mvvm.domain.LoginUseCase
import ru.ds.mvp_mvvm.utils.Constants
import ru.ds.mvp_mvvm.utils.Publisher

const val WRONG_PASSWORD = "Wrong Password"
const val SHOW_SYMBOL = "Show"
const val HIDE_SYMBOL = "Hide"

class LoginViewModel(
    private val loginUseCase: LoginUseCase

) : LoginContract.ViewModel {

    override val shouldShowProgress: Publisher<Boolean> = Publisher()
    override val isSuccess: Publisher<Boolean> = Publisher()
    override val errorText: Publisher<String?> = Publisher()

    @MainThread
    override fun onLogin(login: String, password: String) {
        shouldShowProgress.post(true)


        loginUseCase.login(login, password) { result ->
            shouldShowProgress.post(false)

            if (result) {
                isSuccess.post(true)
                errorText.post("!")

            } else {
                isSuccess.post(false)
                errorText.post(WRONG_PASSWORD)
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