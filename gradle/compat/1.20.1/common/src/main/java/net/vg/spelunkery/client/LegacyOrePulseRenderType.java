package net.vg.spelunkery.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;

/** Gives the legacy renderer access to protected native render-state constants. */
public abstract class LegacyOrePulseRenderType extends RenderType {
    private LegacyOrePulseRenderType() {
        super("spelunkery:ore_pulse_lines", DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINES,
                1536, false, false, () -> {}, () -> {});
    }
    public static RenderType createLines() {
        return create("spelunkery:ore_pulse_lines", DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINES,
                1536, false, false, CompositeState.builder().setShaderState(RENDERTYPE_LINES_SHADER)
                        .setLineState(new LineStateShard(java.util.OptionalDouble.of(2.0D)))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL)
                        .setDepthTestState(NO_DEPTH_TEST).createCompositeState(false));
    }
}
