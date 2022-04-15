package ru.ds.mvp_mvvm.ui.login

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.ds.mvp_mvvm.R
import ru.ds.mvp_mvvm.app
import ru.ds.mvp_mvvm.databinding.ActivityMainBinding


const val SHOW_SYMBOL = "Show"
const val HIDE_SYMBOL = "Hide"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var viewModel: LoginContract.ViewModel? = null
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    private lateinit var compositdisposable: CompositeDisposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositdisposable = CompositeDisposable()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = restoreViewModel()

        showSymbols()//show and hide symbols

        binding.loginButton.setOnClickListener {
            viewModel?.onLogin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        viewModel?.let {
            compositdisposable.add(
                it.shouldShowProgress
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { shouldShow ->
                    if (shouldShow == true) {
                        binding.loginButton.isEnabled = false
                        hideKeyboard(this)
                        binding.progressLoadingLayout.visibility = View.VISIBLE
                    } else {
                        binding.loginButton.isEnabled = true
                        binding.progressLoadingLayout.visibility = View.GONE
                    }
                })
        }

        viewModel?.isSuccess?.subscribe(handler) {
            if (it == true)
                setSuccess()
        }

        viewModel?.errorText?.subscribe(handler) {
            it?.let {
                val success = this.viewModel?.isSuccess?.value
                if (success == false) {
                    this.setError(it)
                }
            }
        }
    }

    //для сохранения состояния объекта (в данном случае это Presenter()) метод возвращает его последний instance
    private fun restoreViewModel(): LoginViewModel {
        val viewModel = lastCustomNonConfigurationInstance as? LoginViewModel

        return viewModel ?: LoginViewModel(app.loginUseCase)
    }

    //для сохранения состояния объекта (в данном случае это Presenter()) он записывается этим методом
    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return viewModel
    }


    private fun setSuccess() {
        binding.loginButton.visibility = View.GONE
        binding.loginEditText.visibility = View.GONE
        binding.passwordEditText.visibility = View.GONE
        binding.root.setBackgroundResource(R.drawable.ic_open_access)
    }

    private fun setError(error: String) {
        Toast.makeText(this, "Error!!! $error", Toast.LENGTH_LONG).show()
    }

    /* override fun showProgress() {
         binding.loginButton.isEnabled = false
         hideKeyboard(this)
         binding.progressLoadingLayout.visibility = View.VISIBLE
     }

     override fun hideProgress() {
         binding.loginButton.isEnabled = true
         binding.progressLoadingLayout.visibility = View.GONE
     }*/

    private fun showSymbols() {
        binding.showHideBtn.setOnClickListener {
            if (binding.showHideBtn.text.toString().equals("Show")) {
                binding.passwordEditText.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.showHideBtn.text = HIDE_SYMBOL
            } else {
                binding.passwordEditText.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.showHideBtn.text = SHOW_SYMBOL
            }
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.isSuccess?.unsubscribeAll()
        viewModel?.errorText?.unsubscribeAll()
        compositdisposable.dispose()

    }
}