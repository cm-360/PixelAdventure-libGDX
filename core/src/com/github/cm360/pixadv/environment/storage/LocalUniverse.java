package com.github.cm360.pixadv.environment.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import com.google.gson.Gson;

public class LocalUniverse extends Universe {

	public LocalUniverse(File universeDirectory) throws FileNotFoundException {
		Gson gson = new Gson();
		gson.fromJson(new FileReader(new File(universeDirectory, "info.json")), Map.class);
	}
	
}
