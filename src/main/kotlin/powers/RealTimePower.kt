package com.evacipated.cardcrawl.mod.humilty.powers

import com.badlogic.gdx.Gdx
import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.StrengthPower
import kotlin.math.ceil

class RealTimePower(
    owner: AbstractCreature
) : AbstractHumilityPower(POWER_ID, "time") {
    companion object {
        val POWER_ID = HumilityMod.makeID("RealTime")
        private const val TIMER = 30
        private const val STR = 1
    }

    private var timer = TIMER.toFloat()

    init {
        this.owner = owner
        this.amount = TIMER
        updateDescription()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0].format(TIMER, STR)
    }

    override fun update(slot: Int) {
        super.update(slot)

        timer -= Gdx.graphics.rawDeltaTime
        val prevAmount = amount
        amount = ceil(timer).toInt()

        if (timer <= 0) {
            timer = TIMER.toFloat()
            flash()
            playApplyPowerSfx()
            addToBot(ApplyPowerAction(owner, owner, StrengthPower(owner, STR), STR))
        } else if (amount != prevAmount) {
            flashWithoutSound()
            // Remove the FlashPowerEffect, so only the small power icon flashes
            AbstractDungeon.effectList.removeLast()
        }
    }
}
