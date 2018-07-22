package com.sandy.rate.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.sandy.rate.vo.ChartVO;

@Service
public class MyService {
		
		/**
		 * 送post request直接獲取回傳的json
		 */
		public ChartVO post(String pageUrl, JSONObject obj, String charSet){
			ChartVO vo = new ChartVO();
			try {
				//取得連線
				URL url = new URL(pageUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");		
				conn.setDoOutput(true);// 參數要輸出
				conn.setUseCaches(false);// 不使用緩存
				conn.setConnectTimeout(60 * 10000);
				conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				conn.setRequestProperty("Referer",
						"https://www.esunbank.com.tw/bank/personal/deposit/rate/forex/exchange-rate-chart?Currency=USD/TWD");
				conn.connect();
				DataOutputStream out = new DataOutputStream(conn.getOutputStream());

				out.writeBytes(obj.toString());
				out.flush();
				out.close();

				//讀取結果
				BufferedReader reader = null;
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charSet));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				conn.disconnect();
				
				//解析結果
				JSONObject myJson = new JSONObject(sb.toString());
				String myStr = myJson.getString("d");
				JSONObject dJson = new JSONObject(myStr);
				Iterator<String> iter = dJson.keys();
				JSONArray ratesArr = new JSONArray();
				while(iter.hasNext()){
					String key = (String)iter.next();
					if(key.equals("Rates")) {
						ratesArr = dJson.getJSONArray(key);
					}
				}
				
				//放入資料
				List<Double> buyRateList = new ArrayList<>();
				List<Double> sellRateList = new ArrayList<>();
				List<String> timeList = new ArrayList<>();
				for(int i=0;i<ratesArr.length();i++){
				     JSONObject j = ratesArr.getJSONObject(i);
				     buyRateList.add(j.getDouble("BuyRate"));
				     sellRateList.add(j.getDouble("SellRate"));
				     timeList.add(j.getString("Time"));
				}
				vo.setTimeList(timeList);
				vo.setBuyRateList(buyRateList);
				vo.setSellRateList(sellRateList);
				vo.setStartDate(obj.getJSONObject("data").getString("Startdate"));
				vo.setEndDate(obj.getJSONObject("data").getString("Enddate"));
			}catch(IOException e) {
				System.out.println("IOException  :" + e);
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return vo;
		}
}
