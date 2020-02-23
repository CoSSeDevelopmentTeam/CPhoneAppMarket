package dev.itsu.cphoneappmarket

import dev.itsu.cphoneappmarket.search.SearchActivity
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ListResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity
import net.comorevi.cphone.cphone.widget.element.Button

class MainActivity(manifest: ApplicationManifest) : ListActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        when((response as ListResponse).buttonIndex) {
            0 -> SearchActivity(manifest).start(bundle)
            1 -> NewAppsActivity(manifest).start(bundle)
            2 -> MyAppsActivity(manifest).start(bundle)
            3 -> WhatIsActivity(manifest).start(bundle)
            else -> return ReturnType.TYPE_END
        }
        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        this.title = bundle.getString("m_title")
        this.content = bundle.getString("m_description")

        this.addButton(Button(bundle.getString("m_button_search")))
        this.addButton(Button(bundle.getString("m_button_newapps")))
        this.addButton(Button(bundle.getString("m_button_myapps")))
        this.addButton(Button(bundle.getString("m_button_whatis")))
    }

}