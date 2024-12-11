package com.napier.devops;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App{

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    public static void main (String[] args) throws IOException {

        // Create new Application
        App a = new App();

        if (args.length < 1) {
            a.connect("localhost:3306", 0);
        } else {
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        a.report1();

        // Disconnect from database
        a.disconnect();
    }

    private void disconnect() {
    }

    // All countries in the world organised by largest population to smallest
    /**
     * This method generates a report of all the countries in the world,
     * ordered by their population from largest to smallest. The report includes
     * the country's code, name, continent, region, population, and capital city.
     * The results are written to a text file.
     *
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report1() throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Code") + "\t" + rset.getString("Name") + "\t" + rset.getString("Continent") + "\t" + rset.getString("Region") + "\t" + rset.getInt("Population") + "\t" + rset.getString("Capital") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportCountriesWorld.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of all the countries in a specific continent,
     * ordered by their population from largest to smallest. The report includes
     * the country's code, name, continent, region, population, and capital city.
     * The results are written to a text file.
     *
     * @param continent The name of the continent to filter the countries by.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report2(String continent) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = '" + continent + "' ORDER BY Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Code") + "\t" + rset.getString("Name") + "\t" + rset.getString("Continent") + "\t" + rset.getString("Region") + "\t" + rset.getInt("Population") + "\t" + rset.getString("Capital") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportCountriesContinent.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of all the countries in a specific region,
     * ordered by their population from largest to smallest. The report includes
     * the country's code, name, continent, population, and capital city.
     * The results are written to a text file.
     *
     * @param region The name of the region to filter the countries by.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report3(String region) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT Code, Name, Continent, Population, Capital FROM Country WHERE Region = '" + region + "' ORDER BY Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                String code = rset.getString("Code");
                String name = rset.getString("Name");
                String continent = rset.getString("Continent");
                int population = rset.getInt("Population");
                int capital = rset.getInt("Capital");
                sb.append(code).append("\t").append(name).append("\t")
                        .append(continent).append("\t").append(population).append("\t").append(capital).append("\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File("./output/report_countries_by_region_" + region + ".txt")));
            writer.write(sb.toString());
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method generates a report of the top N populated countries in the world,
     * where N is provided by the user. The report includes the country's code,
     * name, continent, region, population, and capital city.
     * The results are written to a text file.
     *
     * @param n The number of top countries to include in the report.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report4(int n) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC LIMIT " + n;
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Code") + "\t" + rset.getString("Name") + "\t" + rset.getString("Continent") + "\t" + rset.getString("Region") + "\t" + rset.getInt("Population") + "\t" + rset.getString("Capital") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportTopNCountriesWorld.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }


    /**
     * This method generates a report of the top N populated countries in a given continent.
     * The results are ordered by population in descending order and written to a file.
     *
     * @param continent The continent name for which the countries' population data is required.
     * @param N The number of top populated countries to be included in the report.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report5(String continent, int N) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = '" + continent + "' ORDER BY Population DESC LIMIT " + N;
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Code") + "\t" + rset.getString("Name") + "\t" + rset.getString("Continent") + "\t" + rset.getString("Region") + "\t" + rset.getInt("Population") + "\t" + rset.getString("Capital") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportTopNPopulatedCountriesContinent.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the top N populated countries in a given region.
     * The results are ordered by population in descending order and written to a file.
     *
     * @param region The region name for which the countries' population data is required.
     * @param N The number of top populated countries to be included in the report.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report6(String region, int N) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Region = '" + region + "' ORDER BY Population DESC LIMIT " + N;
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Code") + "\t" + rset.getString("Name") + "\t" + rset.getString("Continent") + "\t" + rset.getString("Region") + "\t" + rset.getInt("Population") + "\t" + rset.getString("Capital") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportTopNPopulatedCountriesRegion.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of all cities in the world, ordered by population in descending order.
     * The results are written to a file.
     *
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report7() throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT CityID, Name, CountryCode, District, Population FROM city ORDER BY Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("CityID") + "\t" + rset.getString("Name") + "\t" + rset.getString("CountryCode") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportCitiesWorld.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of all cities in a given continent, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param continent The continent name for which the cities' population data is required.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report8(String continent) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.CityID, c.Name, c.CountryCode, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE co.Continent = '" + continent + "' " +
                    "ORDER BY c.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("CityID") + "\t" + rset.getString("Name") + "\t" + rset.getString("CountryCode") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportCitiesContinent.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of all cities in a given region, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param region The region name for which the cities' population data is required.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report9(String region) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE co.Region = '" + region + "' " +
                    "ORDER BY c.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportCitiesRegion.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of all cities in a given country, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param countryCode The country code for which the cities' population data is required.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report10(String countryCode) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population FROM city c " +
                    "JOIN country co ON c.CountryCode = co.Code WHERE c.CountryCode = '" + countryCode + "' " +
                    "ORDER BY c.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportCitiesCountry.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of all cities in a given district, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param district The district name for which the cities' population data is required.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report11(String district) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE c.District = '" + district + "' " +
                    "ORDER BY c.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportCitiesDistrict.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the top N populated cities in the world, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param N The number of top populated cities to be included in the report.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report12(int N) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code ORDER BY c.Population DESC LIMIT " + N;
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportTopNPopulatedCitiesWorld.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the top N populated cities in a given continent, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param continent The continent name for which the cities' population data is required.
     * @param N The number of top populated cities to be included in the report.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report13(String continent, int N) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE co.Continent = '" + continent + "' " +
                    "ORDER BY c.Population DESC LIMIT " + N;
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportTopNPopulatedCitiesContinent.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the top N populated cities in a given region, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param region The region name for which the cities' population data is required.
     * @param N The number of top populated cities to be included in the report.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report14(String region, int N) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE co.Region = '" + region + "' " +
                    "ORDER BY c.Population DESC LIMIT " + N;
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportTopNPopulatedCitiesRegion.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the top N populated cities in a given country, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param countryCode The country code for which the cities' population data is required.
     * @param N The number of top populated cities to be included in the report.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report15(String countryCode, int N) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population FROM city c " +
                    "JOIN country co ON c.CountryCode = co.Code WHERE c.CountryCode = '" + countryCode + "' " +
                    "ORDER BY c.Population DESC LIMIT " + N;
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportTopNPopulatedCitiesCountry.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the top N populated cities in a given district, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param district The district name for which the cities' population data is required.
     * @param N The number of top populated cities to be included in the report.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report16(String district, int N) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE c.District = '" + district + "' " +
                    "ORDER BY c.Population DESC LIMIT " + N;
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportTopNPopulatedCitiesDistrict.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of all capital cities in the world, ordered by population in descending order.
     * The results are written to a file.
     *
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report17() throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE c.ID = co.Capital " +
                    "ORDER BY c.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportCapitalCitiesWorld.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of all capital cities in a given continent, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param continent The continent name for which the capital cities' population data is required.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report18(String continent) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE c.ID = co.Capital AND co.Continent = '" + continent + "' " +
                    "ORDER BY c.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportCapitalCitiesContinent.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of all capital cities in a given region, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param region The region name for which the capital cities' population data is required.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report19(String region) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE c.ID = co.Capital AND co.Region = '" + region + "' " +
                    "ORDER BY c.Population DESC";
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportCapitalCitiesRegion.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the top N populated capital cities in the world, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param N The number of top populated capital cities to be included in the report.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report20(int N) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE c.ID = co.Capital " +
                    "ORDER BY c.Population DESC LIMIT " + N;
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportTopNPopulatedCapitalCitiesWorld.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the top N populated capital cities in a given continent, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param continent The continent name for which the capital cities' population data is required.
     * @param N The number of top populated capital cities to be included in the report.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report21(String continent, int N) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE c.ID = co.Capital AND co.Continent = '" + continent + "' " +
                    "ORDER BY c.Population DESC LIMIT " + N;
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportTopNPopulatedCapitalCitiesContinent.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the top N populated capital cities in a given region, ordered by population in descending order.
     * The results are written to a file.
     *
     * @param region The region name for which the capital cities' population data is required.
     * @param N The number of top populated capital cities to be included in the report.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report22(String region, int N) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT c.Name, co.Name AS Country, c.District, c.Population " +
                    "FROM city c JOIN country co ON c.CountryCode = co.Code WHERE c.ID = co.Capital AND co.Region = '" + region + "' " +
                    "ORDER BY c.Population DESC LIMIT " + N;
            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                sb.append(rset.getString("Name") + "\t" + rset.getString("Country") + "\t" + rset.getString("District") + "\t" + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportTopNPopulatedCapitalCitiesRegion.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the population of people, people living in cities, and people not living in cities in each continent.
     * It also includes the percentage of people living and not living in cities.
     * The results are written to a file.
     *
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report23() throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT co.Continent, SUM(co.Population) AS TotalPopulation, " +
                    "SUM(c.Population) AS PopulationInCities, " +
                    "(SUM(co.Population) - SUM(c.Population)) AS PopulationNotInCities " +
                    "FROM country co LEFT JOIN city c ON co.Code = c.CountryCode " +
                    "GROUP BY co.Continent";

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                double totalPopulation = rset.getInt("TotalPopulation");
                double populationInCities = rset.getInt("PopulationInCities");
                double populationNotInCities = rset.getInt("PopulationNotInCities");

                double percentageInCities = (populationInCities / totalPopulation) * 100;
                double percentageNotInCities = (populationNotInCities / totalPopulation) * 100;

                sb.append(rset.getString("Continent") + "\t" + totalPopulation + "\t" + populationInCities + " (" + percentageInCities + "%)" +
                        "\t" + populationNotInCities + " (" + percentageNotInCities + "%)" + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportPopulationContinent.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the population of people, people living in cities, and people not living in cities in each region.
     * It also includes the percentage of people living and not living in cities.
     * The results are written to a file.
     *
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report24() throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT co.Region, SUM(co.Population) AS TotalPopulation, " +
                    "SUM(c.Population) AS PopulationInCities, " +
                    "(SUM(co.Population) - SUM(c.Population)) AS PopulationNotInCities " +
                    "FROM country co LEFT JOIN city c ON co.Code = c.CountryCode " +
                    "GROUP BY co.Region";

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                double totalPopulation = rset.getInt("TotalPopulation");
                double populationInCities = rset.getInt("PopulationInCities");
                double populationNotInCities = rset.getInt("PopulationNotInCities");

                double percentageInCities = (populationInCities / totalPopulation) * 100;
                double percentageNotInCities = (populationNotInCities / totalPopulation) * 100;

                sb.append(rset.getString("Region") + "\t" + totalPopulation + "\t" + populationInCities + " (" + percentageInCities + "%)" +
                        "\t" + populationNotInCities + " (" + percentageNotInCities + "%)" + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportPopulationRegion.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report of the population of people, people living in cities, and people not living in cities in each country.
     * It also includes the percentage of people living and not living in cities.
     * The results are written to a file.
     *
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report25() throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT co.Name, co.Population AS TotalPopulation, " +
                    "SUM(c.Population) AS PopulationInCities, " +
                    "(co.Population - SUM(c.Population)) AS PopulationNotInCities " +
                    "FROM country co LEFT JOIN city c ON co.Code = c.CountryCode " +
                    "GROUP BY co.Name";

            ResultSet rset = stmt.executeQuery(sql);

            while (rset.next()) {
                double totalPopulation = rset.getInt("TotalPopulation");
                double populationInCities = rset.getInt("PopulationInCities");
                double populationNotInCities = rset.getInt("PopulationNotInCities");

                double percentageInCities = (populationInCities / totalPopulation) * 100;
                double percentageNotInCities = (populationNotInCities / totalPopulation) * 100;

                sb.append(rset.getString("Name") + "\t" + totalPopulation + "\t" + populationInCities + " (" + percentageInCities + "%)" +
                        "\t" + populationNotInCities + " (" + percentageNotInCities + "%)" + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportPopulationCountry.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report for the total population of the world.
     * It retrieves the sum of the population of all countries.
     * The result is written to a file.
     *
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report26() throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT SUM(Population) AS TotalPopulation FROM country";

            ResultSet rset = stmt.executeQuery(sql);

            if (rset.next()) {
                sb.append("World Population: " + rset.getInt("TotalPopulation") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportPopulationWorld.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report for the total population of a continent.
     * It retrieves the sum of the population of all countries within a specific continent.
     * The result is written to a file.
     *
     * @param continent The continent whose population is to be calculated.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report27(String continent) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT SUM(Population) AS TotalPopulation FROM country WHERE Continent = '" + continent + "'";

            ResultSet rset = stmt.executeQuery(sql);

            if (rset.next()) {
                sb.append(continent + " Population: " + rset.getInt("TotalPopulation") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportPopulationContinent.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report for the total population of a region.
     * It retrieves the sum of the population of all countries within a specific region.
     * The result is written to a file.
     *
     * @param region The region whose population is to be calculated.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report28(String region) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT SUM(Population) AS TotalPopulation FROM country WHERE Region = '" + region + "'";

            ResultSet rset = stmt.executeQuery(sql);

            if (rset.next()) {
                sb.append(region + " Population: " + rset.getInt("TotalPopulation") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportPopulationRegion.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report for the total population of a country.
     * It retrieves the population of a specific country.
     * The result is written to a file.
     *
     * @param country The country whose population is to be calculated.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report29(String country) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT Population FROM country WHERE Name = '" + country + "'";

            ResultSet rset = stmt.executeQuery(sql);

            if (rset.next()) {
                sb.append(country + " Population: " + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportPopulationCountry.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report for the total population of a district.
     * It retrieves the sum of the population of all cities within a specific district.
     * The result is written to a file.
     *
     * @param district The district whose population is to be calculated.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report30(String district) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT SUM(Population) AS TotalPopulation FROM city WHERE District = '" + district + "'";

            ResultSet rset = stmt.executeQuery(sql);

            if (rset.next()) {
                sb.append(district + " Population: " + rset.getInt("TotalPopulation") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportPopulationDistrict.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report for the population of a specific city.
     * It retrieves the population of the given city.
     * The result is written to a file.
     *
     * @param city The city whose population is to be calculated.
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report31(String city) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT Population FROM city WHERE Name = '" + city + "'";

            ResultSet rset = stmt.executeQuery(sql);

            if (rset.next()) {
                sb.append(city + " Population: " + rset.getInt("Population") + "\r\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportPopulationCity.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }

    /**
     * This method generates a report for the number of people who speak certain languages (Chinese, English, Hindi, Spanish, Arabic),
     * ordered by the greatest number of speakers to the smallest. It also calculates the percentage of the world population speaking each language.
     * The results are written to a file.
     *
     * @throws IOException If an I/O error occurs while writing to the output file.
     */
    public void report32() throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            Statement stmt = con.createStatement();

            // SQL to calculate number of speakers for specific languages
            String sql = "SELECT cl.Language, SUM(c.Population * cl.Percentage / 100) AS TotalSpeakers " +
                    "FROM country c " +
                    "JOIN countrylanguage cl ON c.Code = cl.CountryCode " +
                    "WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') " +
                    "GROUP BY cl.Language " +
                    "ORDER BY TotalSpeakers DESC";

            ResultSet rset = stmt.executeQuery(sql);

            // Get total world population
            String worldPopQuery = "SELECT SUM(Population) AS WorldPopulation FROM country";
            ResultSet worldPopResult = stmt.executeQuery(worldPopQuery);
            int worldPopulation = 0;
            if (worldPopResult.next()) {
                worldPopulation = worldPopResult.getInt("WorldPopulation");
            }

            // Generate the report for language speakers
            sb.append("Language Report (Sorted by Speakers)\n");
            sb.append("Language\tTotal Speakers\tPercentage of World Population\n");

            while (rset.next()) {
                String language = rset.getString("Language");
                double totalSpeakers = rset.getDouble("TotalSpeakers");
                double percentageOfWorld = (totalSpeakers / worldPopulation) * 100;
                sb.append(language + "\t" + String.format("%.0f", totalSpeakers) + "\t" + String.format("%.2f", percentageOfWorld) + "%\n");
            }

            new File("./output/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./output/reportLanguageSpeakers.txt")));
            writer.write(sb.toString());
            writer.close();
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
        }
    }


    /**
     * Connect to the MySQL database.
     *
     * @param conString
     * 		Use db:3306 for docker and localhost:33060 for local or Integration
     * 		Tests
     * @param
     */
    public void connect(String conString, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                //Added allowPublicKeyRetrieval=true to get Integration Tests
                // to work. Possibly due to accessing from another class?
                con = DriverManager.getConnection("jdbc:mysql://" + conString
                        + "/world?allowPublicKeyRetrieval=true&useSSL"
                        + "= true", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt "
                        + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

}