package qa.asc.util;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GetFiles {
	
	Stream<Path> filesInFolder;
	
	@SuppressWarnings("unchecked")
	public GetFiles(String path) throws IOException, NoSuchFileException {
		try {
			this.filesInFolder = (Stream<Path>) Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
			
		} catch (NoSuchFileException nsfe) {
			throw new NoSuchFileException(String.format("Get Files NoSuchFileException %s", nsfe));
			
		} catch (IOException e) {
			throw new IOException(String.format("Get Files IOException %s", e));
		} 
	}
	
	public Collection<File> getFiles(String extension) {
		Predicate<? super Object> matches = file -> ((File) file).getPath().endsWith(extension);
		return ((Collection<File>) this.filesInFolder).stream().filter(matches).collect(Collectors.toList());
	}
	
	public Collection<File> getFilesRegex(String pattern) {
		Pattern p = Pattern.compile(pattern);
		Predicate<? super Object> matches = file -> p.matcher(((File) file).getPath()).matches();
		return ((Collection<File>) this.filesInFolder).stream().filter(matches).collect(Collectors.toList());
	}
	
}
