package wingsoft.core.command;

import java.util.List;

public class Procedure {
	private String name;
	private List<Field> params;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setParams(List<Field> params) {
		this.params = params;
	}
	public List<Field> getParams() {
		return params;
	}
}
