package com.sg.survey;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.microsoft.schemas.office.visio.x2012.main.CellType;
import com.sg.survey.title.TitleInfoModel;
import com.sg.survey.title.TitleModel;
import com.sg.survey.util.UploadUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by jiuge on 2020/5/26.
 */
@Controller("com.sg.survey.ctrl")
@RequestMapping("/survey")
public class SurveyCtrl{


    @Autowired
    private SurveyService surveyService;

    @ResponseBody
    @RequestMapping(value = "/get/survey/list",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurveyModelList(String creator,String surveyType){

        Result result = surveyService.getSurveyModelListByCreatorAndType(creator,surveyType);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/get/survey/info",method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String getSurveyInfo(String surveyId){

        Result result = surveyService.getSurveyInfo(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/get/survey",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurvey(String surveyId){

        Result result = surveyService.getSurveyTitleOptionModel(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/load/survey",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String loadSurvey(String surveyId, String wxNickname, String wxOpenId) throws UnsupportedEncodingException {
        wxNickname = new String(wxNickname.getBytes("ISO-8859-1"),"UTF-8");
        Result result = surveyService.getSurveyTitleOptionModel(surveyId,wxNickname,wxOpenId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/submit/survey",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    public String submitSurveyTitleOptionModel(@RequestBody  SurveyTitleOptionModel<TitleInfoModel> survey, String wxNickname, String wxOpenId) throws UnsupportedEncodingException {
        wxNickname = new String(wxNickname.getBytes("ISO-8859-1"),"UTF-8");
        Result result = surveyService.submitSurveyTitleOptionModel(survey,wxNickname,wxOpenId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/get/survey/summary",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurveySummary(String surveyId){

        long beginTime = System.currentTimeMillis();
        Result result = surveyService.getSurveySummary(surveyId);
        String resultStr = JSON.toJSONString(result);
        long endTime = System.currentTimeMillis();
        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/get/survey/submit/list",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurveySubmitDetail(String surveyId){

        Result result = surveyService.getSurveySubmitDetailList(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/get/survey/name/statistics",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getSurveyByNameStatistics(String surveyId,String titleId){

        Result result = surveyService.getSurveyByNameStatistics(surveyId,titleId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }


    @ResponseBody
    @RequestMapping(value = "/save/survey",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    public String saveSurvey(@RequestBody  SurveyModel surveyModel){

        Result result = surveyService.saveSurvey(surveyModel);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/update/survey",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    public String updateSurvey(@RequestBody  SurveyModel surveyModel){

        Result result = surveyService.updateSurvey(surveyModel);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }

    @ResponseBody
    @RequestMapping(value = "/delete/survey",method = RequestMethod.DELETE,produces = "text/plain;charset=utf-8")
    public String deleteSurvey(@RequestBody Map<String,Object> paramsMap){

        String surveyId = paramsMap.get("surveyId").toString();
        Result result = surveyService.deleteSurvey(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }


    @ResponseBody
    @RequestMapping(value = "/clear/survey",method = RequestMethod.DELETE,produces = "text/plain;charset=utf-8")
    public String clearSurvey(@RequestBody Map<String,Object> paramsMap){

        String surveyId = paramsMap.get("surveyId").toString();
        Result result = surveyService.clearSurvey(surveyId);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }


    @ResponseBody
    @RequestMapping(value = "/update/survey/time/setting",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    public String updateSurveyTimeSetting(@RequestBody  SurveyModel surveyModel){

        Result result = surveyService.updateSurveyTimeSetting(surveyModel);
        String resultStr = JSON.toJSONString(result);

        return resultStr;
    }


    @ResponseBody
    @RequestMapping(value = "/export/excel",method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, String surveyId, int sortCols, @RequestBody List<TitleModel> titleModelList){
        try {
            OutputStream outputStream = response.getOutputStream();
            XSSFWorkbook wb = surveyService.createWorkbook(surveyId, sortCols, titleModelList);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename="+surveyId+".xlsx");
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/contest/title/import")
    public String contestTitleImport(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file,String surveyId, String surveyType){
        Result result = new Result();
        result = surveyService.contestTitleImport(file,surveyId);

        return JSON.toJSONString(result);
    }



    @ResponseBody
    @RequestMapping("/upload")
    public String upload(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file){
        Result<String> result = new Result();
        result.setMessage(Message.Success.getType());
        try{
            request.setCharacterEncoding("UTF-8");

            if(file.isEmpty()){
                result.setMessage(Message.Error.getType());
                return JSON.toJSONString(result);
            }
            String fileName = file.getOriginalFilename();
            String path = null;
            String type = null;
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;

            if(Validator.isEmpty(type)){
                result.setMessage(Message.Error.getType());
                return JSON.toJSONString(result);
            }

            if (!("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase()))){
                result.setMessage(Message.Error.getType());
                return JSON.toJSONString(result);
            }

            // 项目在容器中实际发布运行的根路径
            //String realPath = request.getServletPath();
            String realPath = UploadUtil.getUploadPath();
            File dir = new File(realPath);
            if(!dir.exists()){
                dir.mkdirs();
            }

            // 自定义的文件名称
            //String trueFileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()) + fileName;
            String trueFileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS").format(new Date())+"."+type;
            // 设置存放图片文件的路径
            path = realPath + "/" + trueFileName;
            file.transferTo(new File(path));
            result.setData(trueFileName);
        }catch (Exception e){
            result.setMessage(e.getMessage());
        }

        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping(value = "/get/openid",method = RequestMethod.GET,produces = "text/plain;charset=utf-8")
    public String getOpenId(HttpSession session,String code){
        Result<String> result = new Result<>();
        result.setMessage(Message.Success.getType());
        StringBuffer url = new StringBuffer();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        url.append("https://api.weixin.qq.com/sns/jscode2session")
            .append("?appid=wx23c65ec205e7c1ca")
            .append("&secret=8c8912176ed7668c9abe95bc2d1af776")
            .append("&js_code=").append(code)
            .append("&grant_type=authorization_code")
            .append("&connect_redirect=1");

        try{
            String res = null;
            httpClient = HttpClientBuilder.create().build();
            HttpGet httpget = new HttpGet(url.toString());    //GET方式
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()          // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)             // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(false).build();           // 将上面的配置信息 运用到这个Get请求里
            httpget.setConfig(requestConfig);                         // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpget);                   // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                res = EntityUtils.toString(responseEntity);
            }
            JSONObject jo = JSON.parseObject(res);
            String openid = jo.getString("openid");
            result.setData(openid);
        }catch(Exception e){
            result.setMessage(e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                httpClient.close();
                response.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return JSON.toJSONString(result);
    }


}
