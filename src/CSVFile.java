// CSVFile class:

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVFile {
    private final BufferedReader reader;
    private final Map<Integer, String[]> data;
    private String[] headers;
    private boolean hasHeaders;
    private final String splitBy;

    public CSVFile(String filePath, String splitBy) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(filePath));
        this.data = new HashMap<>();
        this.hasHeaders = false;
        this.splitBy = splitBy;
    }

    public void read() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(splitBy);
            if (!hasHeaders) {
                headers = values;
                hasHeaders = true;
                continue;
            }
            data.put(data.size() + 1, values);
        }
    }

    public String[] getHeaders() {
        return headers;
    }

    public Map<Integer, String[]> getData() {
        return data;
    }
}
