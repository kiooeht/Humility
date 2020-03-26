package com.evacipated.cardcrawl.mod.humilty.powers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.vfx.scene.TorchParticleLEffect
import com.megacrit.cardcrawl.vfx.scene.TorchParticleMEffect
import com.megacrit.cardcrawl.vfx.scene.TorchParticleSEffect

class BurningStabsPower(
    owner: AbstractCreature
) : AbstractHumilityPower(POWER_ID, "painfulStabs") {
    companion object {
        val POWER_ID = HumilityMod.makeID("BurningStabs")
    }
    private var particleTimer: Float = 0f

    init {
        this.owner = owner
        this.amount = -1
        updateDescription()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0]
    }

    override fun onInflictDamage(info: DamageInfo?, damageAmount: Int, target: AbstractCreature?) {
        if (damageAmount > 0 && info?.type != DamageInfo.DamageType.THORNS) {
            addToBot(MakeTempCardInDiscardAction(Burn(), 1))
        }
    }

    override fun update(slot: Int) {
        super.update(slot)

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
