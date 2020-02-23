package dev.itsu.cphoneappmarket

import cn.nukkit.Player
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.data.ApplicationData
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ListResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity
import net.comorevi.cphone.cphone.widget.element.Button

class MyAppsActivity(manifest: ApplicationManifest) : ListActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val listResponse = response as ListResponse
        if (listResponse.buttonIndex == ListResponse.NOT_SELECTED) return ReturnType.TYPE_END
        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle
        title = bundle.getString("m_button_myapps")
        content = bundle.getString("ma_content")
        ApplicationSQLManager.getApplications(bundle.cPhone.player.name).forEach {
            addButton(object : Button(it) {
                override fun onClick(player: Player) {
                    AppDetailsActivity(manifest, ApplicationData.applications[it]!!).start(bundle)
                }
            })
        }
    }

}