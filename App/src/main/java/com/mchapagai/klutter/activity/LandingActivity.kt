package com.mchapagai.klutter.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.mchapagai.klutter.R
import com.mchapagai.klutter.features.about.activity.AboutActivity
import com.mchapagai.klutter.dialog.MaterialDialogFragment
import com.mchapagai.klutter.dialog.MovieDialogBuilder
import com.mchapagai.klutter.utils.PreferencesHelper
import com.mchapagai.klutter.views.MaterialCircleImageView
import com.mchapagai.klutter.views.MaterialTextView
import dagger.android.AndroidInjection
import java.io.Serializable

class LandingActivity : AppCompatActivity() {

    private lateinit var preferencesUtils: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.landing_activity_layout_container)
        preferencesUtils = PreferencesHelper(this)
        initViews()
    }

    private fun initViews() {
        val launchPopularMovies = findViewById<MaterialTextView>(R.id.landing_popular_movies)
        val launchPopularShows = findViewById<MaterialTextView>(R.id.landing_popular_shows)
        val launchInfoScreen = findViewById<ConstraintLayout>(R.id.landing_about_page_layout)
        val launchProfileScreen = findViewById<MaterialCircleImageView>(R.id.landing_user_profile)

        launchPopularMovies.setOnClickListener { view ->
            startActivity(
                Intent(
                    view.context,
                    DiscoverMoviesActivity::class.java
                )
            )
        }
        animateCircle(launchPopularMovies)

        launchPopularShows.setOnClickListener { view ->
            startActivity(
                Intent(
                    view.context,
                    DiscoverOnTheAirActivity::class.java
                )
            )
        }
        animateCircle(launchPopularShows)

        launchInfoScreen.setOnClickListener { view ->
            startActivity(
                Intent(
                    view.context,
                    AboutActivity::class.java
                )
            )
        }

        // TODO accessing Java based dialog is throwing an error:
        // TODO @JvmFiled cannot be applied to lateinit
        launchProfileScreen.setOnClickListener { view ->
            // Check to see if user is logged in, if not show a dialog to prompt user to login
            if (!preferencesUtils.isSignedIn) {
                val builder = MovieDialogBuilder()
                    .setMessage(resources.getString(R.string.prompt_to_login_message))
                    .setTitle(resources.getString(R.string.prompt_to_login_title))
                    .setLayoutResId(R.layout.confirmation_dialog)
                    .setPositiveButtonText(
                        resources.getString(R.string.material_dialog_ok)
                    )
                    .setNegativeButtonText(
                        resources.getString(R.string.material_dialog_cancel)
                    )
                    .setCustomButton(true)
                val materialDialogFragment = MaterialDialogFragment.showDialog(
                    builder, this
                )
                materialDialogFragment.setOnDialogClickListener(
                    object : MaterialDialogFragment.OnDialogClickListener {
                        override fun onPositiveButtonClicked(data: Serializable, tag: String) {
                            val intent = Intent(
                                view.context,
                                LoginActivity::class.java
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                        }

                        override fun onNegativeButtonClicked(tag: String) {}
                    })
            } else {
                preferencesUtils.isSignedIn
                val intent = Intent(view.context, ProfileActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun animateCircle(textView: TextView?) {
        val anim = TranslateAnimation(-25.0f, 25.0f, -25.0f, 25.0f)
        anim.duration = 600
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.isFillEnabled = true
        anim.fillAfter = true
        anim.repeatMode = Animation.RELATIVE_TO_SELF
        anim.repeatCount = 1
        textView!!.startAnimation(anim)
    }
}