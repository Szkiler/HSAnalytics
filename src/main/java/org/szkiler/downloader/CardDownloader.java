package org.szkiler.downloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CardDownloader {
	// Configuration Data:
	// The url of the website
	private static String webSiteURL = "http://www.gosugamers.net/hearthstone/cards?page=1";
	// SerialNumber Prefix to Start
	private static int currentImageSerialNumber = 0;
	// String Prefix
	private final static String serialTextPrefix = "CARD_DOWNLOADER";
	// Pages with cards to run
	private final static int numberOfWebsitePagesToRun = 29;
	// The path of the folder that you want to save the images to
	private static final String folderPath = "D:/CardsTest/";
	// Minimum Size of image to be downloaded as card
	public final static int minimumImageSize = 10000;

	private static int currentPageWithCardsNumber = 1;
	private static int currentImageSize;

	public static void main(String[] args) {

		webSiteRunner();

	}

	public static void webSiteRunner() {

		for (; currentPageWithCardsNumber <= numberOfWebsitePagesToRun;) {

			webSiteURL = webSiteURL.substring(0, webSiteURL.indexOf("=") + 1) + currentPageWithCardsNumber;
			System.out.println("CURRENT URL IS " + webSiteURL);
			getEveryImageSourceToReadAndSave(connectToWebsiteAndGetHtmlDocument());
			currentPageWithCardsNumber++;
		}

	}

	public static Elements getAllElementsWithImgTag(Document htmlPageDocument) {
		Elements imageElements = htmlPageDocument.getElementsByTag("img");
		System.out.println("NUMBER OF ELEMENTS IN PAGE " + imageElements.size());
		return imageElements;
	}

	public static void getEveryImageSourceToReadAndSave(Document htmlPageDocument)

	{
		Elements imageElements = getAllElementsWithImgTag(htmlPageDocument);

		for (Element imageTagElement : imageElements) {
			String imageSource = imageTagElement.absUrl("src");
			System.out.println("Image found. Image adress is: " + imageSource);

			try {
				readAndWriteImage(imageSource);
			} catch (IOException e) {

				System.err.println("Data Read/Write Exception");
			}

		}

	}

	public static Document connectToWebsiteAndGetHtmlDocument() {

		Document doc = null;
		try {
			doc = Jsoup.connect(webSiteURL).header("Content-Encoding", "gzip,deflate, sdch")
					.header("Connection", "keep-alive").header("Host", "www.gosugamers.net")
					.header("Referer", "www.gosugamers.net/cards").header("Accept", "*/*")
					.header("Accept-Language", "pl-PL,pl;q=0.8,en-US;q=0.6,en;q=0.4")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36")
					.maxBodySize(0).timeout(6000000).get();
		} catch (IOException e) {
			System.err.println("JSoup Connection Problem");
			e.printStackTrace();
		}

		return doc;
	}

	private static int getImageSize(URL currentImageUrl) {
		HttpURLConnection httpConnectionWithImage = null;
		try {
			httpConnectionWithImage = (HttpURLConnection) currentImageUrl.openConnection();
			httpConnectionWithImage.setRequestMethod("HEAD");
			httpConnectionWithImage.getInputStream();
			return httpConnectionWithImage.getContentLength();
		} catch (IOException e) {
			return -1;
		} finally {
			httpConnectionWithImage.disconnect();
		}
	}

	private static String imageNameGiver(String downloadableImageUrl) {

		int endOfPathIndex = downloadableImageUrl.lastIndexOf("/") + 1;

		if (endOfPathIndex == downloadableImageUrl.length()) {
			downloadableImageUrl = downloadableImageUrl.substring(1, endOfPathIndex);
			downloadableImageUrl = downloadableImageUrl + currentPageWithCardsNumber;
		}
		String name = downloadableImageUrl.substring(endOfPathIndex, downloadableImageUrl.length());

		String newNameToBeGivenToCardInLocalDisc = folderPath + "/" + serialTextPrefix + currentImageSerialNumber + "-"
				+ currentPageWithCardsNumber + "-" + name;
		currentImageSerialNumber++;
		return newNameToBeGivenToCardInLocalDisc;
	}

	private static void readAndWriteImage(String imageSource) throws IOException {

		// Extract the name of the image from the src attribute
		String newNameToBeGivenToCardInLocalDisc = imageNameGiver(imageSource);

		// Open a URL Stream
		URL currentImageUrl = new URL(imageSource);
		currentImageSize = getImageSize(currentImageUrl);
		if (currentImageSize > minimumImageSize) {
			System.out.println("SAVING to... " + newNameToBeGivenToCardInLocalDisc);
			InputStream imageDataStreamReader = currentImageUrl.openStream();
			FileOutputStream imageDataStreamWriter = new FileOutputStream(newNameToBeGivenToCardInLocalDisc);
			for (int b; (b = imageDataStreamReader.read()) != -1;) {

				imageDataStreamWriter.write(b);
			}
			imageDataStreamWriter.close();
			imageDataStreamReader.close();

			System.out.println(("Filtering success...Current image size is:    " + currentImageSize));
		}

	}
}