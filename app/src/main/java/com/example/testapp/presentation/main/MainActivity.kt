package com.example.testapp.presentation.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.testapp.R
import com.example.testapp.presentation.user.UserFragment
import com.example.testapp.presentation.users.UsersFragment
import com.example.testapp.presentation.util.addFragment
import com.example.testapp.presentation.util.currentFragment
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    private val ui = MainUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener(this)
        ui.setContentView(this)
        setSupportActionBar(ui.toolbar)
        addFragment(useFragment = {
            setupToolbar(it)
        }) { UsersFragment() }
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            super.onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onBackStackChanged() {
        setupToolbar(currentFragment() ?: return)
    }

    override fun onDestroy() {
        supportFragmentManager.removeOnBackStackChangedListener(this)
        super.onDestroy()
    }

    private fun setupToolbar(currentFragment: Fragment) {
        when (currentFragment) {
            is UserFragment -> supportActionBar?.title = getString(R.string.user_title)
            is UsersFragment -> supportActionBar?.title = getString(R.string.user_list_title)
        }

        when (currentFragment) {
            is HasToolbarBackButton -> supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
            else -> supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(false)
                setDisplayShowHomeEnabled(false)
            }
        }
    }

}
