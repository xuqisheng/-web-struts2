package wingsoft.core.command;

import java.util.HashMap;

import wingsoft.core.wfdao.UserAuthorityDAO;

public class CommandAuthorityController {
	protected UserAuthorityDAO uaDao = new UserAuthorityDAO();
	protected CommandContextParser ccparser = new CommandContextParser();
	
	public QueryCommand AddConstraint(QueryCommand qCmd,HashMap<String,String> userContext){
		String uconditions = uaDao.findUserConditions(qCmd.getUserid(),qCmd.getTablenames());
		if((uconditions!=null) && !uconditions.equals("")){
			uconditions = ccparser.parser(uconditions, userContext);
			qCmd.setUserconditions(uconditions);
		}
		return qCmd;
	}
}
