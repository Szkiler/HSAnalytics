package src.main.java.org.szkiler.hsa.core.service;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.resize.ResizeProcessor;

public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException {
		ResizeProcessor rp=new ResizeProcessor(50,30);
		MBFImage img1=ImageUtilities.readMBF(new File("C:\\Users\\rabal1\\Documents\\HSAnalytics\\org.szkiler.hsa.core\\resources\\Desert.jpg"));
		MBFImage img2=ImageUtilities.readMBF(new File("C:\\Users\\rabal1\\Documents\\HSAnalytics\\org.szkiler.hsa.core\\resources\\Hydrangeas.jpg"));
		MBFImage img3=ImageUtilities.readMBF(new File("C:\\Users\\rabal1\\Documents\\HSAnalytics\\org.szkiler.hsa.core\\resources\\Tulips.jpg"));
		img1.processInplace(rp);
		img2.processInplace(rp);
		img3.processInplace(rp);
		DisplayUtilities.display(img1);
		DisplayUtilities.display(img2);
		DisplayUtilities.display(img3);
		
		System.out.println("DUPA");

	}

}
