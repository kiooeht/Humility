package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import com.evacipated.cardcrawl.mod.humilty.patches.utils.addPreBattleAction
import com.evacipated.cardcrawl.mod.humilty.powers.VenomStrikesPower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.beyond.Reptomancer
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger
import javassist.CtBehavior

class ReptomancerVenom {
    @SpirePatch(
        clz = Reptomancer::class,
        method = "usePreBattleAction"
    )
    class AddVenomToReptomancer {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Reptomancer) {
                AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, VenomStrikesPower(__instance, 2), 2))
            }
        }
    }

    @SpirePatch(
        clz = SnakeDagger::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class AddVenomToDaggers {
        companion object {
            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                ctBehavior.addPreBattleAction(::doPreBattleAction)
            }

            @JvmStatic
            fun doPreBattleAction(__instance: SnakeDagger) {
                AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, VenomStrikesPower(__instance, 2), 2))
            }
        }
    }
}
