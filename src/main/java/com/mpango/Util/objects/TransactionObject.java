package com.mpango.Util.objects;


public class TransactionObject{
	
	public String cardNumber;
	public String invoiceNumber;
	
	public TransactionObject(){
		cardNumber = "";
		invoiceNumber = "";
	}
	
	public TransactionObject(String cardNumber,String invoiceNumber){
		this.cardNumber = cardNumber;
		this.invoiceNumber = invoiceNumber;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TransactionObject){
			TransactionObject otherObj = (TransactionObject)obj;
			if(this.cardNumber.equals(otherObj.cardNumber) && this.invoiceNumber.equals(otherObj.invoiceNumber)){
				return true;
			}
		}
		return false;
	}
}
