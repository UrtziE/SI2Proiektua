package adapter;

import javax.swing.table.AbstractTableModel;
import domain.Driver;
import domain.Ride;

public class DriverAdapter extends AbstractTableModel {

    private Driver driver;

    private String[] columnNames = {"From", "To", "Date","Places","Price"}; 


    public DriverAdapter(Driver driver) {
        this.driver = driver;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public int getRowCount() {
        
        return driver.getRides().size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {

        Ride ride = driver.getRides().get(rowIndex);
        switch (columnIndex) {
            case 0: return ride.getFrom();
            case 1: return ride.getTo();
            case 2: return ride.getDate();
            case 3: return ride.getnPlaces();
            case 4: return ride.getPrice();
        }
        return null;
    }
}

