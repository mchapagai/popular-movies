package com.mchapagai.klutter.features.about.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.util.Linkify
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.mchapagai.klutter.BuildConfig
import com.mchapagai.klutter.R
import com.mchapagai.klutter.activity.LandingActivity
import com.mchapagai.klutter.common.BaseActivity
import com.mchapagai.klutter.views.MaterialTextView

class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity_container)
        initLicenses()
        initViews()
    }

    private fun initViews() {
        val aboutAppVersion = findViewById<MaterialTextView>(R.id.about_app_version)
        val toolbar = findViewById<Toolbar>(R.id.about_toolbar)
        val collapsingToolbar =
            findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        val appBarLayout = findViewById<AppBarLayout>(R.id.app_bar_layout)

        setSupportActionBar(toolbar)
        if (toolbar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }

        // Implement addOnOffsetChangedListener to show CollapsingToolbarLayout Tile only when
        // collapsed
        appBarLayout.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShown = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = getString(R.string.title_about_activity)
                    isShown = true
                } else if (isShown) {
                    // There should be a space between double quote otherwise it won't work
                    collapsingToolbar.title = " "
                    isShown = false
                }
            }
        })
        toolbar.setNavigationOnClickListener { onBackPressed() }
        aboutAppVersion.visibility = View.VISIBLE
        aboutAppVersion.text = getString(R.string.msg_app_version, BuildConfig.VERSION_NAME)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initLicenses() {
        val licensesLayout = findViewById<LinearLayout>(R.id.licenses_layout)

        val inflater = LayoutInflater.from(this)
        val softwareList = resources.getStringArray(R.array.software_list)
        val licenseList = resources.getStringArray(R.array.license_list)
        licensesLayout.addView(createItemsText(*softwareList))
        for (i in softwareList.indices) {
            licensesLayout.addView(createDivider(inflater, licensesLayout))
            licensesLayout.addView(createHeader(softwareList[i]))
            licensesLayout.addView(createHtmlText(licenseList[i]))
        }
    }

    private fun createHeader(name: String): TextView {
        val s = "<big><b>$name</b></big>"
        return createHtmlText(s, 8)
    }

    private fun createItemsText(vararg names: String): TextView {
        val s = StringBuilder()
        for (name in names) {
            if (s.isNotEmpty()) {
                s.append("<br>")
            }
            s.append("\u2022 ")
            s.append(name)
        }
        return createHtmlText(s.toString(), 8)
    }

    private fun createHtmlText(s: String, margin: Int = 8): TextView {
        val text = TextView(this)
        text.autoLinkMask = Linkify.WEB_URLS or Linkify.EMAIL_ADDRESSES
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            text.text = Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY)
        }
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val marginPx = if (0 < margin) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            margin.toFloat(),
            resources.displayMetrics
        ).toInt() else 0
        layoutParams.setMargins(0, marginPx, 0, marginPx)
        text.layoutParams = layoutParams
        return text
    }

    private fun createDivider(inflater: LayoutInflater, parent: ViewGroup?): View {
        return inflater.inflate(R.layout.divider, parent, false)
    }


    /**
     * Called when the activity has detected the user press back navigation key
     */
    override fun onBackPressed() {
        val intent = Intent(this, LandingActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}