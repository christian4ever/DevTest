
package com.airasia;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author CHRIS
 */
public class SearchingAirAsia {

    //Parameter
    static String from = "CGK"; // Jakarta
    static String to   = "SIN"; // Singapore
    static String dateFrom = "2015-04-28"; //Format yyyy-mm-dd
    static String dateTo   = "2015-04-30"; //Format yyyy-mm-dd
    static boolean oneWay  = false;

    public static void main (String [] args) throws IOException {
        AirAsia airAsia = new AirAsia(from, to, dateFrom, dateTo, oneWay);
        airAsia.connectToAirAsia();

        List<String> titles = airAsia.getTitlesFlight();
        List<String> directions = airAsia.getDirections();
        List<Flight> flightsOneWay = airAsia.getFlightsOneWay();
        List<Flight> flightsReturn = airAsia.getFlightsReturn();

        airAsia.hasilPencarian(titles, directions, flightsOneWay, flightsReturn);
    }
}
