package com.evacipated.cardcrawl.mod.humilty.helpers

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.GdxRuntimeException

class AssetLoader {
    private val assets = AssetManager()

    fun loadImage(fileName: String): Texture? {
        if (!assets.isLoaded(fileName, Texture::class.java)) {
            val param = TextureLoader.TextureParameter()
            param.minFilter = Texture.TextureFilter.Linear
            param.magFilter = Texture.TextureFilter.Linear
            assets.load(fileName, Texture::class.java, param)
            try {
                assets.finishLoadingAsset(fileName)
            } catch (e: GdxRuntimeException) {
                return null
            }
        }
        return assets.get(fileName, Texture::class.java)
    }

    fun loadAtlas(fileName: String): TextureAtlas? {
        if (!assets.isLoaded(fileName, TextureAtlas::class.java)) {
            assets.load(fileName, TextureAtlas::class.java)
            try {
                assets.finishLoadingAsset(fileName)
            } catch (e: GdxRuntimeException) {
                return null
            }
        }
        return assets.get(fileName, TextureAtlas::class.java)
    }
}
