package com.github.cm360.pixadv.environment.storage;

import com.github.cm360.pixadv.environment.types.Tile;

public class Chunk {

	protected Tile[][][] tiles;
	
	public Chunk(int size) {
		tiles = new Tile[size][size][3];
	}
	
	public Tile getTile(int x, int y, int z) {
		return tiles[x][y][z];
	}
	
	public void setTile(int x, int y, int z, Tile tile) {
		tiles[x][y][z] = tile;
	}

}
