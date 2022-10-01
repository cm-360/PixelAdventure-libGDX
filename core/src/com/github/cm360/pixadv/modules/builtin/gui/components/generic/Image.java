package com.github.cm360.pixadv.modules.builtin.gui.components.generic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.cm360.pixadv.graphics.gui.BoundsMutator;
import com.github.cm360.pixadv.graphics.gui.Component;
import com.github.cm360.pixadv.registry.Registry;

public class Image extends Component {

	private Texture texture;
	
	public Image(Component parent, BoundsMutator boundsMutator) {
		super(parent, boundsMutator);
	}
	
	protected void paintSelf(SpriteBatch batch, Registry registry) {
		batch.draw(texture,
				(float) bounds.getX(),
				(float) bounds.getY(),
				(float) bounds.getWidth(),
				(float) bounds.getHeight());
	}

}
