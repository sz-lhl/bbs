package cms.web.action.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cms.bean.data.DataBaseFile;
import cms.utils.PathUtil;
import cms.web.action.FileManage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 数据库备份/还原管理
 *
 */
@Controller
public class DataBaseAction {
	@Resource MySqlDataManage mySqlDataManage;
	@Resource FileManage fileManage;
	
	/**
	 * 数据库备份文件列表
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/control/dataBase/list") 
	public String execute(ModelMap model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//显示还原数据目录
		String path = "WEB-INF"+File.separator+"data"+File.separator + "backup" + File.separator;
		
		List<DataBaseFile> dataBaseFileList = new ArrayList<DataBaseFile>();
		Map<String, String> folder = mySqlDataManage.getFolder(PathUtil.path()+File.separator+path);
		for (Map.Entry<String, String> entry : folder.entrySet()) {  
			DataBaseFile dataBaseFile = new DataBaseFile();
			dataBaseFile.setFileName(entry.getKey());
			dataBaseFile.setFileSize(entry.getValue());
			
			
			//读取备份的数据库版本
			String version = fileManage.readFileToString(path+File.separator+entry.getKey()+File.separator+"version.txt","utf-8");
			dataBaseFile.setVersion(version);
			
			dataBaseFileList.add(dataBaseFile);
		   
		}  
		
		model.addAttribute("dataBaseFileList",dataBaseFileList );
		return "jsp/data/dataBaseList";
	}	

}
