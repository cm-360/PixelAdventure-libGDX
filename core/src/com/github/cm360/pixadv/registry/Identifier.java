package com.github.cm360.pixadv.registry;

public class Identifier {

	private String namespace;
	private String path;
	private int hashCode;
	
	public Identifier(String namespace, String path) {
		this.namespace = namespace.replaceAll("[^\\w]", "_").toLowerCase();
		this.path = path.replaceAll("[^\\w\\/]", "_").toLowerCase();
		this.hashCode = toString().hashCode();
	}
	
	public static Identifier parse(String string) {
		String[] split = string.split(":", 2);
		return new Identifier(split[0], split[1]);
	}
	
	@Override
	public boolean equals(Object other) {
		return hashCode() == other.hashCode();
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}
	
	public boolean contains(Identifier other) {
		return other.toString().startsWith(toString());
	}
	
	@Override
	public String toString() {
		return String.format("%s:%s", namespace, path);
	}

}
