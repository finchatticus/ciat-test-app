package com.example.testapp.presentation.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.BuildConfig
import com.example.testapp.R
import com.example.testapp.domain.model.User
import com.example.testapp.presentation.user.UserFragment
import com.example.testapp.presentation.users.rv.UsersAdapter
import com.example.testapp.presentation.util.getInitialRecyclerViewLoadCount
import com.example.testapp.presentation.util.replaceFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.support.v4.longToast


class UsersFragment : Fragment(), UsersContract.View {

    private val ui = UsersUI()
    private val usersAdapter = UsersAdapter()
    private var initialUsersCount = 0
    private lateinit var parentView: View


    override var presenter: UsersContract.Presenter? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        UsersPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentView = view
        usersAdapter.apply {
            onLoadMore = {
                presenter?.loadNextUsers()
            }
            onUserClickListener = {
                presenter?.openUserScreen(it.id)
            }
        }
        ui.rvUsers.adapter = usersAdapter
        presenter?.loadNextUsers()
    }

    override fun onResume() {
        super.onResume()
        presenter?.start()
    }

    override fun onDestroyView() {
        presenter?.viewDestroyed(false)
        super.onDestroyView()
    }

    override fun showLoading() {
        ui.pb.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        ui.pb.visibility = View.GONE
    }

    override fun showUsers(items: List<User>) {
        usersAdapter.addItems(items, initialUsersCount)
        if (initialUsersCount == 0) {
            ui.rvUsers.post {
                initialUsersCount = ui.rvUsers.getInitialRecyclerViewLoadCount()
                presenter?.loadNextUsers()
                usersAdapter.setLoading(true)
            }
        } else {
            usersAdapter.setLoading(false)
        }
    }

    override fun showNoInternetConnection() {
        parentView.indefiniteSnackbar(R.string.error_no_internet_connection, R.string.retry) {
            presenter?.loadNextUsers()
        }
    }

    override fun showSomeErrorOccurred() {
        parentView.indefiniteSnackbar(R.string.error_undefined, R.string.retry) {
            presenter?.loadNextUsers()
        }
    }

    override fun showUserScreen(idUser: Int) {
        fragmentManager?.replaceFragment(UserFragment.newInstance(idUser))
    }

    override fun showDebugMessage(message: String) {
        if (BuildConfig.DEBUG) {
            longToast(message)
        }
    }

}