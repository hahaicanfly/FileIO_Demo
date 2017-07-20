public void CheckSDCard() {
    if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
        //No SD or Read Only
        mPath = Environment.getRootDirectory().getAbsolutePath() + "/DCIM/Demo/File";
        Log.i("File Path", "SD Card are NOT Available ! ");
    } else {
        mPath = Environment.getExternalStorageDirectory() + "/DCIM/Demo/File";
        Log.i("File Path", "SD Card OK file save to : " + mPath);
    }
 
    // make dir
    packageDir = new File(mPath);
    if (!packageDir.exists()) {
        packageDir.mkdirs();
    }
}
 
private static boolean isExternalStorageReadOnly() {
    String SdCardState = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(SdCardState)) {
        return true;
    }
    return false;
}
 
private static boolean isExternalStorageAvailable() {
    String SdCardState = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(SdCardState)) {
        return true;
    }
    return false;
}
