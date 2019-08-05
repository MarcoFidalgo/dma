/*******************************************************************************
 * 2008-2019 Public Domain
 * Contributors
 * Marco Lopes (marcolopespt@gmail.com)
 *******************************************************************************/
package org.dma.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.dma.java.util.Debug;
import org.dma.java.util.StringUtils;

public class FileHandler {

	/**
	 * Unicode Byte Order Mark<br>
	 * http://unicode.org/faq/utf_bom.html
	 */
	public static final String UTF8_CHARSET = "UTF-8";
	public static final String UTF8_BOM = new String(new char[]{0xEF,0xBB,0xBF});

	public static final String UTF16_CHARSET = "UTF-16";
	public static final String UTF16_BOM = "\ufeff";

	public static final String ISO_CHARSET = "ISO-8859-1";
	public static final String WIN1252_CHARSET = "Windows-1252";

	public static final int BASE64_LINE_LENGTH = 64;
	public static final int STRING_BUFFER_LENGTH = 4096;

	public final File file;

	public FileHandler(String pathname) {
		this(new File(pathname));
	}

	public FileHandler(File file) {
		this.file=file;
	}


	/**
	 * Removes accents and illegal characters<br>
	 * Replaces SPACES with UNDERLINES
	 */
	public static String normalize(String pathname) {
		return StringUtils.normalize(pathname).
				replace("- ","-").
				replace(" -","-").
				replace(" ","_");
	}


	/**
	 * Generic File Copy<br>
	 * Does not cancel the operation
	 */
	@Deprecated
	public AbstractFileCopy copy() {
		return new AbstractFileCopy(file){
			@Override
			public boolean cancel() {
				return false;
			}
		};
	}


	/**
	 * Copies the file to DESTINATION
	 * @param dst can be file or directory
	 */
	public boolean copyTo(File dst) {
		try{
			//destination can be a directory!
			Files.copy(file.toPath(), dst.isDirectory() ?
					new File(dst.getPath()+File.separator+file.getName()).toPath() : dst.toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			return true;

		}catch(Exception e){
			Debug.err(e);
		}return false;
	}


	/**
	 * Moves the file to DESTINATION
	 * @param dst can be file or directory
	 */
	public boolean moveTo(File dst) {
		try{
			//destination can be a directory!
			Files.move(file.toPath(), dst.isDirectory() ?
					new File(dst.getPath()+File.separator+file.getName()).toPath() : dst.toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			return true;

		}catch(Exception e){
			Debug.err(e);
		}return false;
	}


	public boolean renameTo(String pathname) {
		try{
			return file.renameTo(new File(pathname));

		}catch(Exception e){
			Debug.err(e);
		}return false;
	}


	public boolean delete() {
		try{
			return file.delete();

		}catch(Exception e){
			Debug.err(e);
		}return false;
	}


	public InputStream asInputStream() {
		try{
			return new FileInputStream(file);

		}catch(Exception e){
			Debug.err(e);
		}return null;
	}


	public OutputStream asOutputStream() {
		try{
			return new FileOutputStream(file);

		}catch(Exception e){
			Debug.err(e);
		}return null;
	}


	public URL toURL() {
		try{
			return file.toURI().toURL();

		}catch(Exception e){
			Debug.err(e);
		}return null;
	}


	public boolean equals(File file){
		try{
			return this.file.getCanonicalFile().
					equals(file.getCanonicalFile());

		}catch(Exception e){
			Debug.err(e);
		}return false;
	}


}
