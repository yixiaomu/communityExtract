package cn.passionshark.community.web.controller.base;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.passionshark.project.community.core.service.base.FileService;
import cn.passionshark.project.community.core.util.AbstractTradeFacade;

@Controller
@RequestMapping(value = "/base/file")
public class FileController extends AbstractTradeFacade {

    static final Logger logger = LoggerFactory.getLogger(FileController.class);
    
    @Autowired
    private FileService fileService;
    /**
     * 上传图片
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA)
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, 
    		HttpServletRequest request, ModelMap model) {
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();
        //String fileName = new Date().getTime()+".jpg";  
        System.out.println(path);  
        File targetFile = new File("/tmp/data/", fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        //model.addAttribute("fileUrl", request.getContextPath()+"/upload/"+fileName);  
  
        return fileName;  
    }  
}
