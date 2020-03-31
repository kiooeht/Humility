package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import com.evacipated.cardcrawl.mod.humilty.powers.DecasProtectionPower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.beyond.Deca
import com.megacrit.cardcrawl.monsters.beyond.Donu

class DonuDecasProtection {
    @SpirePatch(
        clz = Deca::class,
        method = "usePreBattleAction"
    )
    class Add {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Deca) {
                AbstractDungeon.getMonsters().monsters
                    .filterNot { it == __instance || it.isDeadOrEscaped }
                    .filterIsInstance<Donu>()
                    .forEach {
                        AbstractDungeon.actionManager.addToBottom(
                            ApplyPowerAction(
                                it,
                                __instance,
                                DecasProtectionPower(it, __instance)
                            )
                        )
                    }
            }
        }
    }

    @SpirePatch(
        clz = Deca::class,
        method = "die"
    )
    class Remove {
        companion object {
            @JvmStatic
            fun Prefix(__instance: Deca) {
                AbstractDungeon.getMonsters().monsters
                    .filterNot { it.isDeadOrEscaped }
                    .forEach {
                        AbstractDungeon.actionManager.addToBottom(
                            RemoveSpecificPowerAction(it, __instance, DecasProtectionPower.POWER_ID)
                        )
                    }
            }
        }
    }
}
