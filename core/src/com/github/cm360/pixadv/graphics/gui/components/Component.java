package com.github.cm360.pixadv.graphics.gui.components;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.cm360.pixadv.registry.Registry;

public abstract class Component {

	protected Rectangle2D bounds;
	protected BoundsMutator boundsMutator;
	protected boolean focusable;
	protected boolean hovered;
	protected Component parent;
	protected List<Component> children;
	
	public Component(Component parent) {
		this(parent, dummy -> dummy);
	}

	public Component(Component parent, BoundsMutator boundsMutator) {
		this.bounds = new Rectangle2D.Double();
		this.boundsMutator = boundsMutator;
		this.focusable = false;
		this.hovered = false;
		this.parent = parent;
		this.children = new ArrayList<Component>();
	}

	public void paint(SpriteBatch batch, Registry registry) {
		updateBounds(parent.getBounds());
		paintSelf(batch, registry);
		for (Component child : children)
			child.paint(batch, registry);
	}
	
	protected void paintSelf(SpriteBatch batch, Registry registry) {
		// Do nothing by default
	}
	
	public void updateBounds(Rectangle2D parentBounds) {
		bounds = boundsMutator.mutate(parentBounds);
	}
	
	public Rectangle2D getBounds() {
		return bounds;
	}
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	public Component attemptFocus(Point mousePos) {
		for (Component child : children) {
			Component result = child.attemptFocus(mousePos);
			if (result != null)
				return result;
		}
		return (focusable && getBounds().contains(mousePos)) ? this : null;
	}
	
	public Component getParent() {
		return parent;
	}
	
	public List<Component> getChildren() {
		return children;
	}

}
