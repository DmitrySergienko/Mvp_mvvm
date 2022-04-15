package ru.ds.mvp_mvvm.ui.login

import androidx.annotation.MainThread
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.ds.mvp_mvvm.domain.LoginUseCase
import ru.ds.mvp_mvvm.utils.Publisher

const val WRONG_PASSWORD = "Wrong Password"


class LoginViewModel(
    private val loginUseCase: LoginUseCase

) : LoginContract.ViewModel {
    private val _shouldShowProgress = BehaviorSubject.create<Boolean>()// сюда можно вносить объекты и затем их получать!!?
    override val shouldShowProgress: Observable<Boolean> =_shouldShowProgress
    override val isSuccess: Publisher<Boolean> = Publisher()
    override val errorText: Publisher<String?> = Publisher()

    @MainThread
    override fun onLogin(login: String, password: String) {
       _shouldShowProgress.onNext(true)

        loginUseCase.login(login, password).subscribe { result ->
            _shouldShowProgress.onNext(false)

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