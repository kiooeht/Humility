package com.evacipated.cardcrawl.mod.humilty

import basemod.BaseMod
import basemod.ModLabeledToggleButton
import basemod.ModPanel
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.PostInitializeSubscriber
import com.evacipated.cardcrawl.mod.humilty.helpers.AssetLoader
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.EventStrings
import com.megacrit.cardcrawl.localization.MonsterStrings
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.UIStrings
import java.io.IOException
import java.util.*

@SpireInitializer
class HumilityMod :
    PostInitializeSubscriber,
    EditStringsSubscriber {
    companion object Statics {
        val ID: String
        val NAME: String

        val assets = AssetLoader()

        private var config: SpireConfig? = null

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

            try {
                val defaults = Properties().apply {
                    this["DisableRealTime"] = false
                }
                config = SpireConfig(ID, "config", defaults)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun makeID(id: String) = "$ID:$id"
        fun assetPath(path: String) = "humilityAssets/$path"

        fun disableRealTime(): Boolean {
            return config?.getBool("DisableRealTime") ?: false
        }
    }

    override fun receivePostInitialize() {
        val settingsPanel = ModPanel()

        val disableRealTimeText = CardCrawlGame.languagePack.getUIString(makeID("OptionDisableRealTime"))?.TEXT ?: Array(2) { "[MISSING_TEXT]" }
        ModLabeledToggleButton(
            disableRealTimeText[0],
            disableRealTimeText[1],
            370f, 700f,
            Settings.CREAM_COLOR,
            FontHelper.charDescFont,
            disableRealTime(),
            settingsPanel,
            {},
            { toggle ->
                config?.apply {
                    setBool("DisableRealTime", toggle.enabled)
                    try {
                        save()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        ).also { settingsPanel.addUIElement(it) }

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
        loadLocFile(language, MonsterStrings::class.java)
        loadLocFile(language, PowerStrings::class.java)
        loadLocFile(language, EventStrings::class.java)
        loadLocFile(language, UIStrings::class.java)
    }

    override fun receiveEditStrings() {
        loadLocFiles(Settings.GameLanguage.ENG)
        if (Settings.language != Settings.GameLanguage.ENG) {
            loadLocFiles(Settings.language)
        }
    }
}
