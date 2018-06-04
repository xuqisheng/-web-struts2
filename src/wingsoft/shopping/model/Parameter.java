package wingsoft.shopping.model;

public class Parameter {
	private String parameterid;
	private String parametername;
	private String parametertype;
	
	public Parameter(){}

	public Parameter(String parameterid, String parametername,
			String parametertype) {
		super();
		this.parameterid = parameterid;
		this.parametername = parametername;
		this.parametertype = parametertype;
	}

	public String getParameterid() {
		return parameterid;
	}

	public void setParameterid(String parameterid) {
		this.parameterid = parameterid;
	}

	public String getParametername() {
		return parametername;
	}

	public void setParametername(String parametername) {
		this.parametername = parametername;
	}

	public String getParametertype() {
		return parametertype;
	}

	public void setParametertype(String parametertype) {
		this.parametertype = parametertype;
	}
	
	
}
