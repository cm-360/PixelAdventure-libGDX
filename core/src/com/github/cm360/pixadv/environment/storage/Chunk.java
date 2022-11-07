package com.github.cm360.pixadv.environment.storage;

import com.github.cm360.pixadv.environment.types.Tile;

public class Chunk {

	public static final int layers = 3;
	protected Tile[][][] tiles;
	
	public Chunk(int size) {
		tiles = new Tile[size][size][layers];
	}
	
	public Tile getTile(int x, int y, int z) {
		return tiles[x][y][z];
	}
	
	public void setTile(Tile tile, int x, int y, int z) {
		tiles[x][y][z] = tile;
	}

}
