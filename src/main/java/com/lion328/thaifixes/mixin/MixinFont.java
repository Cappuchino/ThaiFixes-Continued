package com.lion328.thaifixes.mixin;

import com.lion328.thaifixes.HangingThaiCharacterTexturedGlyph;
import com.lion328.thaifixes.ThaiFixes;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Font.class)
public abstract class MixinFont
{

    @Inject(method = "createTexturedGlyph", at = @At("RETURN"), cancellable = true)
    public void onCreateTexturedGlyph(IGlyphInfo glyphInfo, CallbackInfoReturnable<TexturedGlyph> cir)
    {
        if (!ThaiFixes.processingThaiChars.containsKey(glyphInfo))
        {
            return;
        }

        TexturedGlyph parent = cir.getReturnValue();
        char c = ThaiFixes.processingThaiChars.get(glyphInfo);

        TexturedGlyph wrapped = HangingThaiCharacterTexturedGlyph.fromParent(parent, c);
        cir.setReturnValue(wrapped);

        ThaiFixes.processingThaiChars.remove(glyphInfo);
    }
}
