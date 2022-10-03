package com.github.cm360.pixadv.graphics.gui.components.generic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.cm360.pixadv.ClientApplication;
import com.github.cm360.pixadv.graphics.gui.components.BoundsMutator;
import com.github.cm360.pixadv.graphics.gui.components.Component;
import com.github.cm360.pixadv.registry.Identifier;
import com.github.cm360.pixadv.registry.Registry;

public class Image extends Component {

	protected Identifier[] textures;
	
	public Image(Component parent, BoundsMutator boundsMutator) {
		super(parent, boundsMutator);
	}
	
	protected void paintSelf(SpriteBatch batch, Registry registry) {
		for (Identifier textureId : textures) {
			batch.draw(ClientApplication.getRegistry().getTexture(textureId),
					(float) bounds.getX(),
					(float) bounds.getY(),
					(float) bounds.getWidth(),
					(float) bounds.getHeight());
		}
		
	}
	
	public void setTextures(Identifier[] newTextures) {
		textures = newTextures;
	}

}
