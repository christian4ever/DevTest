package com.airasia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author CHRIS
 */
public class AirAsia {

    private String url = "https://booking3.airasia.com/Flight/InternalSelect?";
    private String from;
    private String to;
    private String dateFrom;
    private String dateTo;
    private boolean oneWay;
    private Document doc;

    public AirAsia(String from, String to, String dateFrom, String dateTo, boolean oneWay) {
        this.from = from;
        this.to = to;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.oneWay = oneWay;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String generateUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.url);
        sb.append("o1=");
        sb.append(from);
        sb.append("&d1=");
        sb.append(to);
        sb.append("&dd1=");
        sb.append(dateFrom);
        if(!oneWay) {
            sb.append("&dd2=");
            sb.append(dateTo);
            sb.append("&r=true");
        }
        sb.append("&ADT=1&CHD=0&inl=0&s=true&mon=true&cc=IDR");

        return sb.toString();
    }

    public void connectToAirAsia() throws IOException {
        String aksesToAirasia = generateUrl();
        doc = Jsoup.connect(aksesToAirasia)
                .timeout(30000)
                .get();
    }

    public List<String> getTitlesFlight() {
        Elements titles = doc.select("#availabilityForm .avail-header .avail-header-title");
        List<String> data = new ArrayList<String>();
        for(Element el : titles) {
            data.add(el.text());
        }

        return data;
    }

    public List<String> getDirections() {
        Elements jurusan = doc.select("#availabilityForm .avail-header .avail-header-cities");
        List<String> data = new ArrayList<String>();
        for (Element el : jurusan) {
            data.add(el.text().replace(" ", " -> "));
        }
        return data;
    }

    public List<Flight> getFlightsOneWay() {
        Elements isiJadwal = doc.select("#availabilityForm .avail-table");
        Elements isiJadwalPergi = isiJadwal.get(0).select(".avail-table-detail-table");
        Elements hargaJadwalPergiLF = isiJadwal.get(0).select("td.avail-fare.depart.LF .avail-fare-price-container");
        Elements hargaJadwalPergiPF = isiJadwal.get(0).select("td.avail-fare.depart.PF .avail-fare-price-container");

        List<Flight> listJadwalPergi = new ArrayList<Flight>();
        for (Element el : isiJadwalPergi) {
            Elements extractSchedule = el.select(".avail-table-detail");

            String[] data = new String[4];
            int i = 0;
            for (Element el1 : extractSchedule) {
                if (i != 2) {
                    data[i] = el1.text();
                } else {
                    data[i] = el1.select("div .carrier-hover-oneway-header .carrier-hover-bold").text();
                }
                i++;
            }

            Flight jadwal = new Flight();
            jadwal.setDepartTime(data[0]);
            jadwal.setArriveTime(data[1]);
            jadwal.setCodeFlight(data[2]);
            jadwal.setDuration(data[3]);

            listJadwalPergi.add(jadwal);
        }

        for (int i = 0; i < listJadwalPergi.size(); i++) {
            String priceLF = hargaJadwalPergiLF.get(i).text();
            String pricePF = hargaJadwalPergiPF.get(i).text();
            listJadwalPergi.get(i).setPriceLF(priceLF);
            listJadwalPergi.get(i).setPricePF(pricePF);
        }

        return listJadwalPergi;
    }

    public List<Flight> getFlightsReturn(){
        if(!oneWay) {
            Elements isiJadwal = doc.select("#availabilityForm .avail-table");
            Elements isiJadwalPulang = isiJadwal.get(1).select(".avail-table-detail-table");
            Elements hargaJadwalPulangLF = isiJadwal.get(1).select("td.avail-fare.return.LF .avail-fare-price-container");
            Elements hargaJadwalPulangPF = isiJadwal.get(1).select("td.avail-fare.return.PF .avail-fare-price-container");

            List<Flight> listJadwalPulang = new ArrayList<Flight>();
            for (Element el : isiJadwalPulang) {
                Elements extractSchedule = el.select(".avail-table-detail");

                String[] data = new String[4];
                int i = 0;
                for (Element el1 : extractSchedule) {
                    if (i != 2) {
                        data[i] = el1.text();
                    } else {
                        data[i] = el1.select("div .carrier-hover-oneway-header .carrier-hover-bold").text();
                    }
                    i++;
                }

                Flight jadwal = new Flight();
                jadwal.setDepartTime(data[0]);
                jadwal.setArriveTime(data[1]);
                jadwal.setCodeFlight(data[2]);
                jadwal.setDuration(data[3]);

                listJadwalPulang.add(jadwal);
            }

            for (int i = 0; i < listJadwalPulang.size(); i++) {
                String priceLF = hargaJadwalPulangLF.get(i).text();
                String pricePF = hargaJadwalPulangPF.get(i).text();
                listJadwalPulang.get(i).setPriceLF(priceLF);
                listJadwalPulang.get(i).setPricePF(pricePF);
            }

            return listJadwalPulang;
        }

        return null;
    }

    public void hasilPencarian(List<String> titles, List<String> directions, List<Flight> flightsOneWay, List<Flight> flightsReturn) {
        System.out.println("Hasil Pencarian AirAsia : ");
        System.out.println("========================================================");

        for (int i = 0; i < titles.size(); i++) {
            System.out.println(titles.get(i) + " " + directions.get(i));
            if (i == 0) { // OneWay
                System.out.println(dateFrom);
                printFlight(flightsOneWay);
            } else if (i == 1 && !oneWay) { //Return
                System.out.println(dateTo);
                printFlight(flightsReturn);
            }
            System.out.println("========================================================");
        }

    }

    public void printFlight(List<Flight> flights) {
        System.out.println("Depart Time \t Arrive Time \t Code Flight \t Duration \t Low Fare \t\t Premium Flex");
        for (Flight fg : flights) {
            System.out.println(fg.getDepartTime() + " \t " + fg.getArriveTime() + " \t " + fg.getCodeFlight() + " \t\t" + fg.getDuration() + " \t " + fg.getPriceLF() + " \t " + fg.getPricePF());
        }
    }
}
