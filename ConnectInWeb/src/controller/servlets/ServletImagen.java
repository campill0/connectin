package controller.servlets;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.Blob;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Usuario;

import dao.DAOException;
import dao.DAOFactory;
import dao.UsuarioDAO;


/**
 * Servlet implementation class ServletDistribuidorImagenes
 */
@WebServlet("/ServletImagen")
public class ServletImagen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletImagen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       System.out.println("asdas");
       String id=request.getParameter("id");
       System.out.println(id.length());
        Long idUsuario=null;
        if(id.length()!=0){
        	idUsuario = Long.parseLong(request.getParameter("id"));	
        
        }
    	byte [] imagen = null;
        if(idUsuario != null){
        	
			try {
				  DAOFactory df = DAOFactory.getDAOFactory(DAOFactory.Type.JPA);
				  UsuarioDAO usuarioDAO = df.getUsuarioDAO();
				  Usuario u =  usuarioDAO.findUsuario(idUsuario);
				  if(u != null ) {
					  imagen = u.getImagen();
				  }
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			
			if(imagen != null)
			{
				
			ServletOutputStream out = response.getOutputStream();
			
			response.setContentType("image/jpg");
	        out.write(imagen);
	        out.flush();
	        out.close();
	        return;
			}
			
        }
        
   
        	//Si no se localiza la foto del usuario servimos la foto por defecto
		
			
			String nophoto="ffd8ffe000104a46494600010200000100010000ffdb004300080606070605080707070909080a0c140d0c0b0b0c1912130f141d1a1f1e1d1a1c1c20242e2720222c231c1c2837292c30313434341f27393d38323c2e333432ffdb0043010909090c0b0c180d0d1832211c213232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232ffc000110800df00e003012200021101031101ffc4001f0000010501010101010100000000000000000102030405060708090a0bffc400b5100002010303020403050504040000017d01020300041105122131410613516107227114328191a1082342b1c11552d1f02433627282090a161718191a25262728292a3435363738393a434445464748494a535455565758595a636465666768696a737475767778797a838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae1e2e3e4e5e6e7e8e9eaf1f2f3f4f5f6f7f8f9faffc4001f0100030101010101010101010000000000000102030405060708090a0bffc400b51100020102040403040705040400010277000102031104052131061241510761711322328108144291a1b1c109233352f0156272d10a162434e125f11718191a262728292a35363738393a434445464748494a535455565758595a636465666768696a737475767778797a82838485868788898a92939495969798999aa2a3a4a5a6a7a8a9aab2b3b4b5b6b7b8b9bac2c3c4c5c6c7c8c9cad2d3d4d5d6d7d8d9dae2e3e4e5e6e7e8e9eaf2f3f4f5f6f7f8f9faffda000c03010002110311003f00f4da28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a7c51493b08e35dc4d0032a48e09a5ff00551bb7d0122b72d3498edf0d26247f7e83e82b440a00e6974abc6ff967b7ea40a9068f79ff004cff00efaae8a8a00e70e9179fdd43f46a824d3eea2fbd0bfe1cff002aeaa931401c791495d55cd9c374bfbc5e7b30e08fc6b9dbbb392ce5dadf329e8dd8ff00f5e802bd14514005145140051451400514514005145140051451400514514005145140051451400e55667555f9989c015d25959adac2178de7ef1fe9f4acdd160dd70f37f70607d4ff00f5bf9d6f5001451450014514500145145001514f04771118e45dca7f4a968a00e52eed64b597cb6fbbd9bb11505755756d1dd40636fc0f707d6b9774689da36fbc0906801b4514500145145001451450014514500145145001451450014514500145145006e6843fd1e5ff007ffa56b565e863fd11ff00dffe82b52800a28a2800a28a2800a28a2800a28a28010d72da80ff008984dfef57526b98d4bfe42137d7fa0a00ab4514500145145001451450014514500145145001451450014514500145145006fe883fd09ffdf3fc8569d66e89ff001e47fdf3fd2b4a800a28a2800a28a2800a28a2800a28a28010d731a97fc8426faff415d39ae6352ff9084df5fe82802ad145140051451400514514005145140051451400514514005145140051454d69b7edb0eee9bc7f3a00dad17fe3c8ff00be7fa56952014b4005145140051451400514514005145140086b98d4bfe42137d7fa0ae9f1595acc71adb86da37971ce067a50061d145140051451400514514005145140051451400514514005145140053e26d92a37a303fad328a00ec41a5a82d26f3ed6393d40fcfbd4f400514514005145140051451400514514005646b8dfb9857d589fd3ff00af5af581adbee9e28ffb8327ea4ff9fce803328a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a00d5d1276f35e166e082c07bd6ed72d60fe55fc2dfc3bb1f9f15d4d0014514500145145001451450014514500453c9e540f27f74135ca49234ae6466dcc4e4d6feaf36cb2dbde4207e1d4d73d4005145140051451400514514005145140051451400514514005145140051451400a0d7516572b756eb27f17423d0d72d56f4fb9920ba1b7eeb9008f5e7ad0074f4520a5a0028a28a0028a28a00290d2d53d426682ca475fbdc007d39a00c7d56e3cfbadabf763e3f1ef5468a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a28a2800a9ec5775ec2bfed0a82aee94bbb508fdb27f4ff00ebd007482969052d001451450014514500159fabff00c83dff00de1fceb42b3f59ff009079ff00787f3a00e768a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a007c11f9b7091ff7d80aeae28638902c6a140e381583a3dbf9b75e67f0c7cfe3daba11400b4514500145145001451450014846ea5a28039ad560582f7e5f955c06c76aa55b7ad5bef885c7f7383f4fff005d625001451450014514500145145001451450014514500145145001454b0db4d3bed8a32defd87e35a96fa22fde9db3fecaf4fce80321519df6aa966f40326afdbe8f712fcd2e221f99fcab721b78a05db146147b54b4015ad6d23b388a479e4e493d4d59a28a0028a28a0028a28a0028a28a0028a28a008a4896589a36fba460d644fa1b2fcd049bbd9b83f9d6e51401c8cd04d036d92329f5e87f1a8ebaf64574daca08f43c8aa171a35bcbf345fba6f6e47e5401cfd1572e34cba83f877a7aaf3fa554a004a28a2800a28a2800a0d4d6f6b35d36d8d7ea4f007e35b56ba443161a5fdebfbf41f850063dbd95c5d7fab5f97fbc7815af6fa3c317cd2fef5bf21f9569014b400d55541b55401e829d4514005145140051451400514514005145140051451400514514005145140051451400555b8b1b7bafbf1f3fde1c1ab5450073d71a3cd17cd17ef57d3a1acf656472aca558763c5763504f6b0dc26d9541f7ee3e8680394a2b52eb47922cb40dbd7fba7aff00f5eb3086572aca558763c1a00eba38d6240a8a1547614fa28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a0028a28a004c551bfd3d6e9372e1650383ebec6afd1401ffd9";
			byte[] nophotoba=	hexStringToByteArray(nophoto);
			
			ServletOutputStream out = response.getOutputStream();
			
			response.setContentType("image/jpg");
	        out.write(nophotoba);
	        out.flush();
	        out.close();
			
			/*
			 
			 byte[] content = ;
InputStream is = new BufferedInputStream(new ByteArrayInputStream(content));
mimeType = URLConnection.guessContentTypeFromStream(is);
			 */
		
        
        
        
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	   public static byte[] hexStringToByteArray(String s) {
           int len = s.length();
           byte[] data = new byte[len / 2];
           for (int i = 0; i < len; i += 2) {
               data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                    + Character.digit(s.charAt(i+1), 16));
           }
           return data;
       }

}
