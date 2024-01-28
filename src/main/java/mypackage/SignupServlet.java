package mypackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

public class SignupServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();

		Part filePart = req.getPart("image");
		String fileName = getFileName(filePart);
		InputStream imageInputStream = filePart.getInputStream();




		String typeOfImage = fileName.substring(fileName.lastIndexOf(".") + 1);

		String fname = req.getParameter("fname");
		String lname = req.getParameter("lname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");

		resp.setContentType("text/html");
		resp.getWriter();

		String uploadDir = getServletContext().getRealPath("/images");
		File uploadDirFile = new File(uploadDir);
		if (!uploadDirFile.exists()) {
			uploadDirFile.mkdir();
		}else {}

		String filePath = uploadDir + File.separator + fileName;

		if (!fname.isBlank() && !lname.isBlank() && !email.isBlank() && !password.isBlank() && filePart != null) {

			String regex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
			Pattern pattern = Pattern.compile(regex);

			PreparedStatement pstmt = null;
			if (pattern.matcher(email).matches()) {
				String q = "SELECT email FROM users WHERE email=?";

				Connection conn;
				try {
					conn = new DatabaseConfig().getConnection();
					pstmt = conn.prepareStatement(q);
					pstmt.setString(1, email);
					ResultSet rs = pstmt.executeQuery();

					if (rs.next()) {
						session.setAttribute("error_message", "This email is already exists !");
						req.getRequestDispatcher("signup-now").forward(req, resp);
					} else {

						if (!filePath.isBlank()) {
							if (containsExtension(typeOfImage)) {

								Time timeObj = new Time(System.currentTimeMillis());
								long time = timeObj.getTime();

								String new_image_name = time + fileName;

								String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
								File uploadDirectory = new File(uploadPath);
								if (!uploadDirectory.exists()) {
									uploadDirectory.mkdir();
								}

								Path destinationPath = Paths.get(uploadDirectory.getAbsolutePath());


								try {

									FileOutputStream fout = new FileOutputStream(destinationPath.resolve(new_image_name).toString());
									fout.write(imageInputStream.readAllBytes());

						           String status = "Active now";

						           String uniqueId = generateUniqueId();
						           String passwordHash = AppSecurity.encript(password);

						           PreparedStatement pstmt2 = null;
						           String query2 = "INSERT INTO  `users`  (unique_id, fname, lname, email, password, img, status) VALUES (?,?,?,?,?,?,?)";
						           pstmt2 = new DatabaseConfig().getConnection().prepareStatement(query2);
						           pstmt2.setString(1,uniqueId);
						           pstmt2.setString(2,fname);
						           pstmt2.setString(3,lname);
						           pstmt2.setString(4,email);
						           pstmt2.setString(5,passwordHash);
						           pstmt2.setString(6,new_image_name);
						           pstmt2.setString(7, status);

						           int i = pstmt2.executeUpdate();
						           
						           if(i > 0) {
						        	   
						        	   session.setAttribute("unique_id", uniqueId);
						           }

						           if(i == 1) {
						        	 
						        	   req.getRequestDispatcher("user-chatbox").forward(req, resp);

						           }

								} catch (IOException | NoSuchAlgorithmException e) {
									session.setAttribute("error_message", "Internal error");
									req.getRequestDispatcher("signup-now").forward(req, resp);
								}

							} else {
//								out.write("Please enter valid image format - jpg, png, jpeg");

								session.setAttribute("error_message", "Please enter valid image format - jpg, png, jpeg");
								req.getRequestDispatcher("signup-now").forward(req, resp);
							}

						} else {
							session.setAttribute("error_message", "Please enter image with format - jpg, png, jpeg");
							req.getRequestDispatcher("signup-now").forward(req, resp);
						}

					}

				} catch (ClassNotFoundException | SQLException e) {
					session.setAttribute("error_message", "Internal error");
					req.getRequestDispatcher("signup-now").forward(req, resp);
					e.printStackTrace();
				}

			}
		}

	}

	private String getFileName(Part part) {
		String contentDispositionHeader = part.getHeader("content-disposition");
		String[] elements = contentDispositionHeader.split(";");
		for (String element : elements) {
			if (element.trim().startsWith("filename")) {
				return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return "unknown.jpg";
	}

	private boolean containsExtension(String uploadedFileExtendsion) {

		String extensions[] = { "png", "jpg", "jpeg" };

		for (String value : extensions) {
			if (value.equalsIgnoreCase(uploadedFileExtendsion)) {
				return true;
			}
		}

		return false;
	}

	static AtomicLong counter = new AtomicLong(System.currentTimeMillis());
	// generating unique id's
	public static String generateUniqueId() {
		
		UUID uniqueID = UUID.randomUUID();
		
		String uuid1 = uniqueID.toString();
		String uuid2 = Const.generateUniqueID() + "";
		
		String UUID = uuid1 + uuid2;
		
		
		return UUID;
	}
	
	
	
	
	

}
