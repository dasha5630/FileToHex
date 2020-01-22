package efa.utility.tdv;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: 21.10.2019 application doesn't work with ru text
public class Main {
    static String pathIn;
    static String pathOut;
    static File file;
    static int value = 0;
    static int bytesCounter = 0;
    static int globalBytesCounter = 0;

    public static void main(String[] args) throws IOException {
        pathIn  = args[0];
        pathOut = args[1];
        file = new File(pathOut);
        try (BufferedReader br = new BufferedReader(new FileReader(new File(pathIn)))) {
            String line;
            StringBuilder sbHex = new StringBuilder();
            StringBuilder sbText = new StringBuilder();
            StringBuilder sbRes = new StringBuilder();

            FileWriter writer = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(writer);
            while ((value = br.read()) != -1) {
                sbHex.append(String.format("0x%02X, ", value));
                sbText.append((char) value);
                globalBytesCounter++;
                if (bytesCounter == 15) {
                    sbRes.append(sbHex.toString())
                            .append("//")
                            .append(sbText.toString().replace('\n', '.').replace('\r', '.'))
                            .append("\n");
                    sbText.setLength(0);
                    sbHex.setLength(0);
                    bytesCounter = 0;
                } else{
                    bytesCounter++;
                }
            }
            sbHex.append("};");
            if (bytesCounter != 0) {
                for (; bytesCounter < 16; bytesCounter++) {
                    sbHex.append(" ");
                }

            }

            sbRes.append(sbHex).append("//").append(sbText).append("\n");
            sbRes.append("//").append(globalBytesCounter);
            bw.write(sbRes.toString());
            bw.close();
        }
    }
}