package com.linln.utill;

import com.linln.admin.base.util.ApiResponse;
import com.linln.devtools.generate.utils.CodeUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;

/**
 * 文件操作工具
 * @author 小懒虫
 * @date 2019/3/2
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取模板文件路径
     * @param clazz 类对象
     */
    public static String templatePath(Class<?> clazz){
        return clazz.getResource("").getPath() + clazz.getSimpleName() + ".tpl";
    }

    /**
     * 保存文本文件
     * @param file 文件对象
     * @param content 文件内容
     */
    public static void saveWriter(File file, String content){
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            if (!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, CodeUtil.ENCODE);
            osw.write(content);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static ApiResponse upload(MultipartFile file) {
        String path = "D:\\test\\";
        String fileName = UUID.randomUUID().toString().replace("-","");
        File tFile = new File(path + File.pathSeparator + fileName);

        FileOutputStream fileOutputStream  = null;
        try {
            fileOutputStream = new FileOutputStream(tFile);
            IOUtils.copy(file.getInputStream(),fileOutputStream);
            logger.info("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("异常");
            return ApiResponse.ofError("异常");
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("文件上传成功");
        return ApiResponse.ofSuccess("文件上传成功");
    }
}
