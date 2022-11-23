package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import basemod.ReflectionHacks
import com.evacipated.cardcrawl.mod.humilty.powers.FatedShapesPower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches2
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.beyond.Deca
import com.megacrit.cardcrawl.monsters.beyond.Donu

class DonuDecaLinked {
    @SpirePatches2(
        SpirePatch2(
            clz = Donu::class,
            method = SpirePatch.CONSTRUCTOR
        ),
        SpirePatch2(
            clz = Deca::class,
            method = SpirePatch.CONSTRUCTOR
        )
    )
    class CombineMaxHP {
        companion object {
            @JvmStatic
            fun Postfix(__instance: AbstractMonster) {
                val maxHp = if (AbstractDungeon.ascensionLevel >= 9) {
                    530
                } else {
                    500
                }
                ReflectionHacks.privateMethod(AbstractMonster::class.java, "setHp", Int::class.java)
                    .invoke<Unit>(__instance, maxHp)
            }
        }
    }

    @SpirePatch(
        clz = Donu::class,
        method = "usePreBattleAction"
    )
    class AddDonu {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Donu) {
                val deca = AbstractDungeon.getMonsters().monsters
                    .filterIsInstance<Deca>()
                    .firstOrNull()
                if (deca != null) {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            __instance,
                            __instance,
                            FatedShapesPower(__instance, deca)
                        )
                    )
                }
            }
        }
    }

    @SpirePatch(
        clz = Deca::class,
        method = "usePreBattleAction"
    )
    class AddDeca {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Deca) {
                val donu = AbstractDungeon.getMonsters().monsters
                    .filterIsInstance<Donu>()
                    .firstOrNull()
                if (donu != null) {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            __instance,
                            __instance,
                            FatedShapesPower(__instance, donu)
                        )
                    )
                }
            }
        }
    }
}
