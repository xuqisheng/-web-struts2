package wingsoft.shopping.model;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Supplier entity. @author MyEclipse Persistence Tools
 */

public class Supplier implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String address;
	private String telephone;
	private String contact;
	private String account;
	private String accname;
	private String accbank;
	private String SLevel;
	private String email;
	private String businesslicense;
	private String taxlicense;
	private Date qualificationsbegin;
	private Date qualificationsend;
	private Double havesettle;
	private Double freeze;
	private Double presettle;
	private Double waitpay;
	private String orgcode;
	private String pfiles;
	private String qq;
	private String url;
	private Double postage;
	private Double small;
	private String taxNo;
	private String remark;
	private int paymentDays;
	private Double deposit;
	private String EInvoice;
	private String EInvoiceNoauth;
	private String B_CODE;
	private String branchcode;
	
	
	private Set supplierBcodes = new HashSet(0);

	// Constructors

	/** default constructor */
	public Supplier() {
	}

	/** minimal constructor */
	public Supplier(String id, String name, Double havesettle, Double freeze,
			Double presettle, Double waitpay, Double postage, Double small,
			String EInvoice, String EInvoiceNoauth,String B_CODE) {
		this.id = id;
		this.name = name;
		this.havesettle = havesettle;
		this.freeze = freeze;
		this.presettle = presettle;
		this.waitpay = waitpay;
		this.postage = postage;
		this.small = small;
		this.EInvoice = EInvoice;
		this.EInvoiceNoauth = EInvoiceNoauth;
		this.B_CODE = B_CODE;
	}

	/** full constructor */
	public Supplier(String id, String name, String address, String telephone,
			String contact, String account, String accname, String accbank,
			String SLevel, String email, String businesslicense,
			String taxlicense, Date qualificationsbegin,
			Date qualificationsend, Double havesettle, Double freeze,
			Double presettle, Double waitpay, String orgcode, String pfiles,
			String qq, String url, Double postage, Double small, String taxNo,
			String remark, int paymentDays, Double deposit,
			String EInvoice, String EInvoiceNoauth,String B_CODE, Set supplierBcodes) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.telephone = telephone;
		this.contact = contact;
		this.account = account;
		this.accname = accname;
		this.accbank = accbank;
		this.SLevel = SLevel;
		this.email = email;
		this.businesslicense = businesslicense;
		this.taxlicense = taxlicense;
		this.qualificationsbegin = qualificationsbegin;
		this.qualificationsend = qualificationsend;
		this.havesettle = havesettle;
		this.freeze = freeze;
		this.presettle = presettle;
		this.waitpay = waitpay;
		this.orgcode = orgcode;
		this.pfiles = pfiles;
		this.qq = qq;
		this.url = url;
		this.postage = postage;
		this.small = small;
		this.taxNo = taxNo;
		this.remark = remark;
		this.paymentDays = paymentDays;
		this.deposit = deposit;
		this.EInvoice = EInvoice;
		this.EInvoiceNoauth = EInvoiceNoauth;
		this.B_CODE = B_CODE;
		this.supplierBcodes = supplierBcodes;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getB_CODE() {
		return B_CODE;
	}

	public void setB_CODE(String bCODE) {
		B_CODE = bCODE;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccname() {
		return this.accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getAccbank() {
		return this.accbank;
	}

	public void setAccbank(String accbank) {
		this.accbank = accbank;
	}

	public String getSLevel() {
		return this.SLevel;
	}

	public void setSLevel(String SLevel) {
		this.SLevel = SLevel;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBusinesslicense() {
		return this.businesslicense;
	}

	public void setBusinesslicense(String businesslicense) {
		this.businesslicense = businesslicense;
	}

	public String getTaxlicense() {
		return this.taxlicense;
	}

	public void setTaxlicense(String taxlicense) {
		this.taxlicense = taxlicense;
	}

	public Date getQualificationsbegin() {
		return this.qualificationsbegin;
	}

	public void setQualificationsbegin(Date qualificationsbegin) {
		this.qualificationsbegin = qualificationsbegin;
	}

	public Date getQualificationsend() {
		return this.qualificationsend;
	}

	public void setQualificationsend(Date qualificationsend) {
		this.qualificationsend = qualificationsend;
	}

	public Double getHavesettle() {
		return this.havesettle;
	}

	public void setHavesettle(Double havesettle) {
		this.havesettle = havesettle;
	}

	public Double getFreeze() {
		return this.freeze;
	}

	public void setFreeze(Double freeze) {
		this.freeze = freeze;
	}

	public Double getPresettle() {
		return this.presettle;
	}

	public void setPresettle(Double presettle) {
		this.presettle = presettle;
	}

	public Double getWaitpay() {
		return this.waitpay;
	}

	public void setWaitpay(Double waitpay) {
		this.waitpay = waitpay;
	}

	public String getOrgcode() {
		return this.orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getPfiles() {
		return this.pfiles;
	}

	public void setPfiles(String pfiles) {
		this.pfiles = pfiles;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Double getPostage() {
		return this.postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	public Double getSmall() {
		return this.small;
	}

	public void setSmall(Double small) {
		this.small = small;
	}

	public String getTaxNo() {
		return this.taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getPaymentDays() {
		return this.paymentDays;
	}

	public void setPaymentDays(int paymentDays) {
		this.paymentDays = paymentDays;
	}

	public Double getDeposit() {
		return this.deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public String getEInvoice() {
		return this.EInvoice;
	}

	public void setEInvoice(String EInvoice) {
		this.EInvoice = EInvoice;
	}

	public String getEInvoiceNoauth() {
		return this.EInvoiceNoauth;
	}

	public void setEInvoiceNoauth(String EInvoiceNoauth) {
		this.EInvoiceNoauth = EInvoiceNoauth;
	}

	public Set getSupplierBcodes() {
		return this.supplierBcodes;
	}

	public void setSupplierBcodes(Set supplierBcodes) {
		this.supplierBcodes = supplierBcodes;
	}

}