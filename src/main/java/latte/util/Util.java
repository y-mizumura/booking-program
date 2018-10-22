package latte.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;
import org.springframework.security.crypto.codec.Base64;

public class Util {
	
	// base64イメージに変換
	public static String loadBinaryImage(String filename) {
		try {
			// ファイルインスタンスを取得し、ImageIOへ読み込む
			File f = new File(filename);
			BufferedImage image = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(baos);
			image.flush();

			// 読み終わった画像をバイト出力
			ImageIO.write(image, "jpg", bos);
			bos.flush();
			bos.close();
			byte[] bImage = baos.toByteArray();

			// バイト配列→BASE64へ変換
			byte[] encoded = Base64.encode(bImage);
			String base64Image = new String(encoded);

			return base64Image;

		} catch (Exception e) {

		}
		return "";
	}
	
}
