package ltd.newbee.mall.util;

import net.sf.json.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

;


/**
 * Created by 01440590 on 2019/1/23.
 */
public class RestUtils {
    public static String httpPostWithJson(JSONObject jsonObj, String url){
        String result = "{\"msg\":\"生成付款码异常\",\"charset\":\"UTF-8\",\"code\":\"000000\"}";
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost request = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<String> keySet = jsonObj.keySet();
            for(String key:keySet){
                String value = jsonObj.getString(key);
                nvps.add(new BasicNameValuePair(key, value));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            //System.out.println( response.getStatusLine().getStatusCode());
            result = EntityUtils.toString(entity);
            //System.out.print(result);//返回结果
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String ards[]){
        try {
            HttpClient client = HttpClients.createDefault();
            String url = "https://zgateway.kjtpay.com/recv.do";
            HttpPost request = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("request_no", "20190124123737242502"));
            nvps.add(new BasicNameValuePair("service", "instant_trade"));
            nvps.add(new BasicNameValuePair("version", "1.0"));
            nvps.add(new BasicNameValuePair("partner_id", "200001725622"));
            nvps.add(new BasicNameValuePair("charset", "UTF-8"));
            nvps.add(new BasicNameValuePair("sign_type", "RSA"));
            nvps.add(new BasicNameValuePair("sign", "AMPE%2FNK%2BKn9XyOo6zeB3wwfQmZ2mXKIFuvvpjc0nVd1XSnwgjf6rgloPTS7Em2axQAiDkZcO3UwX5c4eOO0jwu0rzjQ9%2FrtrQAf5djw7E46VzzPtX%2Fn%2BOiAhQmS7%2FYUNXTI%2BgC4vTbIBdoyF8asWfA7bgnX3yjfBCCW5EXdDz4E%3D"));
            nvps.add(new BasicNameValuePair("timestamp", "2019-01-24+14%3A18%3A05"));
            nvps.add(new BasicNameValuePair("format", "JSON"));
            nvps.add(new BasicNameValuePair("biz_content", "YH%2F8VNdoMoUtOq8Qs%2BOwJ0wJbxglXew5F8Bv%2FYHraskBAyJSOWpItFSZIOAtZa8bZStv7ZhXvd%2BU3IDlbK5IPel8yv90FV7oRpz7XPBCrBQm%2B8MtlK3wP9ZP7Kurb1%2BQGBzBjlt%2F2DvfvGZxKgRv3ZP2qjjCFsr3aZnMyDGR1jaJ4pvejeTCLyj%2Bme7bBcMAO9VbiRSiMT7xRZBtxGjIlnNqskkfVAuf8VXoGtC%2FborLBkXznYIerjCZIknAzmljTzeLrIiCD2WJR6zmvp6dKwQ1xooP8ZE%2BaFSD1Jd4%2BfUuBrD%2BqProPDa5psNNsdPAqUsJuE27f3uDjqi1L8lLqZlDUHXwPs2SmyxR1bprMlgadBmSg2JRsxdtxEIXu7javaRr0X1UozVOWAvBBDJsGeH8kedcy3fAA0Ewuufps4ZZrCNn01USQxGFPV7HAA01wJ5qNZGf3pDf1aOqDXZ9K7ozdD3xpeDCwlJt7JvnH9aIOZJc%2FvqsLZao%2FqRfg888I9gSrY0Kic7tIhH8f6G1DihUZigXxQ1w0RKJz0wyoCG2X5zL%2FvqbiCijeUqgpaM95ZssaAQj5eAMjGv0MpvmDzwDPTU24ZzTbKnW%2F%2FXhJ0ccxiWvoM9ZsbhFLLfvDBZZb5zvHaDa7xbmvrxLUd9C0U%2BgGeXW5J1v1HxnHGwsUIxljhBaf3pIrOJ95oXv68yLYN0deJbUlkh36m%2BOcMqiA9We7OeXjZ2ziSmxPZ8D%2FFUOsQv1DZLyI21%2Bi6ecNs0Kslzae7eCoL%2FrZEa5E9WLmRODTXsqE7V26GxaUgTsBfiYqbjNNEd3wT88rqSDTj7DqnZ%2FtUoymGvcUXbMOuH52w%3D%3D"));
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
            request.setEntity(formEntity);

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            //System.out.println( response.getStatusLine().getStatusCode());
            //System.out.print(EntityUtils.toString(entity));//返回结果
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
