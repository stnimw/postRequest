package com.sandy.rate.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sandy.rate.service.MyService;
import com.sandy.rate.utils.MyUtils;
import com.sandy.rate.vo.ChartVO;
import com.sandy.rate.vo.SearchVO;
import com.sandy.rate.vo.TimePeriod;
@RestController
public class MyController {
	
	@Autowired
	MyService myService;
	
	@RequestMapping(value = "/")
    public ModelAndView search() {
		ModelAndView view = new ModelAndView("exchangerate");
		try{
			//設default		
			LocalDateTime currentDateTime = LocalDateTime.now();
			JSONObject parentObj = new JSONObject();
			JSONObject obj = new JSONObject();
			obj.put("Currency", "CNY");
			obj.put("Currencytype", "0");
			obj.put("Rangetype", "0");
			obj.put("Startdate", currentDateTime.truncatedTo(ChronoUnit.DAYS).toString());
			obj.put("Enddate", currentDateTime.toString());
			parentObj.put("data", obj);
			ChartVO vo = myService.post(
					"https://www.esunbank.com.tw/bank/Layouts/esunbank/Deposit/DpService.aspx/GetLineChartJson", parentObj,
					"utf-8");
			
			view.addObject("data",vo);
			
			return view;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return view;
    }

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	 public ChartVO update(@RequestBody SearchVO searchVo) {
		ChartVO vo = new ChartVO();
		try{
			TimePeriod timePeriod = MyUtils.getTimePeriodByRangeType(searchVo.getRangeType());
			String startDate = timePeriod.getStartDate();
			String endDate = timePeriod.getEndDate();
			
			JSONObject parentObj = new JSONObject();
			JSONObject obj = new JSONObject();
			obj.put("Currency", searchVo.getCurrency());
			obj.put("Currencytype", "0");
			obj.put("Rangetype", searchVo.getRangeType());
			obj.put("Startdate", startDate);
			obj.put("Enddate", endDate);
			parentObj.put("data", obj);
			String url = "https://www.esunbank.com.tw/bank/Layouts/esunbank/Deposit/DpService.aspx/GetLineChartJson";
			vo = myService.post(url, parentObj,"utf-8");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
    }
	
}
