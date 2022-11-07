package com.github.cm360.pixadv.registry;

import java.io.InputStream;

import com.badlogic.gdx.files.FileHandleStream;

public class StreamHandle extends FileHandleStream {

	protected InputStream stream;
	
	public StreamHandle(String path, InputStream stream) {
		super(path);
		this.stream = stream;
	}

	@Override
	public InputStream read() {
		return stream;
	}

}
