package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.PainfulStabsPower
import com.megacrit.cardcrawl.vfx.scene.TorchParticleLEffect
import com.megacrit.cardcrawl.vfx.scene.TorchParticleMEffect
import com.megacrit.cardcrawl.vfx.scene.TorchParticleSEffect
import javassist.CannotCompileException
import javassist.CtBehavior
import javassist.CtNewMethod

@SpirePatch(
    clz = PainfulStabsPower::class,
    method = SpirePatch.CONSTRUCTOR
)
class BookOfStabbingFireEffect {
    companion object {
        @JvmStatic
        fun Raw(ctBehavior: CtBehavior) {
            val ctClass = ctBehavior.declaringClass
            val pool = ctClass.classPool
            var method = CtNewMethod.delegator(
                ctClass.superclass.getDeclaredMethod("update"),
                ctClass
            )
            try {
                ctClass.addMethod(method)
            } catch (e: CannotCompileException) {
                e.printStackTrace()
                method = ctClass.getDeclaredMethod("update")
            }

            method.insertAfter("${BookOfStabbingFireEffect::class.qualifiedName}.addFireParticles(owner);")
        }

        private var particleTimer: Float = 0f
        @JvmStatic
        fun addFireParticles(owner: AbstractCreature) {
            particleTimer += Gdx.graphics.deltaTime
            if (particleTimer > 0.1f) {
                particleTimer -= MathUtils.random(0.05f, 0.1f)
                val renderGreenS = TorchParticleSEffect.renderGreen
                val renderGreenM = TorchParticleMEffect.renderGreen
                val renderGreenL = TorchParticleLEffect.renderGreen
                TorchParticleSEffect.renderGreen = false
                TorchParticleMEffect.renderGreen = false
                TorchParticleLEffect.renderGreen = false
                AbstractDungeon.effectsQueue.add(
                    when (MathUtils.random(0, 2)) {
                        0 -> TorchParticleSEffect(
                                owner.hb.cX + MathUtils.random(owner.hb.width / 2f, -owner.hb.width / 2f),
                                owner.hb.cY + MathUtils.random(owner.hb.height / 2f, -owner.hb.height / 2f)
                            )
                        1 -> TorchParticleMEffect(
                                owner.hb.cX + MathUtils.random(owner.hb.width / 2f, -owner.hb.width / 2f),
                                owner.hb.cY + MathUtils.random(owner.hb.height / 2f, -owner.hb.height / 2f)
                            )
                        else -> TorchParticleLEffect(
                                owner.hb.cX + MathUtils.random(owner.hb.width / 2f, -owner.hb.width / 2f),
                                owner.hb.cY + MathUtils.random(owner.hb.height / 2f, -owner.hb.height / 2f)
                            )
                    }.also { it.renderBehind = MathUtils.randomBoolean(0.2f) }
                )
                TorchParticleSEffect.renderGreen = renderGreenS
                TorchParticleMEffect.renderGreen = renderGreenM
                TorchParticleLEffect.renderGreen = renderGreenL
            }
        }
    }
}
