package com.github.cm360.pixadv.graphics.gui.components.generic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.graphics.gui.components.BoundsMutator;
import com.github.cm360.pixadv.graphics.gui.components.Component;
import com.github.cm360.pixadv.registry.Identifier;
import com.github.cm360.pixadv.registry.Registry;

public class TextField extends Component {

	protected Identifier fontId;
	protected int fontSize;
	
	protected String text;
	protected int cursorStart;
	protected int cursorEnd;
	
	public TextField(Component parent, BoundsMutator boundsMutator, Identifier fontId, int fontSize) {
		super(parent, boundsMutator);
		this.fontId = fontId;
		this.fontSize = fontSize;
	}
	
	@Override
	protected void paintSelf(SpriteBatch batch, Registry registry) {
		super.paintSelf(batch, registry);
		BitmapFont font = ClientApplication.getRegistry().getFont(fontId, fontSize);
		// Draw foreground text
		font.setColor(Color.WHITE);
		font.draw(batch, text,
				bounds.x,
				bounds.y + (bounds.height * 0.75f),
				bounds.width, Align.left, false);
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
