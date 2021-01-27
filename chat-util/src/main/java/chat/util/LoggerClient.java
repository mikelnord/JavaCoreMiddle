package chat.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoggerClient {

    private final String pathFile;

    public LoggerClient(String pathFile) {
        this.pathFile = pathFile;
    }

    public void readFileChannel() throws IOException {
        Path path = Paths.get(pathFile);
        if (Files.exists(path)) {
            List<String> fileLines = Files.readAllLines(path);
            int countLines = fileLines.size();
            if (countLines > 100) {
                for (int i = 100; i < countLines; i++) {
                    System.out.println(fileLines.get(i));
                }
            } else {
                for (String s : fileLines) {
                    System.out.println(s);
                }
            }
        }
    }

    public void writeFileChannel(ByteBuffer byteBuffer) throws IOException, ParseException {
        Set<StandardOpenOption> options = new HashSet<>();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.APPEND);
        Path path = Paths.get(pathFile);
        FileChannel fileChannel = FileChannel.open(path, options);
        fileChannel.write(ByteBuffer.wrap(getDateTime()));
        fileChannel.write(ByteBuffer.wrap(" -- ".getBytes()));
        fileChannel.write(byteBuffer);
        fileChannel.write(ByteBuffer.wrap(System.lineSeparator().getBytes()));
        fileChannel.close();
    }

    public byte[] getDateTime() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy H:m:s");
        return formatter.parse(formatter.format(new Date())).toString().getBytes();
    }

}
