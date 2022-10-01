package com.github.cm360.pixadv.graphics.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.cm360.pixadv.registry.Registry;

public abstract class Component {

	protected Rectangle bounds;
	protected BoundsMutator boundsMutator;
	protected boolean focusable;
	protected boolean hovered;
	protected Component parent;
	protected List<Component> children;
	
	public Component(Component parent) {
		this(parent, dummy -> dummy);
	}

	public Component(Component parent, BoundsMutator boundsMutator) {
		this.bounds = new Rectangle();
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
	
	public void updateBounds(Rectangle parentBounds) {
		bounds = boundsMutator.mutate(parentBounds);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	public Component attemptFocus(Vector2 mousePos) {
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
