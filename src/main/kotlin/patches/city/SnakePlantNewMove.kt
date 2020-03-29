package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ChangeStateAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.city.SnakePlant
import com.megacrit.cardcrawl.powers.ArtifactPower
import com.megacrit.cardcrawl.powers.GainStrengthPower
import com.megacrit.cardcrawl.powers.StrengthPower

class SnakePlantNewMove {
    companion object {
        private const val POISON_STRIKE: Byte = 3
        private const val DAMAGE = 12
        private const val STR_DOWN = 3
    }

    @SpirePatch(
        clz = SnakePlant::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class AddDamage {
        companion object {
            @JvmStatic
            fun Postfix(__instance: SnakePlant, x: Float, y: Float) {
                __instance.damage.add(DamageInfo(__instance, DAMAGE))
            }
        }
    }

    @SpirePatch(
        clz = SnakePlant::class,
        method = "getMove"
    )
    class GetMove {
        companion object {
            @JvmStatic
            fun Prefix(__instance: SnakePlant, num: Int): SpireReturn<Unit?> {
                if (num < 30) {
                    __instance.setMove(POISON_STRIKE, AbstractMonster.Intent.ATTACK_DEBUFF, __instance.damage[1].base)
                    return SpireReturn.Return(null)
                }
                return SpireReturn.Continue()
            }
        }
    }

    @SpirePatch(
        clz = SnakePlant::class,
        method = "takeTurn"
    )
    class TakeTurn {
        companion object {
            @JvmStatic
            fun Prefix(__instance: SnakePlant) {
                if (__instance.nextMove == POISON_STRIKE) {
                    AbstractDungeon.actionManager.addToBottom(ChangeStateAction(__instance, "ATTACK"))
                    AbstractDungeon.actionManager.addToBottom(WaitAction(0.5f))
                    AbstractDungeon.actionManager.addToBottom(DamageAction(
                        AbstractDungeon.player,
                        __instance.damage[1],
                        AbstractGameAction.AttackEffect.POISON,
                        true
                    ))
                    AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(
                        AbstractDungeon.player,
                        __instance,
                        StrengthPower(AbstractDungeon.player, -STR_DOWN),
                        -STR_DOWN,
                        true,
                        AbstractGameAction.AttackEffect.NONE
                    ))
                    if (!AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID)) {
                        AbstractDungeon.actionManager.addToBottom(
                            ApplyPowerAction(
                                AbstractDungeon.player,
                                __instance,
                                GainStrengthPower(AbstractDungeon.player, STR_DOWN),
                                STR_DOWN,
                                true,
                                AbstractGameAction.AttackEffect.NONE
                            )
                        )
                    }
                }
            }
        }
    }
}
