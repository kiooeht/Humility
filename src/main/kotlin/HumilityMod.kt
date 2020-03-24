package com.evacipated.cardcrawl.mod.humilty

import basemod.BaseMod
import basemod.ModPanel
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.PostInitializeSubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.ImageMaster
import java.io.IOException
import java.util.*

@SpireInitializer
class HumilityMod :
    PostInitializeSubscriber,
    EditStringsSubscriber {
    companion object Statics {
        val ID: String
        val NAME: String

        //val assets = AssetLoader()

        init {
            var tmpID = "humility"
            var tmpNAME = "Humility"
            val properties = Properties()
            try {
                properties.load(HumilityMod::class.java.getResourceAsStream("/META-INF/" + tmpID + "_version.prop"))
                tmpID = properties.getProperty("id")
                tmpNAME = properties.getProperty("name")
            } catch (e: IOException) {
                e.printStackTrace()
            }
            ID = tmpID
            NAME = tmpNAME
        }

        @Suppress("unused")
        @JvmStatic
        fun initialize() {
            BaseMod.subscribe(HumilityMod())
        }

        fun makeID(id: String) = "$ID:$id"
        fun assetPath(path: String) = "humilityAssets/$path"
    }

    override fun receivePostInitialize() {
        val settingsPanel = ModPanel()

        BaseMod.registerModBadge(
            ImageMaster.loadImage(assetPath("images/modBadge.png")),
            NAME,
            "kiooeht",
            "TODO",
            settingsPanel
        )
    }

    private data class LocResult(val clz: Class<*>, val path: String)

    private fun makeLocPath(language: Settings.GameLanguage, filename: String): String {
        val langPath = when (language) {
            else -> "eng"
        }
        return assetPath("localization/$langPath/$filename.json")
    }

    private fun loadLocFile(language: Settings.GameLanguage, stringType: Class<*>) {
        BaseMod.loadCustomStringsFile(stringType, makeLocPath(language, stringType.simpleName))
    }

    private fun loadLocFiles(language: Settings.GameLanguage) {
        //loadLocFile(language, MonsterStrings::class.java)
        //loadLocFile(language, PowerStrings::class.java)
        //loadLocFile(language, UIStrings::class.java)
    }

    override fun receiveEditStrings() {
        loadLocFiles(Settings.GameLanguage.ENG)
        if (Settings.language != Settings.GameLanguage.ENG) {
            loadLocFiles(Settings.language)
        }
    }
}
