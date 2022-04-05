package ru.ds.mvp_mvvm

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import ru.ds.mvp_mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivityMainBinding
    private var presenter: LoginContract.Presenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)

        binding.loginButton.setOnClickListener {
            presenter?.onLogin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
    }

    //для сохранения состояния объекта (в данном случае это Presenter()) метод возвращает его последний instance
    private fun restorePresenter(): LoginPresenter {
        val presenter = lastCustomNonConfigurationInstance as? LoginPresenter
        return presenter ?: LoginPresenter()
    }

    //для сохранения состояния объекта (в данном случае это Presenter()) он записывается этим методом
    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    override fun setSuccess() {
        binding.loginButton.visibility = View.GONE
        binding.loginEditText.visibility = View.GONE
        binding.passwordEditText.visibility = View.GONE
        binding.root.setBackgroundResource(R.drawable.ic_open_access)
    }

    override fun setError(error: String) {
        Toast.makeText(this, "Error!!! $error", Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        binding.loginButton.isEnabled = false
        hideKeyboard(this)
    }

    override fun hideProgress() {
        binding.loginButton.isEnabled = true
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
}