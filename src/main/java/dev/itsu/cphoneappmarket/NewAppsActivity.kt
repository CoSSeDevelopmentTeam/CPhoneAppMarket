package dev.itsu.cphoneappmarket

import cn.nukkit.Player
import net.comorevi.cphone.cphone.application.ApplicationManifest
import net.comorevi.cphone.cphone.data.ApplicationData
import net.comorevi.cphone.cphone.model.Bundle
import net.comorevi.cphone.cphone.model.ListResponse
import net.comorevi.cphone.cphone.model.Response
import net.comorevi.cphone.cphone.widget.activity.ReturnType
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity
import net.comorevi.cphone.cphone.widget.element.Button
import net.comorevi.cphone.presenter.SharingData
import java.io.File

class NewAppsActivity(manifest: ApplicationManifest) : ListActivity(manifest) {

    private lateinit var bundle: Bundle

    override fun onStop(response: Response): ReturnType {
        val listResponse = response as ListResponse
        if (listResponse.buttonIndex == ListResponse.NOT_SELECTED) return ReturnType.TYPE_END
        return ReturnType.TYPE_CONTINUE
    }

    override fun onCreate(bundle: Bundle) {
        this.bundle = bundle

        title = bundle.getString("m_button_newapps")
        content = bundle.getString("na_content")

        val dir = File("${SharingData.server.pluginPath}/CPhone/app")
        var index = 0
        (dir.listFiles() ?: return)
                .toList()
                .stream()
                .sorted { file, file2 -> if (file.lastModified() >= file2.lastModified()) 1 else -1 }
                .forEach {
                    if (index > 2) return@forEach

                    val app = ApplicationData.applications[it.nameWithoutExtension] ?: return@forEach

                    addButton(object : Button(app.getTitleByRegion(bundle.cPhone.region)) {
                        override fun onClick(player: Player) {
                            AppDetailsActivity(manifest, app).start(bundle)
                        }
                    })
                    index++
                }

        addButton(object : Button(bundle.getString("back_to_top")) {
            override fun onClick(player: Player) {
                MainActivity(manifest).start(bundle)
            }
        })
    }
}