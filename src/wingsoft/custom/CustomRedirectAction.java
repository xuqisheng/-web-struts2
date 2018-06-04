package wingsoft.custom;

import java.io.InputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import wingsoft.core.command.QueryDAO;
import wingsoft.tool.db.ConnectionPool;
import wingsoft.tool.db.ConnectionPoolManager;

public class CustomRedirectAction
{
  protected static ConnectionPool pool = ConnectionPoolManager.getPool("CMServer");
  static QueryDAO queryDao = new QueryDAO();
  private InputStream responseText;

  public void setResponseText(InputStream responseText)
  {
    this.responseText = responseText; }

  public InputStream getResponseText() {
    return this.responseText; }

  public void redirectCustomPage() throws Exception {
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpSession session = request.getSession();
    System.out.println("contex="+session.getAttribute("userContextStr"));
    System.out.println("userid="+session.getAttribute("userId"));
    if ((session.getAttribute("userContextStr") == null) || (session.getAttribute("userId") == null))
      return ;

    String url = request.getParameter("url");

    request.getRequestDispatcher("WEB-INF/" + url).forward(request, response);
    return ;
  }
}