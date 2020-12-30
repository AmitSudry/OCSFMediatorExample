package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import il.cshaifasweng.OCSFMediatorExample.entities.Item;
import il.cshaifasweng.OCSFMediatorExample.entities.Reservation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ReservationController implements Initializable 
{
	
	//private List<Branch> branchList;
	
	@FXML
    private TextField Phone;
	
	@FXML
    private DatePicker Date;

    @FXML
    private ComboBox<String> BranchPick;

    @FXML
    private ComboBox<String> HourPick;

    @FXML
    private TextField NumberOfPeople;

    @FXML
    private TextField Name;

    @FXML
    private ComboBox<String> DiningPick;

    @FXML
    private TextField ReservationStatus;


    @FXML
    void BookTable(ActionEvent event) 
    {
    	if(Phone.getText().equals("") || Name.getText().equals("")
    			|| NumberOfPeople.getText().equals("") || DiningPick.getValue()==null
    			|| HourPick.getValue()==null || BranchPick.getValue()==null
    			|| Date.getValue()==null)
    	{
    		ReservationStatus.setText("You're missing fields!");
    		return;
    	}
    	
    	boolean isInside = DiningPick.getValue().toString().equals("Inside") ? true : false;
    	
    	Reservation r = new Reservation(BranchPick.getValue().toString(), Date.getValue().toString(),
    			Integer.parseInt(NumberOfPeople.getText()), Integer.parseInt(HourPick.getValue().toString()),
    			Name.getText(), isInside, Phone.getText());
    	
    	Phone.setText("");
    	Name.setText("");
    	NumberOfPeople.setText("");
    	HourPick.setValue(null);
    	DiningPick.setValue(null);
    	BranchPick.setValue(null);
    	Date.setValue(null);
    	
    	try 
    	{
        	SimpleClient.getClient().sendToServer(r);
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	ReservationStatus.setText("Table booked!");
    }
    
	@FXML
    private void switchToCustomerController() throws IOException 
    {
        App.setRoot("customer");
    }
	
	@Subscribe
    public void onWarningEvent(GetItemsEvent event) 
    {
    
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		EventBus.getDefault().register(this);
		
		DiningPick.getItems().clear();
        ObservableList<String> list = DiningPick.getItems();
        
        list.add("Inside");
        list.add("Outside");
        
        BranchPick.getItems().clear();
        ObservableList<String> list1 = BranchPick.getItems();
        
        list1.add("Haifa");
        list1.add("Jerusalem");
        
        HourPick.getItems().clear();
        ObservableList<String> list2 = HourPick.getItems();
        
        list2.add("1000");
        list2.add("1015");
        list2.add("1030");
        list2.add("1045");
        list2.add("1100");
        list2.add("1115");
	}
}