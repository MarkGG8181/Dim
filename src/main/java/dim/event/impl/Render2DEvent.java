package dim.event.impl;

import dim.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent extends Event {
	public final float p_175180_1_;
	public final ScaledResolution sr;
	
	public Render2DEvent(float p_175180_1_, ScaledResolution sr) {
		super();
		this.p_175180_1_ = p_175180_1_;
		this.sr = sr;
	}
}
