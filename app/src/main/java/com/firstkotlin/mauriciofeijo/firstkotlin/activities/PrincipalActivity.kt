package com.firstkotlin.mauriciofeijo.firstkotlin.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.Toast
import com.firstkotlin.mauriciofeijo.firstkotlin.fragments.RepositoriesFragment
import com.firstkotlin.mauriciofeijo.firstkotlin.models.Repository
import com.firstkotlin.mauriciofeijo.firstkotlin.models.User
import com.firstkotlin.mauriciofeijo.firstkotlin.services.RepositoriesService
import com.firstkotlin.mauriciofeijo.firstkotlin.services.UserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PrincipalActivity : AppCompatActivity() {
    private lateinit var repositories: ArrayList<Repository>
    private lateinit var user: User
    private var firstInfoFound: Boolean = false;

    private var repositoryDisposable: Disposable? = null
    private var userDisposable: Disposable? = null

    private val repositoriesServe by lazy {
        RepositoriesService.create()
    }
    private val UserServe by lazy {
        UserService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = FrameLayout(this)
        layout.id = android.R.id.content

        setContentView(layout)

        beginSearch("mfeijo96")
    }

    override fun onPause() {
        super.onPause()

        repositoryDisposable?.dispose()
        userDisposable?.dispose()
    }

    private fun beginSearch(userName: String) {
        userDisposable = UserServe.getUser(userName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> updateList(result) },
                        { error -> showError(error.message) }
                )

        repositoryDisposable =
            repositoriesServe.getRepositories(userName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result -> updateList(result) },
                            { error -> showError(error.message) }
                    )
    }

    private fun updateList(returnValue: Any) {
        if (returnValue is List<*>) {
            repositories = returnValue as ArrayList<Repository>
        } else if (returnValue is User) {
            user = returnValue
        }

        if (firstInfoFound) {
            supportFragmentManager
                    .beginTransaction()
                    .add(android.R.id.content, RepositoriesFragment.newInstance(user, repositories))
                    .commit()
        } else {
            firstInfoFound = true;
        }
    }

    private fun showError(error: String?) {
        error?.let {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }
}