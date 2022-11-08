package com.github.cm360.pixadv.graphics.gui.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.graphics.picasso.Picasso;
import com.github.cm360.pixadv.input.KeyCombo;
import com.github.cm360.pixadv.registry.Registry;

public abstract class Component {

	public static float scale = 1f;
	
	protected Rectangle bounds;
	protected BoundsMutator boundsMutator;
	protected Map<KeyCombo, Runnable> events;
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
		this.events = new HashMap<KeyCombo, Runnable>();
		this.focusable = false;
		this.hovered = false;
		this.parent = parent;
		this.children = new ArrayList<Component>();
	}

	public void paint(SpriteBatch batch, Registry registry) {
		// Update boundaries
		if (parent != null) {
			updateBounds(parent.getBounds());
		} else {
			Picasso renderingEngine = ClientApplication.getRenderingEngine();
			Rectangle viewport = new Rectangle(0, 0,
					renderingEngine.getViewportWidth(),
					renderingEngine.getViewportHeight());
			updateBounds(viewport);
		}
		// Paint
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
	
	public void registerEvent(KeyCombo trigger, Runnable action) {
		events.put(trigger, action);
	}
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	public void interactClick(Vector2 mousePos, long clickDuration, KeyCombo keys) {
		for (KeyCombo combo : events.keySet())
			if (keys.containsAll(combo))
				events.get(combo).run();
		// play sound or something?
	}
	
	public void interactType(char character, Set<Integer> modifiers) {
		
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
