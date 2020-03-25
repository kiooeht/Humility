package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import basemod.ReflectionHacks
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches
import com.megacrit.cardcrawl.actions.common.SetMoveAction
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_L
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_L

class SlimeSplitThreshold {
    @SpirePatches(
        SpirePatch(
            clz = SlimeBoss::class,
            method = "damage"
        ),
        SpirePatch(
            clz = SpikeSlime_L::class,
            method = "damage"
        ),
        SpirePatch(
            clz = AcidSlime_L::class,
            method = "damage"
        )
    )
    class SlimeBossSplit {
        companion object {
            @JvmStatic
            fun Postfix(__instance: AbstractMonster, info: DamageInfo, ___SPLIT_NAME: String) {
                if (!__instance.isDying && __instance.nextMove != 3.toByte()
                    && __instance.currentHealth > __instance.maxHealth / 2f // don't trigger if less than 50% HP, base game covers that
                    && __instance.currentHealth <= __instance.maxHealth * 0.75f // trigger if less than 75% HP
                    && !getSplitTriggered(__instance)
                ) {
                    __instance.setMove(
                        ___SPLIT_NAME,
                        3.toByte(),
                        AbstractMonster.Intent.UNKNOWN
                    )
                    __instance.createIntent()
                    AbstractDungeon.actionManager.addToBottom(TextAboveCreatureAction(__instance, TextAboveCreatureAction.TextType.INTERRUPTED))
                    AbstractDungeon.actionManager.addToBottom(SetMoveAction(__instance, ___SPLIT_NAME, 3.toByte(), AbstractMonster.Intent.UNKNOWN))
                    setSplitTriggered(__instance, true)
                }
            }

            private fun getSplitTriggered(__instance: AbstractMonster): Boolean {
                if (__instance is SpikeSlime_L) {
                    return ReflectionHacks.getPrivate(__instance, SpikeSlime_L::class.java, "splitTriggered") as Boolean
                }
                if (__instance is AcidSlime_L) {
                    return ReflectionHacks.getPrivate(__instance, AcidSlime_L::class.java, "splitTriggered") as Boolean
                }
                return false
            }

            private fun setSplitTriggered(__instance: AbstractMonster, value: Boolean) {
                if (__instance is SpikeSlime_L) {
                    ReflectionHacks.setPrivate(__instance, SpikeSlime_L::class.java, "splitTriggered", value)
                }
                if (__instance is AcidSlime_L) {
                    ReflectionHacks.setPrivate(__instance, AcidSlime_L::class.java, "splitTriggered", value)
                }
            }
        }
    }
}
