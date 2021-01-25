package com.evacipated.cardcrawl.mod.humilty.patches

import basemod.ReflectionHacks
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.modthespire.lib.*
import com.google.gson.annotations.SerializedName
import com.megacrit.cardcrawl.metrics.Metrics
import com.megacrit.cardcrawl.monsters.MonsterGroup
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen
import com.megacrit.cardcrawl.screens.stats.RunData
import javassist.CtBehavior

class RunHistoryIndicator {
    companion object {
        private const val COLOR = RenderIndicator.COLOR
    }

    @SpirePatch(
        clz = RunHistoryScreen::class,
        method = "renderRunHistoryScreen"
    )
    class Display {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class,
                localvars = ["specialModeText"]
            )
            fun Insert(__instance: RunHistoryScreen, sb: SpriteBatch, ___viewedRun: RunData, @ByRef specialModeText: Array<String>) {
                if (RunDataField.is_humility.get(___viewedRun)) {
                    if (specialModeText[0].isEmpty()) {
                        specialModeText[0] = " ([$COLOR]Humility[])"
                    } else {
                        specialModeText[0] = specialModeText[0].substringBefore(')') + " + [$COLOR]Humility[])" + specialModeText[0].substringAfter(')', "")
                    }
                }
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.FieldAccessMatcher(RunData::class.java, "victory")
                return LineFinder.findInOrder(ctBehavior, finalMatcher)
            }
        }
    }

    @SpirePatch(
        clz = RunData::class,
        method = SpirePatch.CLASS
    )
    class RunDataField {
        companion object {
            @JvmField
            @SerializedName("is_humility")
            val is_humility: SpireField<Boolean> = SpireField { false }
        }
    }

    @SpirePatch(
        clz = Metrics::class,
        method = "gatherAllData"
    )
    class SaveIsHumility {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Metrics, death: Boolean, trueVictor: Boolean, monsters: MonsterGroup?) {
                val addData = ReflectionHacks.privateMethod(Metrics::class.java, "addData", Any::class.java, Any::class.java)
                addData.invoke<Unit>(__instance, "is_humility", true)
            }
        }
    }
}
