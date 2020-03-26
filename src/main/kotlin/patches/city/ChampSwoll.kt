package com.evacipated.cardcrawl.mod.humilty.patches.city

import basemod.ReflectionHacks
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo
import com.megacrit.cardcrawl.monsters.city.Champ
import com.megacrit.cardcrawl.powers.StrengthPower

class ChampSwoll {
    @SpirePatch(
        clz = Champ::class,
        method = "usePreBattleAction"
    )
    class StartingStrength {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Champ) {
                AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, StrengthPower(__instance, 3), 3))
            }
        }
    }

    @SpirePatch(
        clz = Champ::class,
        method = "getMove"
    )
    class NoDefensiveStance {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Champ, num: Int, ___DEFENSIVE_STANCE: Byte, ___GLOAT: Byte, ___SLAP_NAME: String, ___FACE_SLAP: Byte) {
                val move = ReflectionHacks.getPrivate(__instance, AbstractMonster::class.java, "move") as EnemyMoveInfo
                if (move.nextMove == ___DEFENSIVE_STANCE) {
                    __instance.moveHistory.removeIf { it == ___DEFENSIVE_STANCE }
                    if (!lastMove(__instance, ___GLOAT)) {
                        __instance.setMove(___GLOAT, AbstractMonster.Intent.BUFF)
                    } else {
                        __instance.setMove(___SLAP_NAME, ___FACE_SLAP, AbstractMonster.Intent.ATTACK_DEBUFF, __instance.damage[2].base)
                    }
                }
            }

            private fun lastMove(__instance: AbstractMonster, move: Byte): Boolean {
                return __instance.moveHistory.isNotEmpty() && __instance.moveHistory.last() == move
            }
        }
    }

    @SpirePatch(
        clz = Champ::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class BuffStrengthGain {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Champ, ___strAmt: Int) {
                ReflectionHacks.setPrivate(__instance, Champ::class.java, "strAmt", ___strAmt + 1)
            }
        }
    }
}
