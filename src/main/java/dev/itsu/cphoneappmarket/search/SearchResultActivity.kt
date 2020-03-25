package dev.itsu.cphoneappmarket.search

import cn.nukkit.Player
import dev.itsu.cphoneappmarket.AppDetailsActivity
import dev.itsu.cphoneappmarket.MainActivity
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.data.ApplicationData
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ListResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity
import net.comorevi.cphone.cphone.widget.element.Button

class SearchResultActivity(manifest: ApplicationManifest, private val keyword: String) : ListActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val listResponse = response as ListResponse
        if (listResponse.buttonIndex == ListResponse.NOT_SELECTED) return ReturnType.TYPE_END
        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle

        val result = ApplicationData.applications.filter { it.key.toLowerCase().contains(keyword.toLowerCase()) }
        if (result.isEmpty()) {
            createEmpty()
        } else {
            create(result)
        }
    }

    private fun create(result: Map<String, ApplicationManifest>) {
        this.title = "${bundle.getString("sr_title")} (${result.size}${bundle.getString("sr_unit")})"
        val userData = ApplicationSQLManager.getApplications(bundle.cPhone.player.name)
        result.forEach {
            this.addButton(object : Button(it.value.getTitleByRegion(bundle.cPhone.region) + "\n" + (if (userData.contains(it.key)) bundle.getString("sr_installed") else bundle.getString("sr_not_installed"))) {
                override fun onClick(player: Player) {
                    AppDetailsActivity(manifest, it.value).start(bundle)
                }
            })
        }
    }

    private fun createEmpty() {
        this.title = "${bundle.getString("sr_title")} (0${bundle.getString("sr_unit")})"
        this.addButton(object : Button(bundle.getString("back_to_top")) {
            override fun onClick(player: Player) {
                MainActivity(manifest).start(bundle)
            }
        })
    }

}