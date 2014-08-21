package br.com.caelum.vraptor.i18n;

public class NullSafeLocalized implements LocalizedInfo {

	@Override
	public LocalizedInfo pattern(String pattern) {
		return this;
	}

	@Override
	public LocalizedInfo custom(String name) {
		return this;
	}
	
	@Override
	public String toString() {
		return "";
	}
}
