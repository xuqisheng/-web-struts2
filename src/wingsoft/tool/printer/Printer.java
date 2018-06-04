package wingsoft.tool.printer;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

public interface Printer {
	@SuppressWarnings("unchecked")
	public void print(List<HashMap> data, Setting s, OutputStream os)throws Exception;

}