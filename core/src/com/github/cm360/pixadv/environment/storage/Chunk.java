package com.github.cm360.pixadv.environment.storage;

import com.badlogic.gdx.graphics.Color;
import com.github.cm360.pixadv.environment.types.Tile;

public class Chunk {

	protected Tile[][][] tiles;
	protected Color[][] light;
	
	public Chunk(int size) {
		tiles = new Tile[size][size][3];
		light = new Color[size][size];
	}
	
	public Tile getTile(int x, int y, int z) {
		return tiles[x][y][z];
	}
	
	public void setTile(Tile tile, int x, int y, int z) {
		tiles[x][y][z] = tile;
	}
	
	public Color getLight(int x, int y) {
		return light[x][y];
	}
	
	public void setLight(Color color, int x, int y) {
		light[x][y] = color;
	}

}
