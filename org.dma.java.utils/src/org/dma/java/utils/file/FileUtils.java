/*******************************************************************************
 * 2008-2013 Public Domain
 * Contributors
 * Marco Lopes (marcolopes@netc.pt)
 *******************************************************************************/
package org.dma.java.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.dma.java.utils.net.NetUtils;
import org.dma.java.utils.string.StringUtils;

public class FileUtils {

	/**
	 * Unicode Byte Order Mark<br>
	 * http://unicode.org/faq/utf_bom.html
	 */
	public static final String UTF8_BOM = "\ufeff";
	public static final String UTF8_CHARSET = "UTF-8";

	public static final String ISO_CHARSET = "ISO-8859-1";
	public static final String WIN1252_CHARSET = "Windows-1252";

	public static final int BASE64_LINE_LENGTH = 64;

	/**
	 * Generic File Copy<br>
	 * Does not cancel the operation
	 */
	public static final AbstractFileCopy FILECOPY = new AbstractFileCopy(){
		public boolean cancel() {
			return false;
		}
	};





	/*
	 * Conversion
	 */
	public static URL toURL(String filename){
		try{
			return new File(filename).toURI().toURL();

		}catch(MalformedURLException e){
			System.out.println(e);
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * Removes accents and illegal characters<br>
	 * Replaces SPACES with UNDERLINES
	 */
	public static String normalize(String filename){

		return StringUtils.normalize(filename).
				replace("- ","-").replace(" -","-").replace(" ","_");

	}


	/**
	 * Encodes a file into BASE64
	 * @param filename - the file whose contents will be encoded
	 * @param lineLength - Each line of encoded data will be at most
	 * of the given length (rounded down to nearest multiple of 4).
	 * If lineLength <= 0, then the output will not be divided into lines.
	 */
	public static String encodeToBase64(String filename, int lineLength) {

		final String pad="-----";

		try{
			File file=new File(filename);
			byte[] key=FileUtils.readBytesStream(file);

			String encoded=new Base64(lineLength).encodeToString(key);

			return pad+"BEGIN "+file.getName().toUpperCase()+" BASE64"+pad+
				"\n"+encoded+
				pad+"END "+file.getName().toUpperCase()+" BASE64"+pad;

		}catch (Exception e){
			e.printStackTrace();
		}

		return null;

	}



	/**
	 * Encodes a file into BASE64 with a line length of 64
	 * @param filename - the file whose contents will be encoded
	 */
	public static String encodeToBase64(String filename) {

		return encodeToBase64(filename, BASE64_LINE_LENGTH);

	}





	/*
	 * Close
	 */
	public static void close(Closeable resource) {
		try{
			resource.close();

		}catch (IOException e){
		}catch (Exception e){
			e.printStackTrace();
		}
	}





	/*
	 * Delete
	 */
	public static boolean deleteFile(String filename){

		return new File(filename).delete();

	}





	/*
	 * Class FileReader
	 *
	 * Convenience class for reading character files.
	 *
	 * The constructors of this class assume that the default character encoding and the
	 * default byte-buffer size are appropriate. To specify these values yourself,
	 * construct an InputStreamReader on a FileInputStream.
	 *
	 * FileReader is meant for reading streams of characters.
	 * For reading streams of raw bytes, consider using a FileInputStream.
	 *
	 */
	public static String readTextFile(File file) {

		try{
			final BufferedReader br =
					new BufferedReader(
							new FileReader(file));

			final StringBuffer buffer=new StringBuffer();

			try{
				int ch;
				while ((ch = br.read()) > -1){
					buffer.append((char)ch);
				}

			}finally{
				close(br);
			}

			return buffer.toString();

		}catch (FileNotFoundException e){
			System.out.println(e);
		}catch (IOException e){
			System.out.println(e);
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;

	}


	public static String readTextFile(String filename) {

		return readTextFile(new File(filename));

	}


	public static String readTextFileLines(File file, int count) {

		String text="";

		try{
			final BufferedReader br =
					new BufferedReader(
							new FileReader(file));

			try{
				String line;
				while((line = br.readLine()) != null && count--!=0){
					text += line + "\n";
				}

			}finally{
				close(br);
			}

		}catch (FileNotFoundException e){
			System.out.println(e);
		}catch (IOException e){
			System.out.println(e);
		}catch (Exception e){
			e.printStackTrace();
		}

		return text;

	}


	public static String readTextFileLines(String filename, int count) {

		return readTextFileLines(new File(filename), count);

	}





	/*
	 * Class FileWriter
	 *
	 * Convenience class for writing character files.
	 *
	 * The constructors of this class assume that the default character encoding and
	 * the default byte-buffer size are acceptable. To specify these values yourself,
	 * construct an OutputStreamWriter on a FileOutputStream.
	 *
	 * Whether or not a file is available or may be created depends upon the underlying
	 * platform. Some platforms, in particular, allow a file to be opened for writing
	 * by only one FileWriter (or other file-writing object) at a time.
	 * In such situations the constructors in this class will fail if the file involved
	 * is already open.
	 *
	 * FileWriter is meant for writing streams of characters. For writing streams of raw bytes,
	 * consider using a FileOutputStream.
	 *
	 */
	public static boolean writeTextFile(String text, File file) {

		try{
			final BufferedWriter bw =
					new BufferedWriter(
							new FileWriter(file));

			try{
				bw.write(text);

			}finally{
				close(bw);
			}

			return true;

		}catch (IOException e){
			System.out.println(e);
		}catch (NullPointerException e){
		}catch (Exception e){
			e.printStackTrace();
		}

		return false;

	}


	public static boolean writeTextFile(String text, String filename) {

		return writeTextFile(text, new File(filename));

	}





	/*
	 * Class FileInputStream
	 *
	 * A FileInputStream obtains input bytes from a file in a file system.
	 * What files are available depends on the host environment.
	 *
	 * FileInputStream is meant for reading streams of raw bytes such as image data.
	 * For reading streams of characters, consider using FileReader.
	 */
	public static byte[] readBytesStream(File file) {

		byte[] buffer=null;

		try{
			final BufferedInputStream bis =
					new BufferedInputStream(
							new FileInputStream(file));

			try{
				buffer=new AbstractByteReader(){
					public int read(byte[] b, int off, int len) throws IOException {
						return bis.read(b, off, len);
					}
				}.readBytes(file);

			}finally{
				// Close the input stream
				close(bis);
			}

		}catch (FileNotFoundException e){
			System.out.println(e);
		}catch (IOException e){
			System.out.println(e);
		}catch (Exception e){
			e.printStackTrace();
		}

		return buffer;

	}


	public static byte[] readBytesStream(String filename) {

		return readBytesStream(new File(filename));

	}





	/*
	 * Class FileOutputStream
	 *
	 * A file output stream is an output stream for writing data to a File or to a
	 * FileDescriptor. Whether or not a file is available or may be created depends upon
	 * the underlying platform. Some platforms, in particular, allow a file to be opened
	 * for writing by only one FileOutputStream (or other file-writing object) at a time.
	 * In such situations the constructors in this class will fail if the file involved
	 * is already open.
	 *
	 * FileOutputStream is meant for writing streams of raw bytes such as image data.
	 * For writing streams of characters, consider using FileWriter.
	 *
	 */
	public static int writeBytesStream(byte[] bytes, File file) {

		try{
			final BufferedOutputStream bos =
					new BufferedOutputStream(
							new FileOutputStream(file));

			try{
				bos.write(bytes);

			}finally{
				close(bos);
			}

			return bytes.length;

		}catch (FileNotFoundException e){
			System.out.println(e);
		}catch (IOException e){
			System.out.println(e);
		}catch (NullPointerException e){
		}catch (Exception e){
			e.printStackTrace();
		}

		return 0;

	}


	public static int writeBytesStream(byte[] bytes, String filename) {

		return writeBytesStream(bytes, new File(filename));

	}


	public static boolean writeObjectStream(Object obj, File file) {

		try{
			final ObjectOutputStream oos =
					new ObjectOutputStream(
							new FileOutputStream(file));

			try{
				oos.writeObject(obj);

			}finally{
				close(oos);
			}

			return true;

		}catch (FileNotFoundException e){
			System.out.println(e);
		}catch (IOException e){
			System.out.println(e);
		}catch (NullPointerException e){
		}catch (Exception e){
			e.printStackTrace();
		}

		return false;

	}


	public static boolean writeObjectStream(Object obj, String filename) {

		return writeObjectStream(obj, new File(filename));

	}





	/*
	 * Class InputStreamReader
	 *
	 * An InputStreamReader is a bridge from byte streams to character streams:
	 * It reads bytes and decodes them into characters using a specified charset.
	 * The charset that it uses may be specified by name or may be given explicitly,
	 * or the platform's default charset may be accepted.
	 *
	 * Each invocation of one of an InputStreamReader's read() methods may cause one
	 * or more bytes to be read from the underlying byte-input stream. To enable the
	 * efficient conversion of bytes to characters, more bytes may be read ahead from
	 * the underlying stream than are necessary to satisfy the current read operation.
	 *
	 * For top efficiency, consider wrapping an InputStreamReader within a BufferedReader.
	 * For example:
	 *  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	 *
	 */
	public static String readTextStream(File file, String charset) {

		StringBuffer buffer=new StringBuffer();

		try{
			final BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(file), charset));

			try{
				int ch;
				while ((ch = br.read()) > -1){
					buffer.append((char)ch);
				}

			}finally{
				close(br);
			}

		}catch (UnsupportedEncodingException e){
			System.out.println(e);
		}catch (FileNotFoundException e){
			System.out.println(e);
		}catch (IOException e){
			System.out.println(e);
		}catch (Exception e){
			e.printStackTrace();
		}

		return buffer.toString();

	}


	public static String readTextStream(String filename, String charset) {

		return readTextStream(new File(filename), charset);

	}


	public static String readTextStreamUTF8(File file) {

		return readTextStream(file, UTF8_CHARSET);
	}


	public static String readTextStreamUTF8(String filename) {

		return readTextStreamUTF8(new File(filename));

	}


	public static String readURLTextStream(String fileurl, String charset) {

		final StringBuffer buffer=new StringBuffer();

		try{
			final BufferedReader br =
					new BufferedReader(
							new InputStreamReader(
									NetUtils.getInputStream(fileurl), charset));

			try{
				int ch;
				while ((ch = br.read()) > -1){
					buffer.append((char)ch);
				}

			}finally{
				close(br);
			}

		}catch (UnsupportedEncodingException e){
			System.out.println(e);
		}catch (IOException e){
			System.out.println(e);
		}catch (Exception e){
			e.printStackTrace();
		}

		return buffer.toString();

	}


	public static String readURLTextStreamUTF8(String fileurl) {

		return readURLTextStream(fileurl, UTF8_CHARSET);
	}


	public static String readTextStreamLines(File file, int count, String charset) {

		String text="";

		try{
			final BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(file), charset));

			try{
				String line;
				while((line = br.readLine()) != null && count--!=0){
					text += line + "\n";
				}

			}finally{
				close(br);
			}

		}catch (UnsupportedEncodingException e){
			System.out.println(e);
		}catch (FileNotFoundException e){
			System.out.println(e);
		}catch (IOException e){
			System.out.println(e);
		}catch (Exception e){
			e.printStackTrace();
		}

		return text;

	}


	public static String readTextStreamLines(String filename, int count, String charset) {

		return readTextStreamLines(new File(filename), count, charset);

	}





	/*
	 * Class OutputStreamWriter
	 *
	 * An OutputStreamWriter is a bridge from character streams to byte streams:
	 *
	 * Characters written to it are encoded into bytes using a specified charset.
	 * The charset that it uses may be specified by name or may be given explicitly,
	 * or the platform's default charset may be accepted.
	 *
	 * Each invocation of a write() method causes the encoding converter to be invoked
	 * on the given character(s). The resulting bytes are accumulated in a buffer
	 * before being written to the underlying output stream.
	 * The size of this buffer may be specified, but by default it is large enough
	 * for most purposes.
	 *
	 * Note that the characters passed to the write() methods are not buffered.
	 * For top efficiency, consider wrapping an OutputStreamWriter within a BufferedWriter
	 * so as to avoid frequent converter invocations. For example:
	 * Writer out = new BufferedWriter(new OutputStreamWriter(System.out));
	 *
	 */
	public static boolean writeTextStream(String text, File file, String charset) {

		try{
			final BufferedWriter bw =
				new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(file), charset));

			try{
				bw.write(text);

			}finally{
				close(bw);
			}

			return true;

		}catch (UnsupportedEncodingException e){
			System.out.println(e);
		}catch (FileNotFoundException e){
			System.out.println(e);
		}catch (IOException e){
			System.out.println(e);
		}catch (NullPointerException e){
		}catch (Exception e){
			e.printStackTrace();
		}

		return false;

	}


	public static boolean writeTextStream(String text, String filename, String charset) {

		return writeTextStream(text, new File(filename), charset);

	}


	public static boolean writeTextStreamUTF8(String text, File file) {

		return writeTextStream(text, file, UTF8_CHARSET);

	}


	public static boolean writeTextStreamUTF8(String text, String filename) {

		return writeTextStreamUTF8(text, new File(filename));

	}


	public static boolean replaceOcurrencesUTF8(String filename, String[][] searchReplace){
		try{
			String text=FileUtils.readTextStreamUTF8(filename);

			for(int i=0; i<searchReplace.length; i++){
				text=text.replaceAll(searchReplace[i][0], searchReplace[i][1]);
			}

			FileUtils.writeTextStreamUTF8(text, filename);

			return true;

		}catch (Exception e){
			e.printStackTrace();
		}

		return false;

	}


	/*
	 * Temporary Files
	 *
	 * prefix - The prefix string to be used in generating the file's name;
	 * 			must be at least three characters long
	 *
	 * suffix - The suffix string to be used in generating the file's name;
	 * 			may be null, in which case the suffix ".tmp" will be used
	 *
	 * directory - The directory in which the file is to be created,
	 * 			or null if the default temporary-file directory is to be used
	 *
	 */
	public static String createTempFile(FileParameters parameters){

		try{
			File directory=parameters.getFolder()==null ? null : parameters.getFolder();
			File file=File.createTempFile(parameters.getPrefix(), "."+parameters.getSuffix(), directory);
			file.deleteOnExit();

			return file.getAbsolutePath();

		}catch (Exception e){
			e.printStackTrace();
		}

		return null;

	}


	public static String createTempFile(byte[] bytes, FileParameters parameters){

		String filename=createTempFile(parameters);
		writeBytesStream(bytes, filename);

		return filename;

	}


}