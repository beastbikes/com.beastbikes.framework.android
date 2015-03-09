package com.beastbikes.framework.android.update;

import java.util.List;

public interface UpdateManager {

	public ReleasedPackage getLatestReleasedPackage(String platform);

	public List<ReleasedPackage> getAllReleasedPackages();

	public ReleasedPackage getReleasedPackage(String platform, String version);

}
