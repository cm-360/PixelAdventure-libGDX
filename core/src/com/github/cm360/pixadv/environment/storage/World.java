package com.github.cm360.pixadv.environment.storage;

public class World {

	protected Chunk[][] chunks;
	
	public Chunk getChunk(int cx, int cy) {
		return chunks[cx][cy];
	}
	
	public void setChunk(int cx, int cy, Chunk chunk) {
		chunks[cx][cy] = chunk;
	}
	
}
