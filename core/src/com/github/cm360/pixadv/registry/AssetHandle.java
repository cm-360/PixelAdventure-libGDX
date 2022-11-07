package com.github.cm360.pixadv.registry;

import java.io.InputStream;
import java.io.OutputStream;

import com.badlogic.gdx.files.FileHandleStream;

public class AssetHandle extends FileHandleStream {

	public AssetHandle(String path) {
		super(path);
	}

	@Override
	public InputStream read() {
		throw new UnsupportedOperationException();
	}

	@Override
	public OutputStream write(boolean overwrite) {
		throw new UnsupportedOperationException();
	}

}
