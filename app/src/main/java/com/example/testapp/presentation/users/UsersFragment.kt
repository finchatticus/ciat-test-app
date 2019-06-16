package com.example.testapp.presentation.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.BuildConfig
import com.example.testapp.R
import com.example.testapp.domain.model.User
import com.example.testapp.presentation.users.rv.UsersAdapter
import com.example.testapp.presentation.util.getInitialRecyclerViewLoadCount
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.support.v4.longToast


class UsersFragment : Fragment(), UsersContract.View {

    private val ui = UsersUI()
    private val usersAdapter = UsersAdapter()
    private var initialUsersCount = 0
    private var isLoading = false
    private var usersSize = 0
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
        ui.rvUsers.apply {
            addOnScrollListener(recyclerViewOnScrollListener)
            adapter = usersAdapter
        }
        presenter?.loadNextUsers()
    }

    override fun onResume() {
        super.onResume()
        presenter?.start()
    }

    override fun onDestroyView() {
        ui.rvUsers.removeOnScrollListener(recyclerViewOnScrollListener)
        presenter?.viewDetached(false)
        super.onDestroyView()
    }

    override fun showLoading() {
        ui.pb.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        ui.pb.visibility = View.GONE
    }

    override fun showUsers(items: List<User>) {
        usersSize += items.size
        usersAdapter.addItems(items, initialUsersCount)
        if (initialUsersCount == 0) {
            ui.rvUsers.post {
                initialUsersCount = ui.rvUsers.getInitialRecyclerViewLoadCount()
                presenter?.loadNextUsers()
                isLoading = true
            }
        } else {
            isLoading = false
        }
    }

    override fun showNoInternetConnection() {
        parentView.indefiniteSnackbar(R.string.error_no_internet_connection, R.string.retry) {
            presenter?.loadNextUsers()
        }
    }

    override fun showSomeErrorOccured() {
        parentView.indefiniteSnackbar(R.string.error_undefined, R.string.retry) {
            presenter?.loadNextUsers()
        }
    }

    override fun showUserScreen(idUser: Int) {
        showDebugMessage("id: $idUser")
    }

    override fun showDebugMessage(message: String) {
        if (BuildConfig.DEBUG) {
            longToast(message)
        }
    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            if (!isLoading && linearLayoutManager.findLastCompletelyVisibleItemPosition() == usersSize - 1) {
                presenter?.loadNextUsers()
                isLoading = true
            }
        }
    }

}