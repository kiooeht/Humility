package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ChangeStateAction
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.actions.common.RollMoveAction
import com.megacrit.cardcrawl.actions.unique.CanLoseAction
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction
import com.megacrit.cardcrawl.blights.Shield
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.daily.mods.Colossus
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ModHelper
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne
import com.megacrit.cardcrawl.powers.RitualPower
import com.megacrit.cardcrawl.vfx.AwakenedEyeParticle
import com.megacrit.cardcrawl.vfx.AwakenedWingParticle
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect
import javassist.CtBehavior

class AwakenedOneWokeBloke {
    companion object {
        private const val SECOND_REBIRTH = "SECOND_REBIRTH"
        private const val RITUAL = 10
    }

    @SpirePatch(
        clz = AwakenedOne::class,
        method = SpirePatch.CLASS
    )
    class Fields {
        companion object {
            @JvmField
            val trueForm: SpireField<Boolean> = SpireField { false }
        }
    }

    @SpirePatch(
        clz = AwakenedOne::class,
        method = "changeState"
    )
    class AddRitual {
        companion object {
            @JvmStatic
            fun Postfix(__instance: AwakenedOne, key: String, @ByRef ___animateParticles: BooleanArray) {
                if (key == "REBIRTH") {
                    // Undo the CanLoseAction on rebirth
                    AbstractDungeon.actionManager.addToBottom(CannotLoseAction())
                } else if (key == SECOND_REBIRTH) {
                    __instance.maxHealth = if (AbstractDungeon.ascensionLevel >= 9) {
                        220
                    } else {
                        200
                    }
                    AbstractDungeon.player.getBlight(Shield.ID)?.let {
                        __instance.maxHealth = (__instance.maxHealth * it.effectFloat()).toInt()
                    }

                    if (ModHelper.isModEnabled(Colossus.ID)) {
                        __instance.currentHealth = (__instance.currentHealth * 1.5f).toInt()
                    }

                    __instance.state.setAnimation(0, "Idle_2", true)
                    __instance.halfDead = false
                    ___animateParticles[0] = true

                    AbstractDungeon.actionManager.addToBottom(HealAction(__instance, __instance, __instance.maxHealth))
                    AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, RitualPower(__instance, RITUAL, false), RITUAL))
                    AbstractDungeon.actionManager.addToBottom(CanLoseAction())
                }
            }
        }
    }

    @SpirePatch(
        clz = AwakenedOne::class,
        method = "takeTurn"
    )
    class InitiateSecondRebirth {
        companion object {
            @JvmStatic
            fun Prefix(__instance: AwakenedOne, ___REBIRTH: Byte): SpireReturn<Unit?> {
                if (__instance.nextMove == ___REBIRTH && Fields.trueForm.get(__instance)) {
                    AbstractDungeon.actionManager.addToBottom(
                        VFXAction(
                            __instance,
                            IntenseZoomEffect(
                                __instance.hb.cX,
                                __instance.hb.cY,
                                true
                            ),
                            0.05f,
                            true
                        )
                    )
                    AbstractDungeon.actionManager.addToBottom(ChangeStateAction(__instance, SECOND_REBIRTH))
                    AbstractDungeon.actionManager.addToBottom(RollMoveAction(__instance))
                    return SpireReturn.Return(null)
                }
                return SpireReturn.Continue()
            }
        }
    }

    @SpirePatch(
        clz = AwakenedOne::class,
        method = "damage"
    )
    class SetTrueForm {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class
            )
            fun Insert(__instance: AwakenedOne, info: DamageInfo, ___form1: Boolean, @ByRef ___animateParticles: BooleanArray) {
                if (!___form1) {
                    Fields.trueForm.set(__instance, true)
                    ___animateParticles[0] = false
                    __instance.state.setAnimation(0, "Idle_1", true)
                }
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.FieldAccessMatcher(AwakenedOne::class.java, "form1")
                return LineFinder.findInOrder(ctBehavior, arrayListOf<Matcher>(finalMatcher), finalMatcher)
            }
        }
    }

    @SpirePatch(
        clz = AwakenedOne::class,
        method = "update"
    )
    class MoreParticles {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class
            )
            fun Insert(__instance: AwakenedOne, ___wParticles: ArrayList<AwakenedWingParticle>) {
                if (!__instance.halfDead && Fields.trueForm.get(__instance)) {
                    ___wParticles.add(AwakenedWingParticle())
                    ___wParticles.add(AwakenedWingParticle())
                }
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.NewExprMatcher(AwakenedWingParticle::class.java)
                return LineFinder.findInOrder(ctBehavior, finalMatcher)
            }
        }
    }

    @SpirePatch2(
        clz = AwakenedEyeParticle::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class RedEyes {
        companion object {
            @JvmStatic
            fun Postfix(@ByRef ___color: Array<Color>) {
                val wokeBloke = AbstractDungeon.getCurrMapNode()?.getRoom()?.monsters?.monsters?.firstOrNull { it is AwakenedOne }
                if (wokeBloke != null && Fields.trueForm.get(wokeBloke)) {
                    ___color[0] = Color(
                        MathUtils.random(0.8f, 1f),
                        MathUtils.random(0f, 0.2f),
                        MathUtils.random(0f, 0.2f),
                        0.01f
                    )
                }
            }
        }
    }
}
