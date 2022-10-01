package com.github.cm360.pixadv.graphics.gui.components;

import com.badlogic.gdx.math.Rectangle;

@FunctionalInterface
public interface BoundsMutator {

	public Rectangle mutate(Rectangle parentBounds);

}