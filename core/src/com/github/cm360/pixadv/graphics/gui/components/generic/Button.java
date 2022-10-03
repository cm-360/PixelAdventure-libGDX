package com.github.cm360.pixadv.graphics.gui.components.generic;

import com.github.cm360.pixadv.graphics.gui.components.BoundsMutator;
import com.github.cm360.pixadv.graphics.gui.components.Component;
import com.github.cm360.pixadv.registry.Identifier;

public class Button extends Image {

	protected String text;
	protected Identifier font;
	protected int fontSize;
	
	public Button(Component parent, BoundsMutator boundsMutator, String text, Identifier font, int fontSize) {
		super(parent, boundsMutator);
		this.font = font;
		this.fontSize = fontSize;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Identifier getFont() {
		return font;
	}

	public void setFont(Identifier font) {
		this.font = font;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

}
