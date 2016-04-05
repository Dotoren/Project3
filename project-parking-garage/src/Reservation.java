
public class Reservation {
	
	private String licensePlate;
	private String paymentType;
	private Customer customer;
	private double price;
	
	public Reservation(String licensePlate, String paymentType, Customer customer)
	{
		this.price = 5.00;
		this.licensePlate = licensePlate;
		this.paymentType = paymentType;
		this.customer = customer;
	}
	
	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	//goed?
	public boolean pay(double price)
	{
		//Welke type klant is het
		//Afhankelijk van type klant betaalmogelijkheid bepalen?
		if(price >= this.price)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
