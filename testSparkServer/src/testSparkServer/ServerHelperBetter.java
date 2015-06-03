package testSparkServer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerHelperBetter {

	public static String getHttpConnection(String serverAddress) throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(new URI(serverAddress));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		HttpResponse response = httpclient.execute(httpGet);

		HttpEntity entity = response.getEntity();

        String str = entityToString(entity);

        return str;
	}

    public static String putHttpConnection(String serverAddress, JSONObject params) throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPut = null;
        try {
            httpPut = new HttpPut(new URI(serverAddress));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        httpPut.addHeader("Content-Type", "application/json");
        httpPut.addHeader("Accept", "application/json");
        httpPut.setEntity(new StringEntity(params.toString(), "UTF-8"));

        HttpResponse response = httpclient.execute(httpPut);

        HttpEntity entity = response.getEntity();

        String str = entityToString(entity);

        return str;
    }

    public static String postHttpConnection(String serverAddress, List<NameValuePair> params){
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(serverAddress);
            String ret = null;

            try {
                //httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

                httpPost.addHeader("Accept", "application/json");
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                paramList.addAll(params);
                httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));

                HttpResponse httpresponse = httpclient.execute(httpPost);
                int code = httpresponse.getStatusLine().getStatusCode();
                if (code == 401) return "401";
                ret = entityToString(httpresponse.getEntity());

            } catch (ClientProtocolException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
            return ret;
    }

	public static String postHttpConnection(String serverAddress, JSONObject jsonObject)
			throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(new URI(serverAddress));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		httpPost.addHeader("Content-Type", "application/json");
		httpPost.addHeader("Authorization", "key=AIzaSyC-DNuirxPDQQDExeOep3b0sBy0BnM0uI8");
		httpPost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();

		return entityToString(entity);
	}

	public static String deleteHttpConnection(String serverAddress) throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpDelete httpDelete = null;
		try {
			httpDelete = new HttpDelete(new URI(serverAddress));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		HttpResponse response = httpclient.execute(httpDelete);
		HttpEntity entity = response.getEntity();

		return entityToString(entity);
	}

    public static String deletePatkanysag(String serverAddress,JSONArray jsonObject) {
        HttpEntity entity = null;
        try {

            entity = new StringEntity(jsonObject.toString());

            HttpClient httpClient = new DefaultHttpClient();
            HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(serverAddress);
            httpDeleteWithBody.setEntity(entity);

            HttpResponse response = httpClient.execute(httpDeleteWithBody);

            HttpEntity entityResp = response.getEntity();
            return entityToString(entityResp);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String deleteHttpConnection(String serverAddress,JSONObject jsonObject) throws ClientProtocolException, IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        HttpEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
        HttpDeleteWithBody httpDelete = null;
        try {
            httpDelete = new HttpDeleteWithBody(new URI(serverAddress));
            httpDelete.addHeader("Content-Type", "application/json");
            httpDelete.addHeader("Accept", "application/json");
            httpDelete.setEntity(entity);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpResponse response = httpclient.execute(httpDelete);
        HttpEntity entityResp = response.getEntity();
        return entityToString(entityResp);
    }


	private static String entityToString(HttpEntity entity) throws ParseException, IOException {
		String result = EntityUtils.toString(entity, "UTF-8");
		return result;
	}

//    static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
//        public static final String METHOD_NAME = "DELETE";
//        public String getMethod() {
//            return METHOD_NAME; }
//
//        public HttpDeleteWithBody(final String uri) {
//            super();
//            setURI(URI.create(uri));
//        }
//        public HttpDeleteWithBody(final URI uri) {
//            super();
//            setURI(uri);
//        }
//        public HttpDeleteWithBody() { super(); }
//    }

    static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "DELETE";
        public String getMethod() { return METHOD_NAME; }

        public HttpDeleteWithBody(final String uri) {
            super();
            setURI(URI.create(uri));
        }
        public HttpDeleteWithBody(final URI uri) {
            super();
            setURI(uri);
        }
        public HttpDeleteWithBody() { super(); }
    }
}

