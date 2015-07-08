package org.szkiler.hsa;


import java.io.IOException;
import java.net.URISyntaxException;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.resize.ResizeProcessor;
import org.springframework.core.io.ClassPathResource;

public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException {
		ResizeProcessor rp=new ResizeProcessor(50,30);
		MBFImage img1=ImageUtilities.readMBF(new ClassPathResource("Desert.jpg").getFile());
		MBFImage img2=ImageUtilities.readMBF(new ClassPathResource("Hydrangeas.jpg").getFile());
		MBFImage img3=ImageUtilities.readMBF(new ClassPathResource("Tulips.jpg").getFile());
		img1.processInplace(rp);
		img2.processInplace(rp);
		img3.processInplace(rp);
		DisplayUtilities.display(img1);
		DisplayUtilities.display(img2);
		DisplayUtilities.display(img3);
		
		System.out.println("DUPA");

	}

}
