package edu.penzgtu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {

    public static List<HouseData> loadHouseData(String filePath) throws IOException {
        List<HouseData> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 10) {
                    data.add(new HouseData(values));
                }
            }
        }
        return data;
    }

    public static class HouseData {
        private String transactionUniqueId;
        private String pricePaid;
        private String dateOfTransfer;


        public HouseData(String[] values) {
            if (values.length >= 3) {
                this.transactionUniqueId = values[0].trim();
                this.pricePaid = cleanValue(values[1]);
                this.dateOfTransfer = cleanValue(values[2]);
            }

        }

        public String getPricePaid() {
            return pricePaid;
        }

        public String getDateOfTransfer() {
            return dateOfTransfer;
        }

        public String getCity() {
            if (transactionUniqueId != null && transactionUniqueId.length() > 3) {
                return transactionUniqueId.substring(0, 3);
            }
            return "Unknown";
        }


        private String cleanValue(String value) {
            if (value == null) return "";
            String cleanedValue = value.trim();
            return cleanedValue.replaceAll("\"", "");
        }
    }
}