package controller.backingbeans;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
@ManagedBean(name="imageCropperBean")
@ViewScoped
public class ImageCropperBean implements Serializable {

	public ImageCropperBean() {
		super();
		// TODO Auto-generated constructor stub
		width = height = widthPreview = heightPreview = 128;

		System.out.println("constructor");
		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();

		basepath = servletContext.getRealPath("") + File.separator
				+ "resources" + File.separator + "tmp";
	}

	public int getWidthPreview() {
		return widthPreview;
	}

	public void setWidthPreview(int widthPreview) {
		this.widthPreview = widthPreview;
	}

	public int getHeightPreview() {
		return heightPreview;
	}

	public void setHeightPreview(int heightPreview) {
		this.heightPreview = heightPreview;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4841270855129145648L;

	private CroppedImage croppedImage;

	private String uploadedImage = "nophoto.jpg";
	private String newImageName = "blank";
	private String extension = "png";
	private static final int DEFAULT_WIDTH = 500;
	private int width, height, widthResized, heightResized, widthPreview,			heightPreview;
	byte[] croppedImage2;

	String fakename_resized, fakename;
	String basepath;

	public boolean isRendered() {
		System.out.println(uploadedImage
				+ "//////////////////////////////////////");
		return (!uploadedImage.equals("nophoto.jpg"));
	}

	public float getRatio() {

		return (((float) widthPreview) / ((float) widthPreview));
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public CroppedImage getCroppedImage() {
		return croppedImage;
	}

	public String getUploadedImage() {
		return uploadedImage;
	}

	public void setUploadedImage(String uploadedImage) {
		this.uploadedImage = uploadedImage;
	}

	public void setCroppedImage(CroppedImage croppedImage) {
		this.croppedImage = croppedImage;
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		try {

			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();

			File targetFolder = new File(basepath);

			InputStream inputStream = event.getFile().getInputstream();
			BufferedImage originalImage = ImageIO.read(event.getFile()
					.getInputstream());

			width = originalImage.getWidth();
			height = originalImage.getHeight();
			InputStream inputStreamResized = resize(event.getFile()
					.getInputstream(), Mode.FIT_TO_WIDTH, DEFAULT_WIDTH);
			BufferedImage resizedImage = ImageIO.read(resize(event.getFile()
					.getInputstream(), Mode.FIT_TO_WIDTH, DEFAULT_WIDTH));
			widthResized = resizedImage.getWidth();
			heightResized = resizedImage.getHeight();
			// BufferedImage bi=new BufferedImage(100, 100, 1);

			// extraer parte de la ruta
			String filename = event.getFile().getFileName();
			int dot = filename.lastIndexOf(".");
			int sep = filename.lastIndexOf("/");
			extension = filename.substring(dot + 1);
			String name = filename.substring(sep + 1, dot);
			System.out.println("name:" + name);
			System.out.println("extension:" + extension);
			System.out.println("filename:" + filename);
			String randomName = getRandomImageName();
			fakename_resized = randomName + "_resized." + extension;
			fakename = randomName + "." + extension;
			System.out.println("fakename:" + fakename);
			uploadedImage = fakename_resized;

			/*
			 * transformamos el InputStream en BufferedImage para poder usar
			 * Scalr y redimensionar
			 */

			createFile(targetFolder, fakename_resized, inputStreamResized);
			createFile(targetFolder, fakename, inputStream);
			/*
			 * OutputStream out = new FileOutputStream(new
			 * File(targetFolder,fakename)); int read = 0; byte[] bytes = new
			 * byte[1024]; while ((read = inputStream.read(bytes)) != -1) {
			 * out.write(bytes, 0, read); } inputStream.close(); out.flush();
			 * out.close();
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private InputStream resize(InputStream inputStream, Mode mode,
			Integer... ops) throws IOException {

		BufferedImage miimage = ImageIO.read(inputStream);
		BufferedImage bimage;
		switch (mode) {
		case FIT_TO_WIDTH:
			bimage = Scalr
					.resize(miimage, Method.QUALITY, mode, ops[0]/* width */);
			break;

		case FIT_TO_HEIGHT:
			bimage = Scalr
					.resize(miimage, Method.QUALITY, mode, ops[0]/* height */);
			break;
		case FIT_EXACT:
			bimage = Scalr.resize(miimage, Method.QUALITY, mode,
					ops[0]/* width */, ops[1]/* height */);
			break;
		case AUTOMATIC:
			bimage = Scalr.resize(miimage, Method.QUALITY, mode, 100);
			break;
		default:
			bimage = Scalr.resize(miimage, Method.QUALITY, mode, 100);
			break;
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(bimage, extension, os);
		InputStream inputStreamResized = new ByteArrayInputStream(
				os.toByteArray());
		return inputStreamResized;
	}

	public void createFile(File targetFolder, String fakename,
			InputStream inputStream) throws IOException {
		OutputStream out = new FileOutputStream(
				new File(targetFolder, fakename));
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = inputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		inputStream.close();
		out.flush();
		out.close();
	}

	public String getExtension() {
		if (newImageName.equals("blank")) {
			return "png";
		} else {
			return extension;
		}
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	private String getRandomImageName() {
		int i = (int) (Math.random() * 100000);

		return String.valueOf(i);
	}

	public String crop() throws IOException {
		System.out.println("crop");
		if (croppedImage == null)
			return null;
		// getRandomImageName()
		setNewImageName("croped");
		String newFileName = basepath + File.separator + getNewImageName()
				+ "." + extension;

		System.out.println("###############################################");
		System.out.println(croppedImage.getHeight() + "\n"
				+ croppedImage.getWidth() + "\n" + croppedImage.getLeft()
				+ "\n" + croppedImage.getTop() + "\n"
				+ croppedImage.getOriginalFilename() + "\n" + newFileName
				+ "\n");
		System.out.println("###############################################");
		// CroppedImage croppedImage2 = new CroppedImage("", bytes, left, top,
		// DEFAULT_WIDTH, height)

		BufferedImage originalImage = ImageIO.read(new File(basepath
				+ File.separator + fakename));
		float widthRatio = ((float) widthResized / (float) width);
		float heightRatio = ((float) heightResized / (float) height);
		// traducir parametros x y w h
		int w, h, x, y;
		w = (int) (((float) croppedImage.getWidth()) / widthRatio);
		x = (int) (((float) croppedImage.getLeft()) / widthRatio);
		y = (int) (((float) croppedImage.getTop()) / heightRatio);
		h = (int) (((float) croppedImage.getHeight()) / heightRatio);

		BufferedImage croppedImageTmp = originalImage.getSubimage(x, y, w, h);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(croppedImageTmp, extension, os);
		// para poder guardarlo en bd
		croppedImage2 = os.toByteArray();

		FileImageOutputStream imageOutput;
		try {
			imageOutput = new FileImageOutputStream(new File(newFileName));

			imageOutput.write(os.toByteArray(), 0, os.toByteArray().length);
			imageOutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * FileImageOutputStream imageOutput; try { imageOutput = new
		 * FileImageOutputStream(new File(newFileName));
		 * 
		 * imageOutput.write(croppedImage.getBytes(), 0,
		 * croppedImage.getBytes().length); imageOutput.close(); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */

		return null;
	}

	public byte[] getCroppedImage2() {
		return croppedImage2;
	}

	public void setCroppedImage2(byte[] croppedImage2) {
		this.croppedImage2 = croppedImage2;
	}

	public String getNewImageName() {
		return newImageName;
	}

	public void setNewImageName(String newImageName) {
		this.newImageName = newImageName;
	}
}
