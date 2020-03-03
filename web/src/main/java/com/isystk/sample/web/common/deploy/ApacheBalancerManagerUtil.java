package com.isystk.sample.web.common.deploy;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.ccil.cowan.tagsoup.Parser;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Apacheのバランサマネージャを利用してバランサの制御を行うためのユーティリティ.
 * 
 * @author iseyoshitaka
 */
public class ApacheBalancerManagerUtil {

	// private static final String
	// private static final String LOAD_FACTOR_PARAM_NAME = "lf";
	// private static final String ROUTE_PARAM_NAME = "wr";
	// private static final String ROUTE_REDIR_PARAM_NAME = "rr";
	private static final String STATUS_PARAM_NAME = "dw";
	private static final String WORKER_URL_PARAM_NAME = "w";
	private static final String BALANCER_NAME_PARAM_NAME = "b";
	private static final String NONCE_PARAM_NAME = "nonce";

	// /** Workerリストテーブルのステータスのヘッダ */
	// private static final String STATUS_TABLE_HEADER_LABEL = "Status";

	private static String CHARSET = "utf-8";

	/**
	 * 指定したワーカのステータスを取得する.
	 * 
	 * @param managerUrl
	 *            マネージャのURL
	 * @param balancerName
	 *            バランサ名
	 * @param workerName
	 *            ワーカ名
	 * @return 対象のワーカが有効かどうか。
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 * @throws MalformedURLException
	 */
	public static boolean getWorkerStatus(String managerUrl, String balancerName, String workerName)
			throws MalformedURLException, XPathExpressionException, IOException, SAXException,
			ParserConfigurationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put(BALANCER_NAME_PARAM_NAME, balancerName);
		params.put(WORKER_URL_PARAM_NAME, workerName);
		return checkStatus(requestHttp(addParam(managerUrl, params), workerName));
	}

	/**
	 * バランサマネージャのワーカの有効無効を切り替える
	 * 
	 * @param managerUrl
	 *            マネージャのURL
	 * @param balancerName
	 *            バランサ名
	 * @param workerName
	 *            ワーカ名
	 * @param enable
	 *            有効にする場合はtrue
	 * @return 指定したステータスに切り替えれたかどうか。
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 * @throws MalformedURLException
	 */
	public static boolean changeWorkerStatus(String managerUrl, String balancerName, String workerName, boolean enable)
			throws MalformedURLException, XPathExpressionException, IOException, SAXException,
			ParserConfigurationException {

		Profiler p = new Profiler("balancer");
		System.out.println(p.start(String.format("%s - %s - %s [%b]", managerUrl, balancerName, workerName, enable)));

		try {
			boolean ret = false;
			Map<String, String> params = new HashMap<String, String>();
			params.put(BALANCER_NAME_PARAM_NAME, balancerName);
			params.put(WORKER_URL_PARAM_NAME, workerName);
			final WorkerInfo workerInfo = requestHttp(addParam(managerUrl, params), workerName);
			System.out.println("nonce:" + workerInfo.nonce);

			params.put(STATUS_PARAM_NAME, enable ? "Enable" : "Disable");
			params.put(NONCE_PARAM_NAME, workerInfo.nonce);

			ret = checkStatus(requestHttp(addParam(managerUrl, params), workerName)) == enable;
			return ret;
		} finally {
			System.out.println(p.end());
		}

	}

	/**
	 * 指定されたワーカーのステータスを返す.
	 * 
	 * @param url
	 * @param workerName
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws XPathExpressionException
	 */
	private static WorkerInfo requestHttp(String url, String workerName) throws MalformedURLException, IOException,
			XPathExpressionException, SAXException, ParserConfigurationException {
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setDoOutput(false);
		con.setRequestMethod("GET");
		try {
			System.out.println(url + ": 接続開始");
			con.connect();
			ArrayList<WorkerInfo> workerInfos = parseResult(new InputSource(con.getInputStream()));
			for (WorkerInfo workerInfo : workerInfos) {
				if (workerInfo.strWorker.equals(workerName)) {
					System.out.println("worker name:" + workerInfo.strWorker);
					System.out.println("nonce:" + workerInfo.nonce);
					System.out.println("status:" + workerInfo.strStatus);
					return workerInfo;
				}
			}

			throw new IllegalArgumentException("target worker [" + workerName + "] NOT FOUND");

		} catch (IOException e) {
			BufferedInputStream bis = new BufferedInputStream(con.getErrorStream());
			StringWriter writer = new StringWriter();
			int data;
			while ((data = bis.read()) != -1)
				writer.write(data);
			writer.flush();
			writer.close();
			bis.close();
			throw new IOException(con.getResponseCode() + ":" + con.getResponseMessage());
		}
	}

	/**
	 * ステータスのチェックを行う.
	 * 
	 * @param workerInfo
	 * @return
	 */
	private static boolean checkStatus(WorkerInfo workerInfo) {
		return workerInfo.strStatus.equals("Ok") ? true : false;
	}

	/**
	 * urlにパラメータを追加する.
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	private static String addParam(String url, Map<String, String> paramMap) {
		StringBuilder builder = new StringBuilder();
		builder.append(url);
		if (!url.contains("?")) {
			builder.append("?");
		} else {
			builder.append("&");
		}
		try {
			int i = 0;
			for (Entry<String, String> param : paramMap.entrySet()) {
				if (i > 0) {
					builder.append("&");
				}
				builder.append(param.getKey());
				builder.append("=");
				builder.append(URLEncoder.encode(param.getValue(), CHARSET));
				i++;
			}

		} catch (UnsupportedEncodingException e) {
			// 固定なので起こりえないはず.
			throw new RuntimeException(e);
		}
		return builder.toString();
	}

	/**
	 * バランサのワーカー情報を保持するクラス.
	 * 
	 * @author iseyoshitaka
	 * 
	 */
	private static class WorkerInfo {
		private String strWorker;
		private String nonce;
		private String strStatus;
	}

	/**
	 * バランサマネージャへのリクエスト送信を実行する.
	 * 
	 * @param input
	 *            解析対象のHTMLのInputSource
	 * @return WorkerInfoのリスト
	 * @throws IOException
	 * @throws SAXException
	 * @throws XPathExpressionException
	 * @throws ParserConfigurationException
	 */
	private static ArrayList<WorkerInfo> parseResult(InputSource input)
			throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {

		ArrayList<WorkerInfo> aWorkerInfo = new ArrayList<WorkerInfo>();

		// ドキュメントビルダの生成
		Parser parser = new Parser();

		// HTML 正規化
		StringWriter output = new StringWriter();
		XMLWriter serializer = new XMLWriter(output);
		parser.setContentHandler(serializer);
		parser.parse(input);

		// パースしてDOMのオブジェクトを取得する
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new InputSource(new StringReader(output.toString())));

		XPath xpath = XPathFactory.newInstance().newXPath();

		XPathExpression xpeTableElementCount = xpath.compile("count(/html/body/table)");
		int nTableElementCount = (int) (double) (Double) xpeTableElementCount.evaluate(doc, XPathConstants.NUMBER);

		for (int nTableElementIndex = 2; nTableElementIndex <= nTableElementCount; nTableElementIndex += 2) {

			// 要素を取得 BalancerManagerのHtml内のテーブル2個目固定で参照..
			XPathExpression xpeWorker = xpath
					.compile(String.format("/html/body/table[%d]/tr/td[1]", nTableElementIndex));
			NodeList nlWorker = (NodeList) xpeWorker.evaluate(doc, XPathConstants.NODESET);

			// nonceを取得する
			XPathExpression xpeNonce = xpath
					.compile(String.format("/html/body/table[%d]/tr/td[1]/a/@href", nTableElementIndex));
			NodeList nlNonce = (NodeList) xpeNonce.evaluate(doc, XPathConstants.NODESET);

			XPathExpression xpeStatus = xpath
					.compile(String.format("/html/body/table[%d]/tr/td[6]", nTableElementIndex));
			NodeList nlStatus = (NodeList) xpeStatus.evaluate(doc, XPathConstants.NODESET);

			for (int i = 0; i < nlWorker.getLength(); ++i) {
				WorkerInfo workerInfo = new WorkerInfo();
				workerInfo.strWorker = getTextContent(nlWorker.item(i));
				workerInfo.nonce = getNonceParam(getTextContent(nlNonce.item(i)));
				workerInfo.strStatus = getTextContent(nlStatus.item(i));
				aWorkerInfo.add(workerInfo);
			}
		}

		return aWorkerInfo;
	}

	/**
	 * nonceパラメータ抽出のための正規表現.
	 */
	private static final Pattern ptnNonce = Pattern.compile("^.*" + NONCE_PARAM_NAME + "=(.*)[&$]?");

	/**
	 * urlからnonceパラメータのみを抜き出す.
	 * 
	 * @param strHref
	 *            各バランサノード設定へのurl
	 * @return nonceパラメータの値
	 */
	private static String getNonceParam(String strHref) {
		Matcher matcher = ptnNonce.matcher(strHref);
		return (matcher.matches()) ? matcher.group(1) : null;
	}

	/**
	 * Recursive implementation of the method org.w3c.dom.Node.getTextContent which
	 * is present in JDK 1.5 but not 1.4
	 * 
	 * @author Tobias Hinnerup ()
	 * @param node
	 * @return
	 */
	private static String getTextContent(Node node) {
		Node child;
		String sContent = node.getNodeValue() != null ? node.getNodeValue() : "";

		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			child = nodes.item(i);
			sContent += child.getNodeValue() != null ? child.getNodeValue() : "";
			if (nodes.item(i).getChildNodes().getLength() > 0) {
				sContent += getTextContent(nodes.item(i));
			}
		}

		return sContent;
	}

}
