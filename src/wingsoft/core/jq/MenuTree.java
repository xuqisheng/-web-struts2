package wingsoft.core.jq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wingsoft.tool.common.CommonOperation;

public class MenuTree {
	

	private List<HashMap> treeNodes;
	private StringBuffer menuHtml=new StringBuffer();
	
	

	public MenuTree(List<HashMap> treeNodes) {
		this.treeNodes=treeNodes;
	}

	public List<HashMap> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<HashMap> treeNodes) {
		this.treeNodes = treeNodes;
	}

	@SuppressWarnings("unchecked")
	public String getMenuTreeHtml(){
		menuHtml = new StringBuffer();
		menuHtml.append("<div id='menu'><ul class='menu'>");
		List<HashMap> children = getChildren("-1");
		for(int i = 0; i < children.size(); i++){
			travalTreeNodeToGenerateMenuHtml(children.get(i));
		}
		menuHtml.append("</ul></div>");
		return menuHtml.toString();
	}
	
	@SuppressWarnings("unchecked")
	private void travalTreeNodeToGenerateMenuHtml(HashMap root){
		menuHtml.append("<li>"); 
		
		menuHtml.append(nodeHtml(CommonOperation.nTrim(root.get("NODENAME")),
								 root.get("WINNO").toString(),
								 root.get("NODEID").toString()
								)
						);
		List<HashMap> children=getChildren(root.get("NODEID").toString());
		if(children!=null&&children.size()>0){
			menuHtml.append("<div><ul>");
			//menuHtml.append("<ul>");
			for(int i=0;i<children.size();i++){
				travalTreeNodeToGenerateMenuHtml(children.get(i));
			}
			menuHtml.append("</ul></div>");
			//menuHtml.append("</ul>");
		}
		
		menuHtml.append("</li>");
	}
	
	@SuppressWarnings("unchecked")
	private	List<HashMap> getChildren(String parenId){
		
		List<HashMap> children=new ArrayList<HashMap>();
		HashMap tNode=null;
		for(int i=0;i<treeNodes.size();i++){
			tNode=treeNodes.get(i);
			if(tNode.get("PARENT_NODEID").toString().equals(parenId)){
				children.add(tNode);
			}
		}
		return children;
	}
	
	private String nodeHtml(String nodeName,String pageno,String nodeid){
		String parTag = pageno.equals("-1")?"class='parent'":"onclick=\"$.userContext.setData('currMenuNodeId',"+nodeid+");$.page.open("+pageno+")\"";
		return "<a href='javascript:void(0)' "+parTag+" >" 
					+ "<span>" + nodeName + "</span></a>";
	}
}
