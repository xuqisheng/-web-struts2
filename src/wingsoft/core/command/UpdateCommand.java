package wingsoft.core.command;

import java.util.List;

public class UpdateCommand {
	//目前写死一个更新指令最多只能调用5个存储过程或者sql
	private String check_func;
	private List check_data;
	private String proc0;
	private List data0;
	private String proc1;
	private List data1;
	private String proc2;
	private List data2;
	private String proc3;
	private List data3;
	private String proc4;
	private List data4;
	public String getCheck_func() {
		return check_func;
	}
	public void setCheck_func(String check_func) {
		this.check_func = check_func;
	}
	public List getCheck_data() {
		return check_data;
	}
	public void setCheck_data(List check_data) {
		this.check_data = check_data;
	}
	public String getProc0() {
		return proc0;
	}
	public void setProc0(String proc0) {
		this.proc0 = proc0;
	}
	public List getData0() {
		return data0;
	}
	public void setData0(List data0) {
		this.data0 = data0;
	}
	public String getProc1() {
		return proc1;
	}
	public void setProc1(String proc1) {
		this.proc1 = proc1;
	}
	public List getData1() {
		return data1;
	}
	public void setData1(List data1) {
		this.data1 = data1;
	}
	public String getProc2() {
		return proc2;
	}
	public void setProc2(String proc2) {
		this.proc2 = proc2;
	}
	public List getData2() {
		return data2;
	}
	public void setData2(List data2) {
		this.data2 = data2;
	}
	public String getProc3() {
		return proc3;
	}
	public void setProc3(String proc3) {
		this.proc3 = proc3;
	}
	public List getData3() {
		return data3;
	}
	public void setData3(List data3) {
		this.data3 = data3;
	}
	public String getProc4() {
		return proc4;
	}
	public void setProc4(String proc4) {
		this.proc4 = proc4;
	}
	public List getData4() {
		return data4;
	}
	public void setData4(List data4) {
		this.data4 = data4;
	}
}
