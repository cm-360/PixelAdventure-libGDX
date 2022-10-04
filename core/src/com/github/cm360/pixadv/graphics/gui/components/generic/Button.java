package com.github.cm360.pixadv.graphics.gui.components.generic;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.graphics.gui.components.BoundsMutator;
import com.github.cm360.pixadv.graphics.gui.components.Component;
import com.github.cm360.pixadv.registry.Identifier;
import com.github.cm360.pixadv.registry.Registry;

public class Button extends Image {

	protected String text;
	protected Identifier fontId;
	protected int fontSize;
	
	public Button(Component parent, BoundsMutator boundsMutator, String text, Identifier fontId, int fontSize) {
		super(parent, boundsMutator);
		this.text = text;
		this.fontId = fontId;
		this.fontSize = fontSize;
		this.focusable = true;
	}
	
	@Override
	protected void paintSelf(SpriteBatch batch, Registry registry) {
		super.paintSelf(batch, registry);
		BitmapFont font = ClientApplication.getRegistry().getFont(fontId, fontSize);
		font.draw(batch, text,
				bounds.x,
				bounds.y + (bounds.height * 0.75f),
				0, text.length(),
				bounds.width, Align.center, false);
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Identifier getFontId() {
		return fontId;
	}

	public void setFontId(Identifier fontId) {
		this.fontId = fontId;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

}
