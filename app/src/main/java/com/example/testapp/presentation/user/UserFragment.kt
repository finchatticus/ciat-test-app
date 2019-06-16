package com.example.testapp.presentation.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.BuildConfig
import com.example.testapp.R
import com.example.testapp.domain.model.User
import com.example.testapp.presentation.main.HasToolbarBackButton
import com.example.testapp.presentation.util.PicassoCircleTransform
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.withArguments

class UserFragment : Fragment(), UserContract.View, HasToolbarBackButton {

    companion object {
        private const val ARG_ID_USER = "${BuildConfig.APPLICATION_ID}.ARG_ID_USER"
        fun newInstance(idUser: Int) = UserFragment()
            .withArguments(ARG_ID_USER to idUser)
    }

    private val ui = UserUI()
    private lateinit var parentView: View

    override var presenter: UserContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idUser = arguments?.getInt(ARG_ID_USER, -1) ?: -1
        if (idUser == -1) throw IllegalStateException("${this.javaClass.simpleName} argument idUser is not passed")
        UserPresenter(this, idUser)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentView = view
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

    @SuppressLint("SetTextI18n")
    override fun showUser(user: User) {
        Picasso.get()
            .load(user.avatarUrl)
            .transform(PicassoCircleTransform)
            .into(ui.ivAvatar)
        ui.run {
            tvName.text = "${user.firstName} ${user.lastName} #${user.id}"
            tvEmail.text = user.email
        }
    }

    override fun showNoInternetConnection() {
        parentView.indefiniteSnackbar(R.string.error_no_internet_connection, R.string.retry) {
            presenter?.loadUser()
        }
    }

    override fun showSomeErrorOccurred() {
        parentView.indefiniteSnackbar(R.string.error_undefined, R.string.retry) {
            presenter?.loadUser()
        }
    }


    override fun showDebugMessage(message: String) {
        if (BuildConfig.DEBUG) {
            longToast(message)
        }
    }

}