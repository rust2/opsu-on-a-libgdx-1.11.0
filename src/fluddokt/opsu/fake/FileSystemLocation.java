package fluddokt.opsu.fake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileSystemLocation {

	File f;
	FileHandle fh;
	public FileSystemLocation(File dir) {
		f = dir;
		fh = Gdx.files.local(f.getPath());
	}
	
	public boolean childexist(String file) {
		return fh.child(file).exists();//new File(f,file).exists();
	}
	public FileHandle child(String file) {
		return fh.child(file);
	}

}
