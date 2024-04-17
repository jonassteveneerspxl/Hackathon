import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeExtractor {

    public static void main(String[] args) {
        // Specify the path to the GIF file containing QR codes
        String gifFilePath = "C:\\Users\\Mijn PC\\Downloads\\Movie.gif";

        try {
            // Create an ImageReader to read the GIF file
            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            // Provide the ImageReader with the GIF file to read
            reader.setInput(ImageIO.createImageInputStream(new File(gifFilePath)));

            // Get the number of frames in the GIF
            int numFrames = reader.getNumImages(true);

            // Loop through each frame of the GIF
            for (int i = 0; i < numFrames; i++) {
                // Read the next frame from the GIF
                BufferedImage frame = reader.read(i);

                // Process the frame (e.g., decode the QR code)
                Result result = processFrame(frame, i);
                if (result != null) {
                    System.out.println(result.getText());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Result processFrame(BufferedImage frame, int frameIndex) {
        // Decode the QR code from the frame
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(frame)));
        try {
            return new MultiFormatReader().decode(bitmap);
        } catch (NotFoundException e) {
            // QR code not found in the image
            return null;
        }
    }


}
