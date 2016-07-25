/*******************************************************************************
 * 2008-2016 Public Domain
 * Contributors
 * Marco Lopes (marcolopes@netc.pt)
 *******************************************************************************/
package org.dma.java.util;

public final class VersionNumber {

	public final int major;
	public final int minor;
	public final int macro;
	public final int build;

	public VersionNumber(int major, int minor) {
		this(major, minor, 0);
	}

	public VersionNumber(int major, int minor, int macro) {
		this(major, minor, macro, 0);
	}

	public VersionNumber(int major, int minor, int macro, int build) {
		this.major=major;
		this.minor=minor;
		this.macro=macro;
		this.build=build;
	}

	public VersionNumber(String version) {
		String[] array=ArrayUtils.numbers(version.split("\\."));
		major=array.length>0 ? Integer.valueOf(array[0]) : 0;
		minor=array.length>1 ? Integer.valueOf(array[1]) : 0;
		macro=array.length>2 ? Integer.valueOf(array[2]) : 0;
		build=array.length>3 ? Integer.valueOf(array[3]) : 0;
	}


	/**
	 * Checks if the current version is smaller
	 * @return
	 * TRUE if the current version is SMALLER;
	 * FALSE if the current version is EQUAL of GREATER
	 */
	public boolean smallerThan(VersionNumber version) {
		Debug.out(version);
		if (major>version.major) return false;
		if (major<version.major) return true;
		if (minor>version.minor) return false;
		if (minor<version.minor) return true;
		if (macro>version.macro) return false;
		if (macro<version.macro) return true;
		if (build>version.build) return false;
		if (build<version.build) return true;
		return false; //equal!
	}


	public boolean equals(VersionNumber version) {
		return major==version.major &&
				minor==version.minor &&
				macro==version.macro &&
				build==version.build;
	}


	public int[] toArray(){
		return new int[]{major, minor, macro, build};
	}



	/*
	 * (non-Javadoc)
	 * @see java.lang.Object
	 */
	@Override
	public String toString(){
		return String.valueOf(major)+"."+
			String.valueOf(minor)+"."+
			String.valueOf(macro)+"."+
			String.valueOf(build);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return hashCode()==obj.hashCode();
	}


}
