package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.mod.humilty.patches.utils.addPreBattleAction
import com.evacipated.cardcrawl.mod.humilty.powers.SneckoBotPower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.city.Snecko
import javassist.CtBehavior

class SneckoBot {
    @SpirePatch(
        clz = Snecko::class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = [Float::class, Float::class]
    )
    class GainPower {
        companion object {
            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                ctBehavior.addPreBattleAction(::doPreBattleAction)
            }

            @JvmStatic
            fun doPreBattleAction(snecko: Snecko) {
                AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(snecko, snecko, SneckoBotPower(snecko, 1), 1)
                )
            }
        }
    }

    @SpirePatch(
        clz = Snecko::class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = [Float::class, Float::class]
    )
    class PositionDialog {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Snecko, x: Float, y: Float) {
                __instance.dialogX = -70 * Settings.scale
                __instance.dialogY = 40 * Settings.scale
            }
        }
    }
}
